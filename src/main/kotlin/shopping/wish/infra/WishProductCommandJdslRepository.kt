package shopping.wish.infra

import org.springframework.stereotype.Repository
import shopping.wish.application.command.WishProductCommandRepository
import shopping.wish.domain.WishProduct

@Repository
class WishProductCommandJdslRepository(
    private val wishProductJpaRepository: WishProductJpaRepository,
) : WishProductCommandRepository {
    override fun save(wishProduct: WishProduct): WishProduct = wishProductJpaRepository.save(wishProduct)
}
