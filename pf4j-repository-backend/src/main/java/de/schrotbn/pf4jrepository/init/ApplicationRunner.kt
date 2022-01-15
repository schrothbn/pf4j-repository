package de.schrotbn.pf4jrepository.init

import de.schrotbn.pf4jrepository.config.RepositoryProperties
import de.schrotbn.pf4jrepository.domain.model.PluginRepository
import de.schrotbn.pf4jrepository.domain.model.User
import de.schrotbn.pf4jrepository.domain.model.UserRole
import de.schrotbn.pf4jrepository.domain.repositories.PluginRepositoryRepository
import de.schrotbn.pf4jrepository.domain.repositories.UserRepository
import de.schrotbn.pf4jrepository.domain.services.RepositoryUserService
import de.schrotbn.pf4jrepository.domain.services.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Component
@Profile("dev", "docker")
class ApplicationRunner  (
    private val repository: PluginRepositoryRepository,
    private val userService: UserService,
    private val properties: RepositoryProperties
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (repository.findByName("default") == null) {
            val repo = PluginRepository();
            repo.name = "default";
            repository.insert(repo);
        }
        val pluginPath = Path.of(properties.basePath + File.separator+"artifacts");

        if (Files.exists(pluginPath)) {
            Files.createDirectories(pluginPath);
        }

        if (userService.count() == 0L) {
            val admin = User(
               "admin" ,
                "admin"
            );
            admin.addRole(UserRole("ROLE_ADMIN"));

            val user = User(
               "user",
               "user"
            );
            user.addRole(UserRole("ROLE_USER"));

            userService.save(admin);
            userService.save(user);
        }
    }

}