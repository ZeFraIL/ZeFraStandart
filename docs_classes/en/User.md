# Detailed Class Description: User

________________________________________
## 1. General Information
•	**Class Name:** User  
•	**Type:** Normal Class (POJO - Plain Old Java Object)  
•	**Purpose:** This class is a data model for a user. It is used to store information about a single user, including their name, password, phone number, and email.  
•	**Interaction:** Used by `SignUpFragment` and `LoginFragment` to create user objects. It implements the `Serializable` interface, which allows objects of this class to be passed between Android components (Activities/Fragments) via `Intent` or `Bundle`.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `userName` | `String` | The user's login name. | In all getter and setter methods. |
| `userPassword` | `String` | The user's password. | Used for authentication checks. |
| `userPhone` | `String` | The user's phone number. | For contact info or profile display. |
| `userMail` | `String` | The user's email address. | For notifications or profile display. |

________________________________________
## 3. Class Methods
### Constructor: User(...)
•	**Type:** public  
•	**Parameters:** `userName`, `userPassword`, `userPhone`, `userMail` (all `String`).  
•	**What it does:** Creates a new User object and initializes its fields with the provided values.

### Getters and Setters (e.g., `getUserName`, `setUserName`)
•	**Type:** public  
•	**What they do:** Provide a safe way to access or modify the private fields of the class. This follows the principle of **encapsulation**.

### Method: toString()
•	**Type:** public  
•	**Returns:** `String`  
•	**What it does:** Returns a string representation of the object. This is very useful for debugging to see the user's data in the logs (Logcat).

________________________________________
## 4. Lifecycle
Not applicable (it's a data class, not a UI component).

________________________________________
## 5. UI Interaction
Does not interact with the UI directly. Data from UI elements (like `EditText`) is gathered by a Fragment/Activity and then used to populate this class.

________________________________________
## 6. Interaction with other components
•	**Serializable**: Allows the object to be converted into a byte stream so it can be passed between screens.

________________________________________
## 7. General Logic
The class acts as a simple container. You create a `User` object when someone signs up and use it to pass that person's info around the app.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
Think of the `User` class as a **form or a profile card**. The card has specific slots for "Name", "Password", and "Phone". The class itself is the blank template of the card. When you write a specific person's details on it, you create an "object". The `Serializable` part is like putting that card in an envelope so it can be mailed from one office (screen) to another.
