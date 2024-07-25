package shopping.wish.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import shopping.global.common.BaseEntity

@Entity
class WishList(
    memberId: Long,
) : BaseEntity() {
    @field:Column(name = "member_id", nullable = false)
    var memberId: Long = memberId
        protected set

    @field:Embedded
    var wishListProducts: WishListProducts = WishListProducts()
        protected set
}
