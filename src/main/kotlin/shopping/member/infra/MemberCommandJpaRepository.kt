package shopping.member.infra

import org.springframework.stereotype.Repository
import shopping.member.domain.Member

@Repository
class MemberCommandJpaRepository(
    private val memberJpaRepository: MemberJpaRepository
): MemberCommandRepository {

    override fun save(member: Member) {
        memberJpaRepository.save(member)
    }
}
