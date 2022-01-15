package de.schrotbn.pf4jrepository.domain.model

import lombok.Data
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.ArrayList

@Document
@Data
class PluginRepository {
    @Id
    var id: String? = null
    var name: String? = null
    var plugins: MutableList<Plugin> = ArrayList()

    fun getPluginById(id: String?): Plugin? {
        return plugins.stream().filter {
            it.id == id
        }.findFirst().orElse(null)
    }

    fun addPlugin(plugin: Plugin) {
        plugins.add(plugin)
    }
}