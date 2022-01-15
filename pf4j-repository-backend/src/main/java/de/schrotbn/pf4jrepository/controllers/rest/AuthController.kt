package de.schrotbn.pf4jrepository.controllers.rest

import de.schrotbn.pf4jrepository.config.JwtTokenUtil
import de.schrotbn.pf4jrepository.domain.dto.AuthRequest
import de.schrotbn.pf4jrepository.domain.dto.UserView
import de.schrotbn.pf4jrepository.domain.mappers.UserViewMapper
import de.schrotbn.pf4jrepository.domain.model.User
import de.schrotbn.pf4jrepository.domain.services.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authManager : AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val userViewMapper : UserViewMapper,
    private val userService: UserService
) {

    @PostMapping("login")
    fun login(@RequestBody request : AuthRequest) : ResponseEntity<UserView> {
        try {
            val auth = authManager.authenticate(UsernamePasswordAuthenticationToken(request.username, request.password))

            val user  = auth.principal as User

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.gnerateAccessToken(user))
                .body(userViewMapper.toUserView(user))
        } catch (ex : BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

}