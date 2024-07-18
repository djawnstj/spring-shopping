package shopping.member.presentation.dto.request

import org.springframework.security.crypto.password.PasswordEncoder
import shopping.member.application.command.MemberCreateCommand
import shopping.member.domain.Member
import shopping.member.domain.MemberType
import kotlin.math.log

class MemberSignUpRequest(
    override val email: String,
    override val loginPassword: String,
    override val memberType: MemberType
): MemberCreateCommand {
    override fun toEntity(passwordEncoder: PasswordEncoder): Member = Member(email, loginPassword, memberType)
}
