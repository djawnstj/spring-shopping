package shopping.member.fixture

import shopping.member.application.command.MemberCreateCommand
import shopping.member.domain.Member
import shopping.member.domain.MemberType
import shopping.member.presentation.dto.request.MemberRegisterRequest

enum class MemberFixture(
    val email: String?,
    val loginPassword: String?,
    val memberType: MemberType?,
) {
    // 정상 객체
    `고객 1`("member1@domain.com", "password", MemberType.CUSTOMER),

    // 비정상 객체
    `비밀번호 공백 회원`("member1@domain.com", "", MemberType.CUSTOMER),
    `비밀번호 NULL 회원`("member1@domain.com", null, MemberType.CUSTOMER),
    ;

    fun `회원 엔티티 생성`(): Member = Member(email!!, loginPassword!!, memberType!!)
    fun `회원 가입 요청 DTO 생성`(): MemberRegisterRequest = MemberRegisterRequest(email, loginPassword, memberType)
    fun `회원 생성 COMMAND 생성`(): MemberCreateCommand = MemberCreateCommand(email!!, loginPassword!!, memberType!!)
}