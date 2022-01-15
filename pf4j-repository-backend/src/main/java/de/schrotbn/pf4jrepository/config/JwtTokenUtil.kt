package de.schrotbn.pf4jrepository.config

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import de.schrotbn.pf4jrepository.domain.model.User
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil(private val jwtProperties: JWTProperties) {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val alg = Algorithm.HMAC512(jwtProperties.secret)
    fun gnerateAccessToken(user: User) : String? {
        try {
            val token = JWT.create()
                .withSubject(user.username)
                .withIssuedAt(Date())
                .withExpiresAt(Date(System.currentTimeMillis() + jwtProperties.expirationTime))
                .withIssuer(jwtProperties.issuer)
                .sign(alg);
            return token
        } catch(ex : JWTCreationException) {
            log.error("Couldn't generate token ${ex.message}")
        }
        return null
    }

    fun getUserName(token : String) : String? {
        val decoded = decode(token)
        return decoded?.subject
    }

    fun decode(token : String) : DecodedJWT? {
        try {
            return JWT.decode(token)
        } catch (ex : JWTDecodeException) {
            log.error("Couldn't decode jwt $token ${ex.message}")
        }
        return null
    }

    fun validate(token : String) : Boolean {
        try {
            val verifier = JWT.require(alg)
                .withIssuer(jwtProperties.issuer)
                .build();
            verifier.verify(token)
        } catch (ex : JWTVerificationException) {
            log.error("Couldn't verify token ${token}, ${ex.message}")
            return false
        }
        return true;
    }
}