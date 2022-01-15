# Repository for PF4J

pf4j and pf4j-update compatible repository-server implementation, consisting of a
backend implementation based on **Kotlin** and **Spring-Boot** and a **Vue.js**-based UI.

# Backend
REST-based API implemented using Kotlin and Spring-Boot to Upload Plugins (currently only supported in JAR-packaging). 
Autodetects Plugin- properties in the Manifest.mf-File and manages different versions of the same plugin. 

The backend stores uploaded plugins and its metadata in a MongoDB-instance. 

Provides a generated **plugins.json** to provide the necessary information for the repositories of a pf4j-update-based implementation.

The provided docker-compose.yml can be used to start the backend-server. It uses the docker-image, that can be built using maven and the spring-boot:build-image goal.

## API-Usage


# UI 
WIP
