package shopping.wish.presentation.dto.response

import shopping.product.domain.Product
import shopping.product.presentation.dto.response.ProductResponse
import shopping.wish.domain.WishProduct

class WishListResponse(
    wishList: Set<Product>
) {
    val wishList = wishList.map { wishProduct -> ProductResponse(wishProduct) }
}
