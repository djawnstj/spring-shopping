package shopping.wish.domain

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import shopping.global.common.BaseEntity
import shopping.product.domain.Product

@Entity
@Table(name = "wish_product")
class WishProduct(
    product: Product,
) : BaseEntity() {
    @field:ManyToOne
    @field:JoinColumn(name = "product_id", nullable = false)
    var product: Product = product
        protected set

    fun isSameWishProduct(wishProduct: WishProduct): Boolean = this.product.id == wishProduct.product.id
}
