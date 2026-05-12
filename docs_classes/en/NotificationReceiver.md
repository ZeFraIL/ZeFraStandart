# Detailed Class Description: NotificationReceiver

________________________________________
## 1. General Information
•	**Class Name:** NotificationReceiver  
•	**Type:** BroadcastReceiver  
•	**Purpose:** This class catches the "alarm signal" sent by the system at a scheduled time and transforms it into a visual notification displayed in the phone's status bar.  
•	**Interaction:** Triggered by the system based on the schedule set in the `Reminder` class. It can launch the `CloseNotificationService` or reopen the `Reminder` activity.

________________________________________
## 2. Variables (Class Fields)
This class does not have global fields; all data is extracted locally within the `onReceive` method from the incoming `Intent`.

________________________________________
## 3. Class Methods
### Method Name: onReceive
•	**Type:** public  
•	**Parameters:** `Context context`, `Intent intent`  
•	**What it does:**  
1. Extracts the notification title, text, and priority from the `intent` extras.  
2. Initializes `RemoteViews`. This is a special object used to control UI elements that are rendered outside of the app's process (in the system notification tray).  
3. Populates the notification layout (`notification_layout.xml`) with the text and an icon.  
4. Sets up actions for the buttons inside the notification:
   - The "Close" button triggers `CloseNotificationService`.
   - The "Open App" button navigates the user back to the `Reminder` screen.
5. Builds the notification using `NotificationCompat.Builder`, targeting a high-priority channel (`CHANNEL_HIGH`).  
6. Sends the finished notification to the `NotificationManager` to be displayed.  
•	**When called:** Automatically by the Android system when an alarm goes off.  
•	**Key concept:** `RemoteViews` is restricted to basic UI elements (TextView, ImageView, Button). It doesn't support custom or complex view types.

________________________________________
## 4. Lifecycle
Extremely short: Catch signal -> Build notification -> Show notification -> Finish. It exists only for the duration of the broadcast processing.

________________________________________
## 5. UI Interaction
Uses a **custom notification layout**. This allows the developer to create a more branded and interactive experience (with specific buttons) compared to standard system notifications.

________________________________________
## 6. Interaction with other components
•	**NotificationManager**: The system service responsible for all device notifications.  
•	**PendingIntent.getService**: Used to perform a background task (closing the notification) without opening any UI.  
•	**PendingIntent.getActivity**: Used to launch an activity from the notification tray.

________________________________________
## 7. General Logic
This class acts like a **courier**. When the "alarm clock" (system) says "Time's up!", the courier takes a pre-designed template, fills in the specific details (title/text), and sticks it on the user's "front door" (the notification shade).

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`NotificationReceiver` is like an **errand runner**. Imagine you left a note in a calendar saying: "At 10:00 AM, send me a reminder." At 10:00 AM, the system wakes up this runner. He quickly creates a beautiful postcard (the notification) with interactive buttons and holds it up in front of you so you don't forget your task.
