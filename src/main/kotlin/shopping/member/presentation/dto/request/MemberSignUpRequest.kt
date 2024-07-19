package shopping.member.presentation.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.security.crypto.password.PasswordEncoder
import shopping.global.common.ValidEnum
import shopping.member.application.command.MemberCreateCommand
import shopping.member.domain.Member
import shopping.member.domain.MemberType
import kotlin.math.log

data class MemberRegisterRequest(
    @field:Email
    val email: String?,
    @field:NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.")
    val loginPassword: String?,
    @field:ValidEnum(enumClass = MemberType::class, message = "올바른 회원 구분이 아닙니다.")
    val memberType: MemberType?
) {
    fun toCommand(): MemberCreateCommand = MemberCreateCommand(email!!, loginPassword!!, memberType!!)
}
