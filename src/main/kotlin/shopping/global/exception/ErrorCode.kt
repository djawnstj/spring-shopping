package shopping.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {

    // MEMBER
    DUPLICATED_REGISTER_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다."),
}
