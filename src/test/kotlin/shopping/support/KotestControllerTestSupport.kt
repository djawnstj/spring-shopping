package shopping.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import shopping.member.application.MemberCommandService
import shopping.member.application.MemberQueryService
import shopping.member.presentation.MemberApi

@WebMvcTest(
    controllers = [
        MemberApi::class
    ]
)
@MockkBean(JpaMetamodelMappingContext::class)
abstract class KotestControllerTestSupport: BehaviorSpec() {

    @Autowired
    protected lateinit var mockMvc: MockMvc
    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @MockkBean
    protected lateinit var memberCommandService: MemberCommandService
    @MockkBean
    protected lateinit var memberQueryService: MemberQueryService

    init {
        afterContainer {
            clearAllMocks()
        }
    }

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    protected fun ResultActions.isStatusAs(status: HttpStatus): ResultActions =
        this.andExpectAll(
            MockMvcResultMatchers.status().`is`(status.value()),
            MockMvcResultMatchers.jsonPath("$.meta.code").value(status.value()),
            MockMvcResultMatchers.jsonPath("$.meta.message").value(status.reasonPhrase)
        )

    protected fun ResultActions.isOkResponse(): ResultActions = isStatusAs(HttpStatus.OK)

}
