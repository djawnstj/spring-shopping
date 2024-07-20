package shopping.auth.infra

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import shopping.auth.application.TokenCommandRepository
import shopping.auth.application.TokenQueryRepository
import shopping.auth.domain.AuthenticationCredentials
import shopping.auth.domain.Token

@Repository
class TokenCommandJpaRepository(
    private val tokenJpaRepository: TokenJpaRepository
): TokenCommandRepository {

    override fun save(authenticationCredentials: Token) = tokenJpaRepository.save(authenticationCredentials)

    override fun deleteByJti(jti: String) {
        tokenJpaRepository.delete {
            deleteFrom(
                entity(Token::class)
            ).where(
                path(Token::jti).equal(jti)
            )
        }
    }
}
