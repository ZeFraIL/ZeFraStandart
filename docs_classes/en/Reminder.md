# Detailed Class Description: Reminder

________________________________________
## 1. General Information
•	**Class Name:** Reminder  
•	**Type:** Activity  
•	**Purpose:** This class allows the user to schedule notifications. It provides options to set a specific time using a `TimePicker`, specify a delay in seconds, or configure recurring reminders.  
•	**Interaction:** It interacts with the system's `AlarmManager` to schedule tasks and uses `NotificationReceiver` to handle the actual notification delivery.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `etInterval` | `EditText` | Field for entering a delay in seconds. | Used in the `setNotification` logic. |
| `timePicker` | `TimePicker` | UI widget for choosing hours and minutes. | Used to set the target time in the `Calendar` object. |
| `rgNotificationType`| `RadioGroup` | Group of radio buttons for selecting Single or Repeating types. | Used to toggle the visibility of the repeat period field. |
| `etRepeatPeriod` | `EditText` | Field for entering how often the reminder should repeat (in seconds). | Used for calculating the repeating alarm interval. |

________________________________________
## 3. Class Methods
### Method Name: onCreate
•	**What it does:** Initializes the UI components and sets a listener on the `RadioGroup`. If "Repeating" is selected, the repeat period field is made `VISIBLE`; otherwise, it is set to `GONE`.

### Method Name: setNotification
•	**Type:** private  
•	**What it does:**  
1. Reads the inputs from the text fields (using `try-catch` to safely handle non-numeric input).  
2. Creates a `Calendar` instance and sets it to the time selected in the `TimePicker`.  
3. If an additional interval is provided, it adds those seconds to the calendar.  
4. Creates an `Intent` pointing to `NotificationReceiver` and wraps it in a `PendingIntent`.  
5. Retrieves the system's `AlarmManager`. For one-time alerts, it uses `setExact`; for recurring alerts, it uses `setRepeating`.  
•	**When called:** Triggered when the user clicks the "Set Notification" button.  
•	**Important Note:** Starting with Android 12, using exact alarms (`setExact`) requires special permissions. Ensure these are declared in the Manifest if the notifications don't fire as expected.

________________________________________
## 4. Lifecycle
•	**onCreate()**: Handles initial UI setup and the dynamic visibility logic for the repeat settings.

________________________________________
## 5. UI Interaction
•	**TimePicker**: A user-friendly graphical tool for time selection.  
•	**RadioButton**: Allows switching between different application modes (single vs. repeating).

________________________________________
## 6. Interaction with other components
•	**AlarmManager**: A system service that can wake up the app or trigger actions at a precise time.  
•	**PendingIntent**: A "permission slip" given to the Android system to execute an action on the app's behalf at a later time.

________________________________________
## 7. General Logic
The user specifies a time and whether the reminder should repeat. The app calculates the exact moment in milliseconds and tells the system: "At this exact time, send a broadcast to my `NotificationReceiver`." Even if the app is closed, the system will execute this task.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`Reminder` is like an **assistant with a stopwatch**. You tell the assistant: "Remind me at 2:00 PM" or "Remind me to drink water every hour." The assistant writes this down in their planner (`AlarmManager`) and sets a timer. Even if you go to sleep (close the app), the assistant stays awake, and when the time comes, they tap you on the shoulder with a note (the notification).
