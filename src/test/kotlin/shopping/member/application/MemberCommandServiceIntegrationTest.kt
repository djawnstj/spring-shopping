package shopping.member.application

import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import shopping.member.fixture.MemberFixture
import shopping.member.presentation.dto.request.MemberSignUpRequest
import shopping.support.KotestIntegrationTestSupport
import kotlin.test.asserter

@DisplayName("MemberCommandService 통합 테스트")
class MemberCommandServiceIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var memberCommandService: MemberCommandService

    init {
        Given("회원 생성 요청 객체를 받아") {
            val memberCommand = MemberFixture.`고객 1`.`회원 가입 요청 DTO 생성`()

            When("회원 정보 저장 후") {
                val actual = memberCommandService.createMember(memberCommand)

                Then("저장 된 ID 를 반환 한다") {
                    asserter.assertTrue("유효한 ID 값 이다", actual > 0)
                }
            }
        }
    }

}
