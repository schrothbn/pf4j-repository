package de.schrotbn.pf4jrepository.domain.model

import lombok.Data
import java.io.File
import java.time.LocalDateTime

@Data
class Plugin {
    var id: String? = null
    var description = ""
    var provider = "Unknown"
    var projectUrl = "Unknown"
    var releases: MutableList<PluginRelease> = ArrayList()
    fun addRelease(pluginVersion: String, name: String, sha: String, repository: PluginRepository) {
        val release = PluginRelease(
            pluginVersion,
            LocalDateTime.now(),
            sha,
            repository.name + "/plugins" + File.separator + id + File.separator + pluginVersion + File.separator + name,
        )
        releases.add(release)
    }

    fun hasRelease(pluginVersion: String?): Boolean {
        return releases.stream().anyMatch { it: PluginRelease -> it.version == pluginVersion }
    }
}