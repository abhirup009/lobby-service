package api.platform.lobby.controllers

import api.platform.lobby.commons.LobbyEndpoints
import api.platform.lobby.managers.UserManager
import api.platform.lobby.model.ProspectiveUser
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    val userManager: UserManager
) {
    @PostMapping(LobbyEndpoints.Base)
    fun newDepartment(
        @RequestBody prospectiveUser: ProspectiveUser
    ): ProspectiveUser =
        userManager.newUser(prospectiveUser = prospectiveUser)

    @GetMapping(LobbyEndpoints.Base)
    fun getDepartmentDetails(
        @RequestParam(value = LobbyEndpoints.UserIdParam, required = true)
        prospectiveUserId: String
    ): ProspectiveUser =
        userManager.getUser(prospectiveUserId = prospectiveUserId)

}