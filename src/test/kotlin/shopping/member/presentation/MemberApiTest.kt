package shopping.member.presentation

import io.kotest.core.annotation.DisplayName
import io.mockk.every
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import shopping.member.fixture.MemberFixture
import shopping.support.KotestControllerTestSupport

@DisplayName("MemberApi 테스트")
class MemberApiTest : KotestControllerTestSupport() {

    init {
        Given("회원 가입 성공 - 회원 가입 요청 시 정상 적인 이메일, 비밀번호를 받아") {
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

        Given("회원 가입 실패 - 회원 가입 요청 시") {

            When("비밀번호가 공백 이라면") {
                val response = mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/members")
                        .content(objectMapper.writeValueAsBytes(MemberFixture.`비밀번호 공백 회원`.`회원 가입 요청 DTO 생성`()))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())

                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("비밀번호 검증 실패 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("비밀번호는 필수로 입력하셔야 합니다.")

                }
            }

            When("비밀번호가 null 이라면") {
                val response = mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/members")
                        .content(objectMapper.writeValueAsBytes(MemberFixture.`비밀번호 NULL 회원`.`회원 가입 요청 DTO 생성`()))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())

                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("비밀번호 검증 실패 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("비밀번호는 필수로 입력하셔야 합니다.")

                }
            }
        }
    }
}