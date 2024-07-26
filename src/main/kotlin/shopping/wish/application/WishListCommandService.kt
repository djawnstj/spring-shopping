package shopping.wish.application

import org.springframework.stereotype.Service
import shopping.product.application.ProductQueryService
import shopping.product.domain.Product
import shopping.wish.application.command.WishProductAddCommand
import shopping.wish.application.command.WishProductDeleteCommand
import shopping.wish.domain.WishList
import shopping.wish.domain.WishProduct

@Service
class WishListCommandService(
    private val wishListQueryRepository: WishListQueryRepository,
    private val wishListCommandRepository: WishListCommandRepository,
    private val productQueryService: ProductQueryService,
) {
    fun addWishProduct(memberId: Long, wishProductAddCommand: WishProductAddCommand) {
        val wishList = getWishListByMemberId(memberId)
        val wishProduct = productQueryService.findProductNotDeleted(wishProductAddCommand.productId).toWishProduct(wishList)

        wishList.addWishProduct(wishProduct)
    }

    fun deleteWishProduct(memberId: Long, wishProductDeleteCommand: WishProductDeleteCommand) {
        val wishList = getWishListByMemberId(memberId)
        val wishProduct = productQueryService.findProductNotDeleted(wishProductDeleteCommand.productId).toWishProduct(wishList)

        wishList.deleteWishProduct(wishProduct)
    }

    private fun getWishListByMemberId(memberId: Long): WishList =
        wishListQueryRepository.findByMemberIdAndNotDeleted(memberId).orCreate(memberId)

    private fun WishList?.orCreate(memberId: Long): WishList {
        if (this == null) {
            return wishListCommandRepository.save(WishList(memberId))
        }

        return this
    }

    private fun Product.toWishProduct(wishList: WishList): WishProduct = WishProduct(this, wishList)
}
