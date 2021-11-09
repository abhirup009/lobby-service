package api.platform.lobby.data.impl

import api.platform.lobby.data.UserDao
import api.platform.lobby.model.ProspectiveUser
import api.platform.lobby.utils.toProspectiveUser
import api.platform.lobby.utils.toRecord
import org.jooq.DSLContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import recommender.jooq.Tables

@Repository
class DbUserDao(
    private val dslContext: DSLContext
): UserDao {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(DbUserDao::class.java)
    }

    override fun newUser(prospectiveUser: ProspectiveUser): ProspectiveUser {
        logger.info("Adding new user: $prospectiveUser to db.")
        return dslContext.insertInto(Tables.PROSPECTIVE_USER)
            .set(prospectiveUser.toRecord())
            .returning()
            .fetchOne()
            .toProspectiveUser()
    }

    override fun getUser(prospectiveUserId: String): ProspectiveUser {
        logger.info("Getting user details from db.")
        return dslContext.selectFrom(Tables.PROSPECTIVE_USER)
            .where(Tables.PROSPECTIVE_USER.PROSPECTIVE_USER_ID.eq(prospectiveUserId))
            .fetchOne()
            .toProspectiveUser()
    }
}