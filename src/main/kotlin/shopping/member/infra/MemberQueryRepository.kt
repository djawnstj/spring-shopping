package shopping.member.infra

interface MemberQueryRepository {
    fun existsByEmail(email: String?): Boolean
}
