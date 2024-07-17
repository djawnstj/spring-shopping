package shopping.member.application.command

import org.springframework.security.crypto.password.PasswordEncoder
import shopping.member.domain.Member
import shopping.member.domain.MemberType

interface MemberCreateCommand {
    val email: String
    val loginPassword: String
    val memberType: MemberType

    fun toEntity(passwordEncoder: PasswordEncoder): Member
}
