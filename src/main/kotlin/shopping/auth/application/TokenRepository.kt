package shopping.auth.application

import shopping.auth.domain.AuthenticationCredentials

interface TokenRepository {

    fun save(authenticationCredentials: AuthenticationCredentials): AuthenticationCredentials

    fun findByJti(jti: String): AuthenticationCredentials?

    fun deleteByJti(jti: String)

    fun deleteAll()

}
