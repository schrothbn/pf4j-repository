package de.schrotbn.pf4jrepository.domain.model

import lombok.Data
import java.time.LocalDate
import java.time.LocalDateTime

@Data
data class PluginRelease(
    var version : String,
    var date : LocalDateTime,
    var sha512sum : String,
    var url : String
) {}