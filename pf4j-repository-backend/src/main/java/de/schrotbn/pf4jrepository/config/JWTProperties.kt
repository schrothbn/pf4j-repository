package de.schrotbn.pf4jrepository.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt")
class JWTProperties() {
    var secret: String = "8X47XHCNMB4RYTDF2PW2WDLLM8HJRWDS"
    var expirationTime: Long = 900_000
    var tokenPrefix: String = "Bearer"
    var header: String = "Authorization"
    var issuer: String = "de.schrothbn.pf4jrepository"
    var signUpUrl: String = "/auth/login"
}
