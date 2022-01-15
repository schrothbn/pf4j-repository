package de.schrotbn.pf4jrepository.domain.repositories

import de.schrotbn.pf4jrepository.domain.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun findByName(name: String): User?
}