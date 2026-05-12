# Detailed Class Description: Welcome

________________________________________
## 1. General Information
•	**Class Name:** Welcome  
•	**Type:** Activity (Main Hub)  
•	**Purpose:** This class acts as the primary container for the application's fragments. It manages the bottom navigation bar and the popup menu.  
•	**Interaction:** Switches between `LoginFragment`, `SignUpFragment`, and `GuestFragment` within the `fragment_container`. It delegates menu logic to `BaseMenuClass`.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `context` | `Context` | Application context. | Used to initialize the menu class. |
| `ivPUM` | `ImageView` | Icon that triggers the PopupMenu. | Used in `onCreate` to set up the click listener. |
| `baseMenuClass` | `BaseMenuClass` | Helper object for menu operations. | Used to show menus and release TTS resources. |

________________________________________
## 3. Class Methods
### Method Name: onCreate
•	**Type:** protected  
•	**Return Value:** void  
•	**Parameters:** `Bundle savedInstanceState`  
•	**What the method does:**  
1. Sets the `activity_welcome` layout.  
2. Initializes the menu icon (`ivPUM`) and the `baseMenuClass`.  
3. Configures the `BottomNavigationView`: when a tab is selected, it replaces the current fragment in the container with the corresponding new fragment (`Login`, `SignUp`, or `Guest`).  
4. If the activity is starting for the first time (`savedInstanceState == null`), it loads `LoginFragment` by default.  
•	**When called:** When the activity is created.

### Method Name: onDestroy
•	**Type:** protected  
•	**What it does:** Calls `releaseTextToSpeech()` from `baseMenuClass` to properly shut down the speech engine and free up system memory.  
•	**When called:** When the user closes the activity or the system destroys it.

________________________________________
## 4. Lifecycle
•	**onCreate()**: Setup of navigation and initial fragment loading.  
•	**onDestroy()**: Cleanup of heavy resources like Text-to-Speech.

________________________________________
## 5. UI Interaction
•	**BottomNavigationView**: Listens for item selections to navigate between features.  
•	**ImageView (ivPUM)**: Acts as a menu button.  
•	**FragmentContainerView (id: fragment_container)**: The placeholder where fragments are swapped.

________________________________________
## 6. Interaction with other components
•	**FragmentManager**: Used to `replace` fragments and manage the back stack (allowing the user to go back to previous fragments).  
•	**BaseMenuClass**: Separates menu and voice logic from the UI controller.

________________________________________
## 7. General Logic
The class acts as a "switchboard". It displays a persistent navigation bar at the bottom. When you tap a button, `Welcome` simply swaps out the "content card" (Fragment) being shown in the middle of the screen.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
Think of `Welcome` as a **TV frame**. The frame itself stays the same, but you have a remote control (the bottom navigation). By pressing different buttons on the remote, you change the channels (fragments) shown on the screen. There's also a "settings" button (the menu icon) for extra options.

**Improvement Suggestion:** The code uses `if` statements for menu IDs. While it works, using a `switch` statement or the Navigation Component library is generally preferred for cleaner and more scalable navigation code.
