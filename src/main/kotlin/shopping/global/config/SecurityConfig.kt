package shopping.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
class SecurityConfig(
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun mvcRequestMatcherBuilder(introspector: HandlerMappingIntrospector): MvcRequestMatcher.Builder {
        return MvcRequestMatcher.Builder(introspector)
    }

    companion object {
        private val WHITE_LIST_URI_PREFIX = arrayOf(("/api/members" to HttpMethod.POST))
    }

    private fun createMvcRequestMatcher(uriMethodMapping: Pair<String, HttpMethod>, mvc: MvcRequestMatcher.Builder, method: HttpMethod? = null): MvcRequestMatcher =
        mvc.pattern(uriMethodMapping.first).apply { setMethod(uriMethodMapping.second) }

    private fun createMvcRequestMatcherForWhitelist(mvc: MvcRequestMatcher.Builder): Array<MvcRequestMatcher> =
        WHITE_LIST_URI_PREFIX.map { createMvcRequestMatcher(it, mvc) }.toTypedArray()

}
