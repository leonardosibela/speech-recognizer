package com.sibela.speechrecognizer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sibela.speechrecognizer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecognitionListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var speech: SpeechRecognizer
    private lateinit var actionRecognizeSpeechIntent: Intent

    companion object {
        const val RECORD_AUDIO_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBinding()
        setupSpeechRecognition()
        if (doesNotHaveRecordAudioPermission()) {
            askForAudioPermission()
        }
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startRecordingButton.setOnClickListener { onStartRecordingClicked() }
    }

    private fun onStartRecordingClicked() {
        speech.startListening(actionRecognizeSpeechIntent)
        binding.text.setText(R.string.listening)
    }

    private fun setupSpeechRecognition() {
        speech = SpeechRecognizer.createSpeechRecognizer(applicationContext)
        actionRecognizeSpeechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        actionRecognizeSpeechIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speech.setRecognitionListener(this)
    }

    private fun doesNotHaveRecordAudioPermission() = (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
    ) != PackageManager.PERMISSION_GRANTED)

    private fun askForAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    RECORD_AUDIO_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(binding.coordinator, "You must accept record audio permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Accept") { askForAudioPermission() }
                        .show()
            }
        }
    }

    override fun onBeginningOfSpeech() {
        Log.d("RecognitionListener", "onBeginningOfSpeech()")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.d("RecognitionListener", "onBufferReceived()")
    }

    override fun onEndOfSpeech() {
        Log.d("RecognitionListener", "onEndOfSpeech()")
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Log.d("RecognitionListener", "onReadyForSpeech()")
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.d("RecognitionListener", "onRmsChanged()")
    }

    override fun onError(error: Int) {
        binding.text.setText(R.string.could_not_understand)
    }

    override fun onResults(results: Bundle?) {
        results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.apply {
            val stringBuilder = StringBuilder()
            this.forEach { word ->
                stringBuilder.append("$word ")
            }
            val text = stringBuilder.toString().trim()
            binding.text.text = text
        }
    }

    override fun onPartialResults(partialResults: Bundle?) {
        Log.d("RecognitionListener", "onPartialResults()")
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.d("RecognitionListener", "onEvent()")
    }
}