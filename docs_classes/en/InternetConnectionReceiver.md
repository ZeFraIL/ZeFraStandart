# Detailed Class Description: InternetConnectionReceiver

________________________________________
## 1. General Information
•	**Class Name:** InternetConnectionReceiver  
•	**Type:** BroadcastReceiver (System Event Receiver)  
•	**Purpose:** This class "listens" for changes in the device's internet connection. If the internet goes down, it disables specific UI elements (like the links list) to prevent the user from trying to access unavailable content.  
•	**Interaction:** Registered within `LinksFragment`. It controls any `View` object that is passed to its constructor.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `targetView` | `View` | The UI element that needs to be enabled or disabled based on connectivity. | Inside the `onReceive` method. |

________________________________________
## 3. Class Methods
### Constructor: InternetConnectionReceiver(...)
•	**Parameters:** `View targetView`  
•	**What it does:** Stores a reference to the UI component (e.g., a `ListView`) that this receiver is responsible for controlling.

### Method Name: onReceive
•	**Type:** public  
•	**Parameters:** `Context context`, `Intent intent`  
•	**What it does:**  
1. Accesses the system's `ConnectivityManager`.  
2. Checks the current `NetworkCapabilities` of the device.  
3. Determines if there is an active Wi-Fi or Cellular data connection.  
4. Updates the `targetView` by calling `setEnabled(isInternetAvailable)`. If false, the view becomes non-interactive.  
5. Shows a red error toast using `My_Toast` if the internet is disconnected.  
•	**When called:** Automatically by the Android system whenever there is a change in the device's network state (e.g., turning on Airplane mode).

________________________________________
## 4. Lifecycle
Not applicable. A `BroadcastReceiver` only exists for the duration of the `onReceive` call, but it must be manually registered and unregistered in an Activity/Fragment lifecycle.

________________________________________
## 5. UI Interaction
Directly modifies the `isEnabled` property of the `targetView`.

________________________________________
## 6. Interaction with other components
•	**ConnectivityManager**: A system service used to query the status of network connections.

________________________________________
## 7. General Logic
This class acts as a "watchman" monitoring the internet. As soon as the connection is lost, it "locks the door" (disables the UI element). Once the connection returns, it "unlocks" it.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`InternetConnectionReceiver` is like a **water leak sensor**. It doesn't provide the water itself, but if it "senses" that something is wrong (no internet), it shuts off the valve (disables the button). This prevents the app from trying to perform actions that are impossible without a connection, avoiding crashes or confusion.
