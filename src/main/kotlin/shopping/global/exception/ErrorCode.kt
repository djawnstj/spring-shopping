package shopping.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {

    // MEMBER
    DUPLICATED_REGISTER_EMAIL(HttpStatus.CONFLICT, "이미 존재 하는 이메일 입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 회원 정보를 찾을 수 없습니다."),
}
