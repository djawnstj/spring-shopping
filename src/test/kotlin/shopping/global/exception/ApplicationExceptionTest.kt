package shopping.global.exception

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@DisplayName("공통 예외 클래스 테스트")
class ApplicationExceptionTest {

    @Nested
    inner class `예외 인스턴스 변수가 생성될 때` {

        @Nested
        inner class `전달 받은 message 가 있다면` {

            @Test
            fun `전달 받은 message 를 프로퍼티로 갖는다`() {
                val applicationException = ApplicationException(ErrorCode.DUPLICATED_REGISTER_EMAIL, message = "이미 존재합니다.")

                assertThat(applicationException.message).isEqualTo("이미 존재합니다.")
            }
        }

        @Nested
        inner class `전달 받은 message 가 없다면` {

            private val applicationException = ApplicationException(ErrorCode.DUPLICATED_REGISTER_EMAIL)

            @Test
            fun `ErrorCode 의 message 를 프로퍼티로 갖는다`() {
                assertThat(applicationException.message).isEqualTo("이미 존재하는 이메일 입니다.")
            }
        }
    }

    @Test
    fun `에러 로그를 생성한다`() {
        val printStream = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        ApplicationException(ErrorCode.DUPLICATED_REGISTER_EMAIL)

        System.setOut(printStream)

        assertThat(outputStream.toString().trimIndent()).isEqualTo(
"""
server error
cause: null
message: 이미 존재하는 이메일 입니다.
errorCode: DUPLICATED_REGISTER_EMAIL
""".trimIndent()
        )
    }
}
