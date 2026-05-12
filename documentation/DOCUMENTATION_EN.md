# 📱 Android Application Documentation: ZeFraStandart

________________________________________
## 🧾 General Information
**Project Name:**  
ZeFraStandart  
**Author(s):**  
Zeev Fraiman  
**Date:**  
November 2024  
**Language:**  
Java  
**Development Environment:**  
Android Studio  
**Android Version (minSdk / targetSdk):**  
28 / 34  

________________________________________
## 🎯 Project Purpose
•	**What problem does the app solve?**  
The application serves as a demonstration and educational project that integrates various standard Android mechanisms: database operations, multimedia (TTS/STT), network status monitoring, and sensors.

•	**Why is this task important?**  
It allows practicing interaction between system components (Activity, Fragment, Service, BroadcastReceiver) within a single application.

•	**Target Audience:**  
Developers learning standard Android features and users who need a convenient interface for keeping a diary, managing links, and setting reminders.

________________________________________
## 📌 Application Requirements
**Functional Requirements**
•	User authentication and registration.  
•	Displaying a list of useful links with internet connection checks.  
•	Voice input (Speech-to-Text) and text playback (Text-to-Speech).  
•	Maintaining a personal diary and creating reminders.  
•	Dynamic interface creation in specific sections.  

**Non-functional Requirements**
•	**Performance:** Fast response by offloading heavy operations (DB, files) to background threads.  
•	**Usability:** Intuitive navigation via BottomNavigationView and NavigationDrawer.  
•	**Reliability:** Prevention of crashes when necessary system components (Phone, Email) are missing.

________________________________________
## 🧠 General Architecture
•	**Chosen Approach:**  
MVC (Model-View-Controller).

•	**Why was it chosen?**  
For a project of this scale, the classic separation into controllers (Activity/Fragments) and models (HelperDB/POJO) provides sufficient transparency without excessive code complexity.

•	**Main System Components:**  
– `Activities` (Start, Welcome) — Entry points.  
– `Fragments` — Modular UI parts (Login, Feedback, Links).  
– `HelperDB` — SQLite data management.  
– `Receivers` — Handling system events.  

________________________________________
## 🧩 UML Diagram (Description)
[Start Activity] -> [Welcome Activity]  
[Welcome Activity] -> [Fragments (Login, SignUp, MyDiary, Feedback, etc.)]  
[Fragments] <-> [HelperDB (SQLite)]  
[Fragments] <-> [BaseMenu (Utility Class)]  
[Receivers] -> [System Intents (Battery, Connectivity, Audio)]  

________________________________________
## 📁 Package Structure
– `zeev.fraiman.zefrastandart`: All source code is concentrated in the main package for quick access to components.  
– `res/layout`: Separation of XML interface descriptions.  
– `res/menu`: Definitions for Popup and Drawer menus.  

________________________________________
## 🧩 Detailed Class Description
### 📌 Class: Start
**Role:** Splash Screen.  
**Responsibility:** Initialization of initial settings, greeting the user via TTS.  
**Main Methods:**  
- `onCreate()` — Sets up CountDownTimer for transition to the next screen.  

### 📌 Class: BaseMenu
**Role:** Utility class for menu management.  
**Responsibility:** Centralized control of PopupMenu and registration of receivers (e.g., battery status).  
**Main Methods:**  
- `showPopupMenu()` — Displays the menu.  
- `handleMenuItemClick()` — Contains click logic.  

### 📌 Class: FeedbackFragment
**Role:** Feedback screen.  
**Responsibility:** Recording audio, converting speech to text, saving to a file.  
**Main Methods:**  
- `startSpeechToText()` — Activates voice input.  
- `saveTextToFile()` — Writes to local storage.  

________________________________________
## 🔄 Application Workflow Diagram
1. The user launches the app (StartActivity).  
2. After the timer, WelcomeActivity opens with BottomNavigation.  
3. The user selects a section (e.g., "Links").  
4. LinksFragment requests data from HelperDB.  
5. InternetConnectionReceiver checks network availability before opening a link.  

________________________________________
## 🎨 UI/UX Analysis
•	The interface is built on Material Design principles.  
•	**Simplicity:** Access to all functions within 2-3 clicks.  
•	**Logic:** Uniform control elements (buttons, lists).  
•	**Accessibility:** Use of TTS allows people with visual impairments to use the app.  

________________________________________
## ⚙️ Threading
•	**Technologies Used:**  
– `CountDownTimer` (StartActivity)  
– `AsyncTask` (GuideFragment — for file reading)  
– `Thread` (in SignUp for DB operations)  

•	**Why this method?** Simplicity of implementation for one-time background tasks.  
•	**ANR Prevention:** Heavy operations are moved out of the main thread (UI thread).  

________________________________________
## 💾 Data Handling
•	**SQLite:** Storing structured information (users, links).  
•	**Files:** Saving feedback (feedback.txt).  
•	**SharedPreferences:** Used for basic settings.  

________________________________________
## 🌐 Networking
•	Use of `ConnectivityManager` and `BroadcastReceiver` to monitor internet status.  
•	Action blocking (e.g., link clicks) when no connection is available.  

________________________________________
## 🔐 Security
•	Local database is protected by standard Android mechanisms (app-only access).  
•	Basic input validation during registration.  

________________________________________
## 🧪 Testing
•	**Unit Tests:** Verifying HelperDB logic.  
•	**UI Tests:** Verifying navigation between fragments.  

________________________________________
## 🐞 Error Handling
•	Checking Intent availability (SMS/Email/Call) before launch to prevent ActivityNotFoundException.  
•	`try-catch` blocks when working with files and DB.  

________________________________________
## 📊 Project Self-Assessment
| Criterion | Rating (1–10) |
| :--- | :--- |
| Architecture | 8 |
| Code | 9 |
| UI/UX | 8 |
| Reliability | 10 |
| **Overall Level** | **9** |

________________________________________
## 🏁 Conclusion
The project successfully implements the integration of many Android components. The notification system and real-time sensor/network monitoring are the most successful parts. The main challenge was managing fragment lifecycles during dynamic UI creation.  

________________________________________
## 📎 Appendices
•	[Repository Link]  
•	Screenshots are available in the `drawables` folder.
