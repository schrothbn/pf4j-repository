package de.schrotbn.pf4jrepository.config

import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("pf4j.repository")
@Data
class RepositoryProperties {
    /**
     * Default repository name
     */
    val repositoryName: String = "default"

    /**
     * Storage base-path for saving artifacts
     */
    val basePath : String = "/opt/pf4j"
}