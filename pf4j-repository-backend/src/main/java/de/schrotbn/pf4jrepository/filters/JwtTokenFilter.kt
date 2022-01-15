package de.schrotbn.pf4jrepository.filters

import de.schrotbn.pf4jrepository.config.JwtTokenUtil
import de.schrotbn.pf4jrepository.domain.model.User
import de.schrotbn.pf4jrepository.domain.repositories.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(private val jwtTokenUtil : JwtTokenUtil,
                     private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = header.split(' ')[1].trim()
        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response)
            return
        }
        var userName = jwtTokenUtil.getUserName(token)
        val userDetails : User? = userRepository.findByName(userName!!)
        val auth =  UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails?.authorities ?: mutableListOf()
        )

        auth.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = auth
        filterChain.doFilter(request, response)
    }
}