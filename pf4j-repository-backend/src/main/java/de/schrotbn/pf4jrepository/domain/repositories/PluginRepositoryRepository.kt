package de.schrotbn.pf4jrepository.domain.repositories

import de.schrotbn.pf4jrepository.domain.model.PluginRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PluginRepositoryRepository : MongoRepository<PluginRepository, String> {
    fun findByName(name: String): PluginRepository?
}