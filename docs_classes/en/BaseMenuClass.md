# Detailed Class Description: BaseMenuClass

________________________________________
## 1. General Information
•	**Class Name:** BaseMenuClass  
•	**Type:** Utility Class  
•	**Purpose:** This class provides centralized management for the application's popup menus and Text-to-Speech (TTS) functionality. It prevents code duplication by handling common menu actions in one place.  
•	**Interaction:** Called from `Welcome` activity and others. It interacts with `My_Toast` for notifications and navigation components like `Reminder`.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `context` | `Context` | Reference to the current activity environment. | Used throughout the class for UI tasks. |
| `textToSpeech` | `TextToSpeech` | The engine that converts text into audible speech. | In `initTextToSpeech` and `speak`. |
| `isTextToSpeechEnabled` | `boolean` | Flag to check if the user wants audio commentary. | In the `speak` method logic. |
| `isChargerCheckEnabled` | `boolean` | Tracks the checked state of the sound option in the menu. | In `showPopupMenu`. |

*Note: The `context` field is marked as `static`. This is generally discouraged as it can lead to memory leaks if not handled carefully (the activity might stay in memory after it's closed).*

________________________________________
## 3. Class Methods
### Method Name: showPopupMenu
•	**Type:** public  
•	**Parameters:** `View anchorView` (where the menu appears), `int menuResId` (the XML resource for the menu).  
•	**What it does:** Inflates and displays a `PopupMenu`. It also synchronizes the checkboxes in the menu with the actual state of the application features.

### Method Name: handleMenuItemClick
•	**Type:** private  
•	**What it does:** Contains the logic for all menu choices:
1. `action_tts_check`: Toggles the sound feature on/off.
2. `action_reminder`: Opens the `Reminder` activity.
3. `action_go_back`: Closes the current screen.
4. `action_exit`: Completely shuts down the app using `finishAffinity()`.
5. `action_restart`: Refreshes the current activity.

### Method Name: initTextToSpeech
•	**Type:** private  
•	**What it does:** Sets up the Text-to-Speech engine, configures it for the US English locale, and shows a toast notification if initialization succeeds or fails.

### Method Name: speak
•	**Type:** public static  
•	**Parameters:** `String text`  
•	**What it does:** If the audio feature is enabled, it reads the provided text out loud.  
•	**Important:** Since this is `static`, you can call `BaseMenuClass.speak("Hello")` from anywhere in your code without needing to create a new object.

________________________________________
## 4. Lifecycle
Not an Activity/Fragment. However, it includes a `releaseTextToSpeech()` method that **must** be called in the Activity's `onDestroy` to stop the speech engine correctly.

________________________________________
## 5. UI Interaction
•	**PopupMenu**: The main visual component managed by this class.  
•	**My_Toast**: Used for styled, informative pop-up messages.

________________________________________
## 6. Interaction with other components
•	**Intent**: Used to transition to the `Reminder` screen.  
•	**Activity**: Uses `finish()`, `recreate()`, and `finishAffinity()` to control the lifecycle of the host activities.

________________________________________
## 7. General Logic
When an activity creates this class, it immediately prepares the "voice". When the user taps a menu icon, this class renders the list of options. If the user clicks "Enable Sound", the class activates the speech engine for subsequent `speak()` calls.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
Think of `BaseMenuClass` as a **Swiss Army Knife** for your app. It holds tools that are useful on every screen: a "back" button, an "exit" tool, and even a "talking parrot" (the speech synthesizer). Instead of giving each room (class) its own set of tools, you just keep this one "knife" handy and pull out the tool you need when you need it.

**Improvement Suggestion:** To avoid memory issues, it's better to make the `context` non-static and pass it through the constructor, or use a `WeakReference<Context>`.
