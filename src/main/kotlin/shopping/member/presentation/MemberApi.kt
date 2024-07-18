package shopping.member.presentation

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import shopping.global.common.SuccessResponse
import shopping.member.application.MemberCommandService
import shopping.member.application.MemberQueryService
import shopping.member.presentation.dto.request.MemberSignUpRequest
import shopping.member.presentation.dto.response.MemberSignUpResponse

@RestController
class MemberApi(
    private val memberCommandService: MemberCommandService,
    private val memberQueryService: MemberQueryService
) {

    @PostMapping("/api/member")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody request: MemberSignUpRequest): SuccessResponse<MemberSignUpResponse> {
        val id = memberCommandService.createMember(request)
        return SuccessResponse(MemberSignUpResponse(memberQueryService.findById(id)), HttpStatus.CREATED)
    }

}
