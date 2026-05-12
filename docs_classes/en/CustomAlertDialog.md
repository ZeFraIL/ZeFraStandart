# Detailed Class Description: CustomAlertDialog

________________________________________
## 1. General Information
•	**Class Name:** CustomAlertDialog  
•	**Type:** UI Helper Class  
•	**Purpose:** This class is responsible for displaying custom pop-up dialogs containing multimedia content. It can show YouTube videos or entire web pages directly within the app without navigating away from the current screen.  
•	**Interaction:** Typically called by `LinksFragment`. It uses a third-party library to handle YouTube playback.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `context` | `Context` | The environment needed to build and show the dialog. | In all methods that create a dialog. |

________________________________________
## 3. Class Methods
### Method Name: showDialog (for YouTube)
•	**Type:** public  
•	**Parameters:** `String videoId` (the unique YouTube video identifier).  
•	**What it does:**  
1. Creates an `AlertDialog.Builder`.  
2. Inflates a custom layout file (`dialog_layout.xml`).  
3. Finds the `YouTubePlayerView` in the inflated layout.  
4. Syncs the player with the activity's lifecycle so video stops when the app is minimized.  
5. Adds a listener that starts the video as soon as the player is ready.  
6. Adds a "Close" button to dismiss the dialog.

### Method Name: showWeb (for Web pages)
•	**Type:** public  
•	**Parameters:** `String siteAddress` (the URL to open).  
•	**What it does:**  
1. Programmatically creates a `WebView` instance.  
2. Configures settings (like enabling JavaScript).  
3. Creates a `LinearLayout` in code to hold the web view and a "Close" button.  
4. Places this layout into an `AlertDialog` and displays it.  
•	**Key concept:** Demonstrates how to build a complex UI entirely in Java code without using an XML layout file.

________________________________________
## 4. Lifecycle
While it isn't a UI component with its own lifecycle, the `showDialog` method attaches an observer to the host `AppCompatActivity`'s lifecycle to manage the video player's state.

________________________________________
## 5. UI Interaction
•	**YouTubePlayerView**: A specialized view from an external library for streaming YouTube.  
•	**WebView**: An embedded browser component.  
•	**AlertDialog**: The standard Android pop-up window container.

________________________________________
## 6. Interaction with other components
Depends on the `androidyoutubeplayer` library for the video functionality.

________________________________________
## 7. General Logic
This class acts as a "theatre manager". When you tell it which "show" (video or site) you want to see, it quickly builds the stage (the dialog), sets up the equipment (the player or browser), and invites you to watch.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`CustomAlertDialog` is like a **portable DVD player** or a **tablet** that someone hands to you. You don't have to leave the room (the list of links) to watch a clip. A small window pops up right in front of you, plays the content, and once you're done, you just put it away (close it) and you're right back where you started.
