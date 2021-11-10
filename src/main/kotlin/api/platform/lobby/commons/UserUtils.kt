package api.platform.lobby.commons

import api.platform.lobby.managers.UserManager
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserUtils(private val userManager: UserManager): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return try {
            userManager.getUser(prospectiveUserId = username!!)
                .let {
                    User(it.name,"", emptyList())
                }
        } catch (e: Exception) {
            throw SecurityException("Unable to match userId from db")
        }
    }
}