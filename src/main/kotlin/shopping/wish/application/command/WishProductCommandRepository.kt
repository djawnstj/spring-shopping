package shopping.wish.application.command

import shopping.wish.domain.WishProduct

interface WishProductCommandRepository {
    fun save(wishProduct: WishProduct): WishProduct
}
