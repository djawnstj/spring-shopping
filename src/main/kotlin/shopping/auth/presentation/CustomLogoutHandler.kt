package shopping.auth.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service
import shopping.auth.application.JwtService
import shopping.auth.application.TokenRepository
import shopping.global.common.ErrorResponse
import shopping.global.exception.ErrorCode

@Service
class CustomLogoutHandler(
    private val jwtService: JwtService,
    private val tokenRepository: TokenRepository,
    private val objectMapper: ObjectMapper
): LogoutHandler {

    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val authHeader = request?.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }

        val jti = jwtService.getJti(authHeader.substring(7))

        val savedJti = tokenRepository.findByJti(jti)

        if (savedJti == null) {
            setTokenNotFoundResponse(response)
            return
        }

        tokenRepository.deleteByJti(jti)
        SecurityContextHolder.clearContext()
    }

    private fun setTokenNotFoundResponse(response: HttpServletResponse?) {
        val tokenNotFoundErrorCode = ErrorCode.TOKEN_NOT_FOUND
        val responseEntity = ErrorResponse(tokenNotFoundErrorCode)
        val jsonResponse = objectMapper.writeValueAsString(responseEntity)

        response?.contentType = "application/json"
        response?.characterEncoding = "UTF-8"
        response?.status = tokenNotFoundErrorCode.status.value()
        response?.writer?.write(jsonResponse)
    }

}
