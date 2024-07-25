package shopping.wish.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class WishListProducts(
    @field:OneToMany
    private val products: MutableSet<WishProduct> = mutableSetOf()
) : Set<WishProduct> by products {
}
