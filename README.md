# SpeechRecognizer

A project created to explain how to use android [SpeechRecognizer](https://developer.android.com/reference/android/speech/SpeechRecognizer), a class that provides access to the speech recognition service.

## Android Manifest

First you must add Iternet and Record Audio permissions on your AndroidManifest.xml file:

```xml
<manifest ... >

    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
    <uses-permission android:name="android.permission.INTERNET"/>
```

## Implementation

Then you must create an Action Recognize Speech Intent:

```kotlin
val actionRecognizeSpeechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
actionRecognizeSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
```

And a SpeechRecognizer variable:

```kotlin
val speech = SpeechRecognizer.createSpeechRecognizer(applicationContext)
```

Then you must add a listener to your SpeechRecognizer object which is a class that implements the RecognitionListener interface:

```kotlin
speech.setRecognitionListener(this)
```

This interface has the mothods discribe in [this documentation](https://developer.android.com/reference/android/speech/RecognitionListener).
