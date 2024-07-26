package shopping.wish.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import shopping.global.common.BaseEntity

@Entity
class WishList(
    memberId: Long
) : BaseEntity() {
    @field:Column(name = "member_id", nullable = false)
    var member_id: Long = memberId
        protected set

    @field:Embedded
    private val wishProducts: WishProducts = WishProducts()

    fun addWishProduct(wishProduct: WishProduct) {
        this.wishProducts.add(wishProduct)
    }

    fun deleteWishProduct(wishProduct: WishProduct) {
        this.wishProducts.delete(wishProduct)
    }
}
