# Detailed Class Description: FeedbackFragment

________________________________________
## 1. General Information
•	**Class Name:** FeedbackFragment  
•	**Type:** Fragment  
•	**Purpose:** Allows the user to provide feedback in three ways: typing text, dictating it using voice recognition (Speech-to-Text), or recording a raw audio message.  
•	**Interaction:** Utilizes system services for speech recognition and audio recording. It saves text-based feedback into a local file named `feedback.txt`.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `etFeedback` | `EditText` | Field for typing or displaying recognized text. | Used in saving and STT logic. |
| `speechLauncher` | `ActivityResultLauncher` | Handles the result of the system voice recognition window. | Triggered in `startSpeechToText`. |
| `mediaRecorder` | `MediaRecorder` | Tool used to record sound from the device microphone. | Used in audio recording methods. |
| `isInternetAvailable` | `boolean` | Tracks connectivity status (required for most STT engines). | Used in `updateUIState`. |

________________________________________
## 3. Class Methods
### Method Name: onCreateView
•	**What it does:** Sets up UI listeners. The "Record Audio" button toggles its text between "Start/Stop". The "Voice Input" button checks for internet availability before allowing use.

### Method Name: startSpeechToText
•	**Type:** private  
•	**What it does:** Creates a specialized `Intent` to call the system's voice recognizer. It specifies the language and launches the activity via `speechLauncher`.

### Method Name: saveTextToFile
•	**Type:** private  
•	**Parameters:** `String text`  
•	**What it does:** Opens `feedback.txt` in the app's internal storage and appends the new text. It uses a `try-with-resources` block to ensure the file is closed properly even if an error occurs.

### Method Name: startAudioRecording / stopAudioRecording
•	**What they do:** `start` initializes the `MediaRecorder`, sets the audio source (MIC) and format (`.3gp`), then begins recording. `stop` halts the recording and releases the microphone hardware for other apps.

________________________________________
## 4. Lifecycle
•	**onResume()**: Registers an `internetReceiver` to monitor connectivity changes while the fragment is active.  
•	**onPause()**: Unregisters the receiver to prevent memory leaks and unnecessary battery drain.

________________________________________
## 5. UI Interaction
•	**CheckBox (chbSayWrite)**: A prerequisite check for using the voice input feature.  
•	**SimpleTextWatcher**: A custom listener that enables the "Save" button only when there is actual text in the field.

________________________________________
## 6. Interaction with other components
•	**RecognizerIntent**: The standard Android component for speech-to-text.  
•	**MediaRecorder**: System service for capturing multimedia.  
•	**Internal Storage**: Provides private file storage for the application.

________________________________________
## 7. General Logic
The user can type a message or dictate it. Dictated text appears in the input field, which can then be saved to a persistent text file. Alternatively, the user can record a direct voice memo as an audio file.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`FeedbackFragment` is like a **smart mailbox**. You can put a regular letter in it (type text), dictate a letter to a secretary who writes it down for you (voice input), or leave a message on an answering machine (audio recording). All your messages are carefully organized into a specific folder (file) inside the app's office.
