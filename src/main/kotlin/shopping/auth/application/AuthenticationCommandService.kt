package shopping.auth.application

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.auth.application.command.LoginCommand
import shopping.auth.application.command.TokenRefreshCommand
import shopping.auth.domain.AuthenticationCredentials
import shopping.global.exception.ApplicationException
import shopping.global.exception.ErrorCode
import shopping.member.application.MemberQueryRepository
import shopping.member.domain.Member

@Service
@Transactional(readOnly = true)
class AuthenticationCommandService(
    private val tokenCommandRepository: TokenCommandRepository,
    private val tokenQueryRepository: TokenQueryRepository,
    private val memberQueryRepository: MemberQueryRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) {

    @Transactional
    fun logIn(loginCommand: LoginCommand): AuthenticationCredentials {
        val member: Member = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginCommand.email, loginCommand.loginPassword)
        ).principal as Member

        val authenticationCredentials = jwtService.generateAuthenticationCredentials(member)

        tokenCommandRepository.save(authenticationCredentials)

        return authenticationCredentials
    }

    // TODO 기존 토큰 어떻게 삭제?
    @Transactional
    fun refreshToken(request: TokenRefreshCommand): AuthenticationCredentials {
        val presentedRefreshToken = request.refreshToken

        val member = memberQueryRepository.findByEmailAndNotDeleted(jwtService.getUsername(presentedRefreshToken))

        val foundToken = tokenQueryRepository.findByJti(jwtService.getJti(presentedRefreshToken))
        validateToken(foundToken, presentedRefreshToken, member)

        val authenticationCredentials = jwtService.generateAuthenticationCredentials(member!!)

        tokenCommandRepository.save(authenticationCredentials)
        tokenCommandRepository.delete(foundToken!!)

        return authenticationCredentials
    }

    private fun validateToken(authenticationCredentials: AuthenticationCredentials?, jwt: String, member: Member?) {
        if (authenticationCredentials == null) {
            throw ApplicationException(ErrorCode.TOKEN_NOT_FOUND)
        }

        validateMember(member)

        validateRefreshToken(jwt, authenticationCredentials.refreshToken, member!!)

        validateActiveAccessToken(authenticationCredentials.accessToken, authenticationCredentials.jti)
    }

    private fun validateMember(member: Member?) {
        if (member == null) {
            throw ApplicationException(ErrorCode.MEMBER_NOT_FOUND)
        }
    }

    private fun validateRefreshToken(jwt: String, refreshToken: String, user: UserDetails) {
        if (jwt != refreshToken) throw ApplicationException(ErrorCode.MISS_MATCH_TOKEN)
        if (!jwtService.isTokenValid(jwt, user)) throw ApplicationException(ErrorCode.INVALID_REFRESH_TOKEN)
    }

    private fun validateActiveAccessToken(accessToken: String, jti: String) {
        if (!jwtService.checkTokenExpiredByTokenString(accessToken)) {
            tokenCommandRepository.deleteByJti(jti)
            throw ApplicationException(ErrorCode.INVALID_TOKEN_REISSUE_REQUEST)
        }
    }

}



