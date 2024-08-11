package shopping.wish.fixture

import shopping.product.domain.Product
import shopping.product.fixture.ProductFixture
import shopping.wish.domain.WishProduct
import shopping.wish.domain.WishProducts

enum class WishProductsFixture(
    val wishProducts: Set<ProductFixture>
) {
    `위시 리스트 상품 1개 목록`(setOf(ProductFixture.`상품 1`))
    ;

    fun `객체 생성`(): WishProducts = WishProducts(wishProducts.map { it.`상품 엔티티 생성`() }.toMutableList())
    fun `객체 생성`(vararg wishProduct: Product): WishProducts = WishProducts(wishProduct.toMutableList())
}
