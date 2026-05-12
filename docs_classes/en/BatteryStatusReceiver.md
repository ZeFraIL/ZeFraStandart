# Detailed Class Description: BatteryStatusReceiver

________________________________________
## 1. General Information
•	**Class Name:** BatteryStatusReceiver  
•	**Type:** BroadcastReceiver  
•	**Purpose:** Monitors the device's battery level. If the charge drops to a critical level (15% or less), it displays a custom warning on the screen.  
•	**Interaction:** Dynamically registered through `BaseMenu`. It receives system-wide broadcasts regarding battery state changes.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `lastShownPercentage` | `int` | Stores the last battery percentage that triggered a notification. | Used to prevent showing the warning repeatedly if the percentage hasn't decreased. |

________________________________________
## 3. Class Methods
### Method Name: onReceive
•	**Type:** public  
•	**Parameters:** `Context context`, `Intent intent`  
•	**What it does:**  
1. Verifies that the received action is `ACTION_BATTERY_CHANGED`.  
2. Calculates the current battery percentage using the formula: `(level / scale) * 100`.  
3. Checks the logic condition: is the battery <= 15% AND is it lower than the last time we checked?  
4. Programmatically builds a **custom Toast notification**:
   - Creates a `LinearLayout` with a red background.
   - Adds a battery icon (`ImageView`).
   - Adds a `TextView` showing the actual percentage.
5. Displays this custom view in the center of the screen for a long duration.  
•	**When called:** Automatically by the Android system whenever the battery level changes (typically by 1%).  
•	**Key concept:** This class creates its UI entirely in Java code without using an XML layout, showing how to generate dynamic, eye-catching alerts on the fly.

________________________________________
## 4. Lifecycle
Executes immediately upon receiving the system broadcast.

________________________________________
## 5. UI Interaction
Uses a custom-designed `Toast`. While modern Android versions have some restrictions on custom toasts, this implementation works well while the app context is active.

________________________________________
## 6. Interaction with other components
•	**BatteryManager**: Provides constant keys to extract battery level, scale, and status from the system `Intent`.

________________________________________
## 7. General Logic
This class acts like a **fuel gauge**. It continuously monitors the fuel needle. As soon as the needle enters the "red zone," it switches on a bright red warning light on the dashboard so the driver (user) remembers to refuel.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`BatteryStatusReceiver` is like a **low-battery alarm**. Imagine you have a tiny assistant who checks your phone's settings every minute. If they see the battery is almost empty, they run to the middle of the screen with a red sign and shout: "Attention! Find a charger quickly!".
