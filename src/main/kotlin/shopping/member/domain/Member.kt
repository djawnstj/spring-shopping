package shopping.member.domain

import jakarta.persistence.*
import shopping.global.common.BaseEntity

@Entity
@Table(
    name = "member",
    uniqueConstraints = [UniqueConstraint(name = "uk_member_email", columnNames = ["email"])]
)
class Member(
    @field:Column(name = "email", nullable = false, unique = true)
    val email: String?,
    @field:Column(name = "login_password", nullable = false)
    val loginPassword: String,
    @field:Column(name = "account_type", nullable = false)
    @field:Enumerated(EnumType.STRING)
    val memberType: MemberType
): BaseEntity() {
}
