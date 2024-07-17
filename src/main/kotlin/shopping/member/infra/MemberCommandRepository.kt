package shopping.member.infra

import shopping.member.domain.Member

interface MemberCommandRepository {
    fun save(member: Member)
}
