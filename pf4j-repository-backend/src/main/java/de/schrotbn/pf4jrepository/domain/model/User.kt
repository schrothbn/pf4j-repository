package de.schrotbn.pf4jrepository.domain.model

import de.schrotbn.pf4jrepository.domain.UserDto
import lombok.AllArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.UserDetails
import de.schrotbn.pf4jrepository.domain.model.UserRole
import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.ArrayList
import java.util.stream.Collectors

@Document
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
class User(@Id var name: String, private var password: String) : UserDetails {
    private var roles: MutableList<UserRole>? = ArrayList()
    var locked = false
    var enabled = true

    constructor(userDto: UserDto) : this(userDto.name!!, userDto.password!!)

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return roles!!.stream()
            .map { role: UserRole -> SimpleGrantedAuthority(role.name) }
            .collect(Collectors.toList())
    }

    override fun getPassword(): String = password

    fun setPassword(password : String) {
        this.password = password
    }

    override fun getUsername(): String = name

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = !locked

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = enabled

    fun addRole(role: UserRole) {
        if (roles == null) roles = ArrayList()
        roles!!.add(role)
    }
}