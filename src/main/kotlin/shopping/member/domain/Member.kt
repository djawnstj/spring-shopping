package shopping.member.domain

import jakarta.persistence.*
import shopping.global.common.BaseEntity

@Entity
@Table(
    name = "member",
    uniqueConstraints = [UniqueConstraint(name = "uk_member_email", columnNames = ["email"])]
)
class Member(
    email: String,
    loginPassword: String,
    memberType: MemberType
): BaseEntity() {
    @field:Column(name = "email", nullable = false, unique = true)
    var email: String = email
        protected set
    @field:Column(name = "login_password", nullable = false)
    var loginPassword: String = loginPassword
        protected set
    @field:Column(name = "account_type", nullable = false)
    @field:Enumerated(EnumType.STRING)
    var memberType: MemberType = memberType
        protected set
}
