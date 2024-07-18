package shopping.member.infra

import shopping.member.domain.Member

interface MemberQueryRepository {
    fun existsByEmail(email: String?): Boolean
    fun findByIdAndNotDeleted(id: Long): Member?
}
