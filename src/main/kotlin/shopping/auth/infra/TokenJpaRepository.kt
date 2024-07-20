package shopping.auth.infra

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shopping.auth.domain.AuthenticationCredentials

@Repository
interface TokenJpaRepository : JpaRepository<AuthenticationCredentials, Long>, KotlinJdslJpqlExecutor
