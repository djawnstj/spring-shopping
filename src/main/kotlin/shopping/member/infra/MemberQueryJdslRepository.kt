package shopping.member.infra

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import shopping.member.domain.Member

@Repository
class MemberQueryJdslRepository(
    private val memberJpaRepository: MemberJpaRepository,
    private val entityManager: EntityManager,
    private val jpqlRenderContext: RenderContext,
): MemberQueryRepository {
    override fun existsByEmail(email: String?): Boolean {
        val query = jpql {
            select(
                entity(Member::class)
            ).from(
                entity(Member::class)
            ).where(
                path(Member::email).eq(email)
            )
        }

        return entityManager
            .createQuery(query, jpqlRenderContext)
            .apply { setMaxResults(1) }
            .resultList.isNotEmpty()
    }
}
