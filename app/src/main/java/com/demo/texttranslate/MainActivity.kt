package com.demo.texttranslate

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.texttranslate.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    private lateinit var englishHindiTranslator: Translator
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an English-Hindi translator:
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()
        englishHindiTranslator = Translation.getClient(options)

        binding.idBtnTranslateLanguage.setOnClickListener {
            val conditions = DownloadConditions.Builder()
                .requireWifi()
                .build()
            englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                    // Model downloaded successfully. Okay to start translating.
                    val sourceText = binding.idEdtLanguage.text.toString().trim()
                    translateText(sourceText)
                }
                .addOnFailureListener { exception ->
                    // Model couldn’t be downloaded or other internal error.
                    Toast.makeText(this@MainActivity, "Fail to download modal", Toast.LENGTH_SHORT)
                        .show();
                }
        }
    }

    private fun translateText(text: String) {
        englishHindiTranslator.translate(text)
            .addOnSuccessListener { translatedText ->
                // Translation successful.
                binding.idTVTranslatedLanguage.text = translatedText;
            }
            .addOnFailureListener { exception ->
                // Error.
                Toast.makeText(this@MainActivity, "Fail to translate", Toast.LENGTH_SHORT).show();
            }
    }
}