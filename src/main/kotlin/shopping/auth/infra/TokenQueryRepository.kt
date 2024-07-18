package shopping.auth.infra

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import shopping.auth.application.TokenRepository
import shopping.auth.domain.Token

@Repository
class TokenQueryRepository(
    private val tokenJpaRepository: TokenJpaRepository
): TokenRepository {

    @Transactional
    override fun save(authenticationCredentials: Token) = tokenJpaRepository.save(authenticationCredentials)

    override fun findByJti(jti: String): Token? = tokenJpaRepository.findAll {
        val tokenEntity = entity(Token::class)

        select(
            tokenEntity
        ).from(
            tokenEntity
        ).where(
            path(Token::jti).equal(jti)
        )
    }.firstOrNull()

    override fun deleteByJti(jti: String) {
        tokenJpaRepository.delete {
            deleteFrom(
                entity(Token::class)
            ).where(
                path(Token::jti).equal(jti)
            )
        }
    }

    override fun deleteAll() {
        tokenJpaRepository.deleteAll()
    }
}
