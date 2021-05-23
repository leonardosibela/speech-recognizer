package com.sibela.speechrecognizer

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sibela.speechrecognizer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecognitionListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var speech: SpeechRecognizer
    private lateinit var actionRecognizeSpeechIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpeechRecognition()
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
        Log.d("RecognitionListener", "onError()")
    }

    override fun onResults(results: Bundle?) {
        Log.d("RecognitionListener", "onResults()")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        Log.d("RecognitionListener", "onPartialResults()")
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.d("RecognitionListener", "onEvent()")
    }
}