package shopping.member.presentation.dto.response

import shopping.member.domain.Member
import shopping.member.domain.MemberType

class MemberSignUpResponse(member: Member) {

    val id: Long = member.id
    val email: String = member.email
    val memberType: MemberType = member.memberType

}
