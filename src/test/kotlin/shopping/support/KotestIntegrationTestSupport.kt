package shopping.support

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks
import io.mockk.clearMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotestIntegrationTestSupport: BehaviorSpec() {

    @Autowired
    private lateinit var cleanUp: InfraCleanUp

    init {
        afterEach {
            cleanUp.all()
        }

        afterContainer {
            clearAllMocks()
        }
    }

    override fun extensions(): List<Extension> = listOf(SpringExtension)
}
