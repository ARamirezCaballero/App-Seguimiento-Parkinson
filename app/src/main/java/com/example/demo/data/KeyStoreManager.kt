package com.example.demo.data

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.StrongBoxUnavailableException
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit


//clase que sirve para crear y almacenar claves de cifrado de datos en el dispositivo
//Es una clase singleton
//Tiene el vector de inicialización y el texto cifrado
//Guarda el blob (IV + texto cifrado) en SharedPreferences

@Singleton
class KeyStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    //Se lee y escribe en RAM en lugar de caché con null como valor inicial
    @Volatile
    private var cachedPassphrase: ByteArray? = null

    //generar una vez y utilizar la passphrase de la bd
    fun getOrCreateDatabasePassphrase(): ByteArray {
        //primera comprobación (reutilización de clave en memoria)
        cachedPassphrase?.let { return it.copyOf() }

        //segunda comprobación (para evitar ejecuciones en simultaneo)
        synchronized(this) {
            cachedPassphrase?.let { return it.copyOf() }

            //para recuperar el blob cifrado de la passphrase
            val sealed = readSealedPassphrase()

            //recuperar passphrase o generar una nueva
            val passphrase = if (sealed != null) {
                decrypt(sealed)
            } else {
                val generated = ByteArray(PASSPHRASE_SIZE_BYTES).also { SecureRandom().nextBytes(it) }
                persistSealedPassphrase(encrypt(generated))
                generated
            }

            //Guardar la passphrase en memoria
            cachedPassphrase = passphrase
            return passphrase.copyOf()
        }
    }

    //cifrado mediante AES GCM
    private fun encrypt(plaintext: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        val iv = cipher.iv
        val ciphertext = cipher.doFinal(plaintext)
        return iv + ciphertext
    }

    //descifrado de blob cifrado mediante AES GCM
    private fun decrypt(sealed: ByteArray): ByteArray {
        require(sealed.size > GCM_IV_LENGTH_BYTES) {
            "El blob cifrado de la passphrase está corrupto o es incompleto."
        }
        val iv = sealed.copyOfRange(0, GCM_IV_LENGTH_BYTES)
        val ciphertext = sealed.copyOfRange(GCM_IV_LENGTH_BYTES, sealed.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(
            Cipher.DECRYPT_MODE,
            getOrCreateSecretKey(),
            GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv)
        )
        return try {
            cipher.doFinal(ciphertext)
        } catch (error: Exception) {
            throw IllegalStateException(
                "No se pudo recuperar la passphrase de SQLCipher. " +
                        "Si se regenera, la base cifrada existente no podrá abrirse.",
                error
            )
        }
    }

    //obtener o crear el secret key
    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        val existing = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        if (existing != null) {
            return existing.secretKey
        }

        return try {
            generateSecretKey(useStrongBox = true)
        } catch (_: StrongBoxUnavailableException) {
            generateSecretKey(useStrongBox = false)
        } catch (_: Exception) {
            generateSecretKey(useStrongBox = false)
        }
    }

    //generar el secret key
    private fun generateSecretKey(useStrongBox: Boolean): SecretKey {
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setKeySize(AES_KEY_SIZE_BITS)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(true)
            .setIsStrongBoxBacked(useStrongBox)
            .build()

        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE).run {
            init(spec)
            generateKey()
        }
    }

    //persistencia de la passphrase cifrada
    private fun readSealedPassphrase(): ByteArray? {
        val encoded = prefs.getString(PREF_SEALED_PASSPHRASE, null) ?: return null
        return Base64.decode(encoded, Base64.NO_WRAP)
    }
    private fun persistSealedPassphrase(sealed: ByteArray) {
        prefs.edit {
            putString(PREF_SEALED_PASSPHRASE, Base64.encodeToString(sealed, Base64.NO_WRAP))
        }
    }

    //preferencias
    private val prefs
        get() = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    //claves de cifrado
    private companion object {
        const val ANDROID_KEYSTORE = "AndroidKeyStore"
        const val KEY_ALIAS = "sqlcipher_passphrase_aes"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val AES_KEY_SIZE_BITS = 256
        const val PASSPHRASE_SIZE_BYTES = 32
        const val GCM_IV_LENGTH_BYTES = 12
        const val GCM_TAG_LENGTH_BITS = 128
        const val PREFS_NAME = "keystore_secure_prefs"
        const val PREF_SEALED_PASSPHRASE = "db_passphrase_sealed"
    }
}

