package shopping.member.presentation

import io.mockk.every
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import shopping.member.fixture.MemberFixture
import shopping.support.KotestControllerTestSupport

class MemberApiTest : KotestControllerTestSupport() {

    init {
        Given("이메일, 비밀번호를 받아") {
            val memberFixture = MemberFixture.`고객 1`
            val performRequest = {
                mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/members")
                        .content(objectMapper.writeValueAsBytes(memberFixture.`회원 가입 요청 DTO 생성`()))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
            }
            val member = memberFixture.`회원 엔티티 생성`()

            When("회원 가입을 진행 후") {
                every { memberCommandService.createMember(any()) } returns 1L
                every { memberQueryService.findById(any()) } returns member

                val response = performRequest()

                Then("201 상태 코드를 반환 한다") {
                    response.isStatusAs(HttpStatus.CREATED)
                }

                Then("가입 된 회원의 id, email 을 반환 한다") {
                    response.andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.data").isNotEmpty,
                        MockMvcResultMatchers.jsonPath("$.data.id").isNumber,
                        MockMvcResultMatchers.jsonPath("$.data.email").isString,
                    )
                }
            }
        }
    }
}
