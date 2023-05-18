package com.example.core.utils

import java.math.BigInteger
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object HashGenerator {
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun hashPass(password: String): String {
        val salt = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, 512, 128)
        val f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hash = f.generateSecret(spec).encoded
        val bi = BigInteger(1, hash)
        val hex = bi.toString(16)
        val paddingLength = hash.size * 2 - hex.length
        return if (paddingLength > 0) String.format("%0" + paddingLength + "d", 0) + hex else hex
    }

}