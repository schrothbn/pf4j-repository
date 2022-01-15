package de.schrotbn.pf4jrepository.controllers.rest

import org.springframework.web.bind.annotation.RestController
import lombok.RequiredArgsConstructor
import de.schrotbn.pf4jrepository.domain.repositories.PluginRepositoryRepository
import de.schrotbn.pf4jrepository.config.RepositoryProperties
import de.schrotbn.pf4jrepository.domain.model.Plugin
import de.schrotbn.pf4jrepository.domain.model.Roles
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import kotlin.Throws
import java.io.IOException
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse
import java.util.Locale
import java.util.jar.JarInputStream
import de.schrotbn.pf4jrepository.utils.SHA512Hasher
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import javax.annotation.security.RolesAllowed

@RestController
class RepositoryController(
    private val repository: PluginRepositoryRepository,
    private val repositoryProperties: RepositoryProperties
) {

    @GetMapping(value = ["/{repository}/plugins.json"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPlugins(@PathVariable("repository") repositoryName: String?): List<Plugin> {
        val repository = repository.findByName(repositoryName!!)
        return repository!!.plugins
    }

    @PostMapping(value = ["/{repository}/upload"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(
        IOException::class
    )
    fun uploadPlugin(
        @PathVariable("repository") repositoryName: String?,
        @RequestParam("plugin") file: MultipartFile,
        response: HttpServletResponse
    ): Plugin? {
        val repository = repository.findByName(repositoryName!!)
        if (file.originalFilename?.lowercase(Locale.getDefault())!!.endsWith(".jar")) {
            val jarInputStream = JarInputStream(file.inputStream)
            val manifest = jarInputStream.manifest
            val mainAttributes = manifest.mainAttributes
            val pluginId = mainAttributes.getValue("Plugin-Id")
            val pluginDescription = mainAttributes.getValue("Plugin-Description")
            val pluginVersion = mainAttributes.getValue("Plugin-Version")
            val provider = mainAttributes.getValue("Plugin-Provider")
            val projectUrl = mainAttributes.getValue("Plugin-ProjectUrl")
            var plugin = repository!!.getPluginById(pluginId)
            if (plugin == null) {
                plugin = Plugin()
                plugin.id = pluginId
                plugin.description = pluginDescription ?: "No description given"
                plugin.provider = provider ?: "Unknown"
                plugin.projectUrl = projectUrl ?: "Unknown"
                repository.addPlugin(plugin)
                Files.createDirectories(Path.of(repositoryProperties.basePath + "artifacts" + File.separator + pluginId))
            }
            return if (!plugin.hasRelease(pluginVersion)) {
                val path =
                    Path.of(repositoryProperties.basePath + "artifacts" + File.separator + pluginId + File.separator + pluginVersion)
                if (!Files.exists(path)) {
                    Files.createDirectories(path)
                }
                val filePath = Path.of(path.toString() + File.separator + file.originalFilename)
                if (Files.exists(filePath)) {
                    Files.delete(filePath)
                }
                Files.copy(file.inputStream, filePath)
                val shaHash = SHA512Hasher.getSHA512Hash(file.bytes)
                if (shaHash != null) {
                    plugin.addRelease(pluginVersion, file.originalFilename!!, shaHash, repository)
                    this.repository.save(repository)
                }
                plugin
            } else {
                response.status = HttpStatus.BAD_REQUEST.value()
                return null
            }
        }
        return null
    }

    @GetMapping(
        value = ["{repositoryName}/plugins/{pluginId}/{version}/{fileName}"],
        produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    @Throws(
        IOException::class
    )
    fun getPlugin(
        @PathVariable("repositoryName") repositoryName: String?,
        @PathVariable("pluginId") pluginId: String,
        @PathVariable("version") version: String,
        @PathVariable("fileName") filename: String,
        response: HttpServletResponse
    ) {
        val file =
            repositoryProperties.basePath + "artifacts" + File.separator + pluginId + File.separator + version + File.separator + filename
        IOUtils.copyLarge(FileInputStream(file), response.outputStream)
    }
}