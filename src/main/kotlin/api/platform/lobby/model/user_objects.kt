package api.platform.lobby.model

enum class ProspectiveUserRole {
    USER,
    ADMIN
}

data class ProspectiveUser(
    val prospectiveUserId: String,
    val name: String,
    val role: ProspectiveUserRole
)
