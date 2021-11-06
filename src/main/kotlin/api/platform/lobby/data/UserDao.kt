package api.platform.lobby.data

import api.platform.lobby.model.ProspectiveUser

interface UserDao {
    fun newUser(prospectiveUser: ProspectiveUser): ProspectiveUser

    fun getUser(prospectiveUserId: String): ProspectiveUser

}