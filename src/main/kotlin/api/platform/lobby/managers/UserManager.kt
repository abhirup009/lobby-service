package api.platform.lobby.managers

import api.platform.lobby.data.UserDao
import api.platform.lobby.model.ProspectiveUser
import org.springframework.stereotype.Service

@Service
class UserManager(
    private val userDao: UserDao
) {
    fun newUser(prospectiveUser: ProspectiveUser) =
        userDao.newUser(prospectiveUser = prospectiveUser)

    fun getUser(prospectiveUserId: String) =
        userDao.getUser(prospectiveUserId = prospectiveUserId)

}