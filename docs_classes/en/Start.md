# Detailed Class Description: Start

________________________________________
## 1. General Information
•	**Class Name:** Start  
•	**Type:** Activity  
•	**Purpose:** This class serves as the splash screen of the application. It initializes the database, sets up Text-to-Speech (TTS), and automatically redirects the user to the next screen after 4 seconds.  
•	**Interaction:** Launches the `Welcome` Activity when the timer finishes or the button is clicked. It uses `HelperDB` to prepare the database structure.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `context` | `Context` | Holds information about the current application state. | Used in DB and TTS initialization. |
| `ivLogo` | `ImageView` | UI element for the logo image. | Used in `onCreate` for rotation animation. |
| `bStart` | `Button` | Button for manual transition to the next screen. | Used in `onCreate` to set up click listener. |
| `cdt` | `CountDownTimer` | A timer that counts down from 4 seconds. | Used for automatic navigation. |
| `totalTTS` | `TextToSpeech` | A static variable for speech synthesis. | Initialized here; accessible by other classes. |

________________________________________
## 3. Methods
### Method Name: onCreate
•	**Type:** protected  
•	**Return Value:** void  
•	**Parameters:**  
| Name | Type | Description |
| :--- | :--- | :--- |
| `savedInstanceState` | `Bundle` | Container for screen state restoration data. |
•	**What the method does:**  
1. Sets the UI layout from `activity_start.xml`.  
2. Initializes the database via `HelperDB` to ensure tables are created.  
3. Initializes `totalTTS` for voice features.  
4. Binds UI elements (`ivLogo`, `bStart`) to the code.  
5. Starts a 360-degree rotation animation on the logo.  
6. Sets a click listener on `bStart` that cancels the timer and starts `Welcome` Activity.  
7. Starts the `CountDownTimer` (`cdt`) for a 4-second delay.  
•	**When called:** Automatically by the Android system when the activity starts.  
•	**Important Note:** It's crucial to `cancel()` the timer if the user clicks the button manually to prevent multiple screen transitions.

________________________________________
## 4. Lifecycle
•	**onCreate()**: Called when the app starts. All component setup happens here.

________________________________________
## 5. UI Interaction
•	**ImageView (ivLogo)**: Animated using `.animate().rotation()`.  
•	**Button (bStart)**: Handles the manual skip via `OnClickListener`.  

________________________________________
## 6. Interaction with other components
•	**Intent**: Used to navigate to `Welcome.class`.  
•	**SQLite**: Triggers table creation via `HelperDB`.

________________________________________
## 7. General Logic
When the app opens, the user sees a rotating logo. In the background, the DB is prepared. After 4 seconds (or upon button click), the app moves to the main screen.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
Think of this as the "lobby" of a building. While the guest is waiting there, you check if the rooms are ready (Database) and turn on the speakers (Text-to-Speech). After 4 seconds, you automatically guide them to the main hall (`Welcome`), but if they are in a hurry, they can press a button to go in immediately.

**Pro-tip:** The `totalTTS` is `static`. While convenient, be careful as static variables can cause memory leaks if not properly managed (e.g., shutting down the TTS engine when the app closes).
