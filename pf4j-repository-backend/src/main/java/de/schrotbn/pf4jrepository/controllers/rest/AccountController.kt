package de.schrotbn.pf4jrepository.controllers.rest

import de.schrotbn.pf4jrepository.domain.model.Roles
import de.schrotbn.pf4jrepository.domain.model.User
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import lombok.RequiredArgsConstructor
import de.schrotbn.pf4jrepository.domain.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/accounts")
class AccountController(private val db : UserRepository,
                             private val passwordEncoder : PasswordEncoder) {

    @GetMapping("")
    fun index(): List<User> = db.findAll()

    @GetMapping("/{name}")
    fun findOne(@PathVariable name: String): User? = db.findByName(name)

    @RequestMapping(value = [""], method = [RequestMethod.POST, RequestMethod.PUT])
    fun createOrUpdate(@RequestBody user: User): User =  db.save(user)

    @PutMapping("/{id}/password")
    fun changePassword(@PathVariable("id") id: String, @RequestBody password: String?): User? {
        val user = db.findById(id).orElse(null)
        if (user != null) {
            user.password = passwordEncoder.encode(password)
            return db.save(user)
        }
        return null
    }
}