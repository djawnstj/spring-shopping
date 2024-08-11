package shopping.wish.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany
import shopping.global.exception.ApplicationException
import shopping.global.exception.ErrorCode
import shopping.product.domain.Product

@Embeddable
class WishProducts(
    @field:OneToMany
    private val wishProducts: MutableList<Product> = mutableListOf()
) : Iterable<Product> by wishProducts {
    fun add(wishProduct: Product) {
        this.wishProducts.add(wishProduct)
    }

    fun delete(wishProduct: Product) {
        this.wishProducts.find(wishProduct::equals)?.let {
            this.wishProducts.remove(it)
            return
        }

        throw ApplicationException(ErrorCode.WISH_PRODUCT_NOT_FOUND)
    }
}
