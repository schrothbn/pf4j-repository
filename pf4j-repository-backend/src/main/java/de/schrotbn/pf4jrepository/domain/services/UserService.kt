package de.schrotbn.pf4jrepository.domain.services

import de.schrotbn.pf4jrepository.domain.UserDto
import de.schrotbn.pf4jrepository.domain.model.User
import de.schrotbn.pf4jrepository.domain.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val db : UserRepository, val passwordEncoder: PasswordEncoder){
    fun save(userDto : UserDto): User {
        val user = User(userDto)
        return this.save(user)
    }

    fun save(user : User) : User {
        user.password = passwordEncoder.encode(user.password)
        return db.save(user)
    }

    fun findAll() = db.findAll()

    fun findById(id : String) : User? = db.findById(id).orElse(null)

    fun delete(obj : User) = db.delete(obj)

    fun findByName(name : String) : User? = db.findByName(name)

    fun count() : Long = db.count()

    fun truncate() = db.deleteAll()
}