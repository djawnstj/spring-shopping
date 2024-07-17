package shopping.member.application

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.global.exception.ApplicationException
import shopping.global.exception.ErrorCode
import shopping.member.application.command.MemberCreateCommand
import shopping.member.domain.Member
import shopping.member.infra.MemberCommandRepository
import shopping.member.infra.MemberQueryRepository

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberCommandRepository: MemberCommandRepository,
    private val memberQueryRepository: MemberQueryRepository,
    private val passwordEncoder: PasswordEncoder
) {

//    fun createMember(memberCreateCommand: MemberCreateCommand): Long {
//        val member = memberCreateCommand.toEntity(passwordEncoder)
//
//        if (isAlreadyExistEmail(member)) {
//            throw ApplicationException(ErrorCode.DUPLICATED_REGISTER_EMAIL)
//        }
//
//        memberCommandRepository.save(member)
//
//
//    }

    private fun isAlreadyExistEmail(member: Member): Boolean {
        return memberQueryRepository.existsByEmail(member.email)
    }

}
