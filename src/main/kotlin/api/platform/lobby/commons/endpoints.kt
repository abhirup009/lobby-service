package api.platform.lobby.commons

object CommonEndpoints {
    private const val Root = "/platform"
    private const val Version = "/v1"
    const val Base = "${Version}${Root}"
}

object LobbyEndpoints {
    const val Base = "${CommonEndpoints.Base}/lobby"

    const val UserIdParam = "userId"
}
