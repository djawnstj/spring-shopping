package shopping.auth.application

import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import shopping.auth.fixture.TokenFixture
import shopping.auth.infra.TokenJpaRepository
import shopping.support.KotestIntegrationTestSupport

@DisplayName("TokenCommandRepository 테스트")
class TokenCommandRepositoryTest: KotestIntegrationTestSupport() {
    @Autowired
    private lateinit var repository: TokenCommandRepository
    @Autowired
    private lateinit var tokenJpaRepository: TokenJpaRepository

    init {
        Given("새로운 토큰 객체를 저장할 때") {
            val authenticationCredentials = TokenFixture.`토큰 1`.`토큰 엔티티 생성`()

            When("저장에 성공 하면") {
                repository.save(authenticationCredentials)

                Then("정상 적인 ID 를 생성 한다.") {
                    authenticationCredentials.id shouldNotBe 0L
                }
            }
        }

        Given("jti 를 이용해 토큰을 삭제할 때") {
            val tokenFixture = TokenFixture.`토큰 1`
            val jti = tokenFixture.jti
            val authenticationCredentials = tokenFixture.`토큰 엔티티 생성`()

            tokenJpaRepository.save(authenticationCredentials)

            When("저장된 토큰 중 동일한 jti 를 가진 토큰을 찾으면") {
                repository.deleteByJti(jti)

                Then("저장 되어 있던 토큰을 삭제한다") {
                    val actual = tokenJpaRepository.findAll().size
                    actual shouldBe 0
                }
            }
        }

        Given("토큰 객체를 이용해 토큰을 삭제 할 때") {
            val authenticationCredentials = tokenJpaRepository.save(TokenFixture.`토큰 1`.`토큰 엔티티 생성`())

            When("토큰을 삭제한 후 토큰을 조회하면") {
                repository.delete(authenticationCredentials)

                Then("null 이 반환 된다") {
                    val actual = tokenJpaRepository.findByIdOrNull(authenticationCredentials.id)
                    actual.shouldBeNull()
                }
            }
        }
    }
}
