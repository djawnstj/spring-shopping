package shopping.auth.fixture

import shopping.auth.domain.AuthenticationCredentials

enum class TokenFixture(
    val jti: String,
    val accessToken: String,
    val refreshToken: String
) {
    `토큰 1`("jti", "accessToken", "refreshToken"), ;

    fun `토큰 엔티티 생성`(): AuthenticationCredentials = AuthenticationCredentials(jti, accessToken, refreshToken)
}
