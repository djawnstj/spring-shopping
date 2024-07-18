package shopping.member.fixture

import shopping.member.application.command.MemberCreateCommand
import shopping.member.domain.Member
import shopping.member.domain.MemberType
import shopping.member.presentation.dto.request.MemberSignUpRequest

enum class MemberFixture(
    val email: String,
    val loginPassword: String,
    val memberType: MemberType
) {
    `고객 1`("member1@domain.com", "password", MemberType.CUSTOMER)
    ;

    fun `회원 엔티티 생성`(): Member = Member(email, loginPassword, memberType)
    fun `회원 가입 요청 DTO 생성`(): MemberCreateCommand = MemberSignUpRequest(email, loginPassword, memberType)
}
