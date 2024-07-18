package shopping.auth.application

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val tokenRepository: TokenRepository,
): OncePerRequestFilter() {

    public override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        if (request.servletPath.contains(AUTH_URL)) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith(BEARER_HEADER_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        val username = jwtService.getUsername(jwt)
        val jti = jwtService.getJti(jwt)
        val authenticationCredentials = tokenRepository.findByJti(jti)

        if (authenticationCredentials?.accessToken == jwt && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)

            if (jwtService.isTokenValid(jwt, userDetails)) {
                updateContext(userDetails, request)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun updateContext(userDetails: UserDetails, request: HttpServletRequest) =
        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            .also {
                it.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = it
            }

    companion object {
        private const val AUTH_URL = "/api/auth"
        private const val BEARER_HEADER_PREFIX = "Bearer "
    }

}
