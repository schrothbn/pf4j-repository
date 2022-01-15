package de.schrotbn.pf4jrepository.utils

import java.lang.StringBuilder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

object SHA512Hasher {
    fun getSHA512Hash(content: ByteArray?): String? {
        try {
            val md = MessageDigest.getInstance("SHA-512")
            val result = md.digest(content)
            val sb = StringBuilder()
            for (i in result.indices) {
                sb.append(((result[i] and 0xFF.toByte()) + 0x100).toString(16).substring(1))
            }
            return sb.toString()
        } catch (ex: NoSuchAlgorithmException) {
        }
        return null
    }
}