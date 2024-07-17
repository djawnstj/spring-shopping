package shopping.global.exception

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

class ApplicationException @JvmOverloads constructor(
    val errorCode: ErrorCode,
    override val cause: Throwable? = null,
    message: String? = null,
): RuntimeException() {
    override val message: String = message ?: errorCode.message

    init {
        log.error {
            """
                server error
                cause: $cause
                message: ${message ?: errorCode.message}
                errorCode: $errorCode
            """.trimIndent()
        }
    }

    companion object {
        private val log: KLogger = KotlinLogging.logger {  }
    }
}
