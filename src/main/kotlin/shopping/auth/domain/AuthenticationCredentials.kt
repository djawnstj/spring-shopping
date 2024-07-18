package shopping.auth.domain

import jakarta.persistence.*

typealias Token = AuthenticationCredentials

@Entity
@Table(
    name = "authentication_credentials",
    uniqueConstraints = [UniqueConstraint(name = "uk_authentication_credentials_jti", columnNames = ["jti"])]
)
class AuthenticationCredentials(
    jti: String,
    accessToken: String,
    refreshToken: String,
) {
    @field:Column(name = "jti", nullable = false)
    var jti: String = jti
        protected set
    @field:Column(name = "access_token", nullable = false)
    var accessToken: String = accessToken
        protected set
    @field:Column(name = "refresh_token", nullable = false)
    var refreshToken: String = refreshToken
        protected set
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set
}
