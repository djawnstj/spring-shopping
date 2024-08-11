package shopping.product.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.global.exception.ApplicationException
import shopping.global.exception.ErrorCode
import shopping.product.application.command.ProductCreateCommand
import shopping.product.application.command.ProductUpdateCommand
import shopping.product.domain.Product

@Service
@Transactional
class ProductCommandService(
    private val profanityValidator: ProfanityValidator,
    private val productCommandRepository: ProductCommandRepository,
    private val productQueryRepository: ProductQueryRepository,
) {
    fun createProduct(productCreateCommand: ProductCreateCommand): Product {
        validateProfanityProductName(productCreateCommand.productName)
        val product = productCreateCommand.toEntity()

        return productCommandRepository.save(product)
    }

    fun modifyProduct(productId: Long, productUpdateCommand: ProductUpdateCommand) {
        validateProfanityProductName(productUpdateCommand.productName)
        val product = findProductNotDeleted(productId)

        product.modify(productUpdateCommand.toEntity())
    }

    private fun validateProfanityProductName(productName: String) {
        if (profanityValidator.containsProfanity(productName)) {
            throw ApplicationException(ErrorCode.PROFANITY_CONTAIN_PRODUCT_NAME)
        }
    }

    fun deletedProduct(productId: Long) {
        val product = findProductNotDeleted(productId)
        product.delete()
    }

    private fun findProductNotDeleted(productId: Long): Product {
        productQueryRepository.findByIdAndNotDeleted(productId)?.let { return it }
        throw ApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
    }

}