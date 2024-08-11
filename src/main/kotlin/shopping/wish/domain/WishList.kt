package shopping.wish.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table
import shopping.global.common.BaseEntity
import shopping.product.domain.Product

@Entity
@Table(name = "wish_list")
class WishList(
    memberId: Long
) : BaseEntity() {
    @field:Column(name = "member_id", nullable = false)
    var memberId: Long = memberId
        protected set

    @field:Embedded
    private val wishProducts: WishProducts = WishProducts()

    fun addWishProduct(wishProduct: Product) {
        this.wishProducts.add(wishProduct)
    }

    fun deleteWishProduct(wishProduct: Product) {
        this.wishProducts.delete(wishProduct)
    }

    fun getWishProducts(): Set<Product> = wishProducts.toSet()
}
