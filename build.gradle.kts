import com.rohanprabhu.gradle.plugins.kdjooq.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

val lobbyVersion: String by project
val postgreSqlJdbcVersion: String by project
val kotlinLoggingVersion: String by project
val springCloudVersion: String by project

val dataSourcePrefix = "backend.department.data-source"
val springDataSourcePrefix = "spring.datasource"

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.rohanprabhu.kotlin-dsl-jooq") version "0.4.2"
	id("org.flywaydb.flyway") version "6.1.4"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
}

val props: Properties = Properties().apply {
	this.load(project.file("${projectDir}/src/main/resources/application.properties").inputStream())
}

val datasourceConfig = DatasourceConfig(
	url = props.getProperty("${springDataSourcePrefix}.url"),
	username = props.getProperty("${springDataSourcePrefix}.username"),
	password = props.getProperty("${springDataSourcePrefix}.password"),
	schema = props.getProperty("${dataSourcePrefix}.schema"),
	driver = props.getProperty("${springDataSourcePrefix}.driver-class-name")
)

group = "api.platform"
version = lobbyVersion
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.7")
	implementation("org.postgresql:postgresql:$postgreSqlJdbcVersion")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
	implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
	implementation("org.springframework.boot:spring-boot-starter-security")
	jooqGeneratorRuntime("org.postgresql:postgresql:$postgreSqlJdbcVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
	}
}

flyway {
	url = datasourceConfig.url
	user = datasourceConfig.username
	password = datasourceConfig.password
	schemas = arrayOf(datasourceConfig.schema)
}

jooqGenerator {
	jooqEdition = JooqEdition.OpenSource

	configuration("primary", project.sourceSets.main.get()) {
		configuration = jooqCodegenConfiguration {
			jdbc {
				username = datasourceConfig.username
				password = datasourceConfig.password
				driver   = datasourceConfig.driver
				url = datasourceConfig.url
				schema = datasourceConfig.schema
			}

			generator {
				target {
					packageName = "recommender.jooq"
					directory   = "${project.buildDir}/generated/jooq/primary"
				}

				database {
					name = "org.jooq.meta.postgres.PostgresDatabase"
					inputSchema = datasourceConfig.schema
				}
			}
		}
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = JavaVersion.VERSION_16.toString()
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

data class DatasourceConfig(
	val username: String,
	val password: String,
	val driver: String,
	val url: String,
	val schema: String
)
