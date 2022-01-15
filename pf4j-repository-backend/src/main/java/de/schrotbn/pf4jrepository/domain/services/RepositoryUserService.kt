package de.schrotbn.pf4jrepository.domain.services

import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class RepositoryUserService(private val userService: UserService) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByName(username)
        if (user != null) {
            return user
        }
        throw UsernameNotFoundException("$username couldn't be found!")
    }
}