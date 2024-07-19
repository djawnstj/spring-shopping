package shopping.auth.application

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.FilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.userdetails.UserDetailsService

class JwtAuthenticationFilterTest: BehaviorSpec({

    val jwtService: JwtService = mockk()
    val userDetailsService: UserDetailsService = mockk()
    val tokenQueryRepository: TokenQueryRepository = mockk()

    val jwtTokenFilter: JwtAuthenticationFilter = JwtAuthenticationFilter(jwtService, userDetailsService, tokenQueryRepository)

    Given("필터를 요청이 왔을 때") {
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        val filterChain: FilterChain = mockk(relaxed = true)
        val securityContext: SecurityContext = mockk()

        When("필터를 무시 하는 경로일 경우") {
            request.requestURI = "/api/auth/foo"

            jwtTokenFilter.doFilterInternal(request, response, filterChain)

            Then("SecurityContext 를 업데이트 하지 않는다") {
                verify(exactly = 0) { securityContext.authentication = any<Authentication>() }
                verify(exactly = 1) { filterChain.doFilter(request, response) }

            }
        }
    }

})
