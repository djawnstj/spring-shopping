package shopping.member.application

import org.springframework.stereotype.Service
import shopping.global.exception.ApplicationException
import shopping.global.exception.ErrorCode
import shopping.member.domain.Member

@Service
class MemberQueryService(
    private val memberQueryRepository : MemberQueryRepository
) {

    fun findById(id: Long): Member =
        memberQueryRepository.findByIdAndNotDeleted(id) ?: throw ApplicationException(ErrorCode.MEMBER_NOT_FOUND)

}