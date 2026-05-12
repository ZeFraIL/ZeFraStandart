# Detailed Class Description: LoginFragment

________________________________________
## 1. General Information
•	**Class Name:** LoginFragment  
•	**Type:** Fragment  
•	**Purpose:** This fragment handles the user login process. It verifies the entered username and password against the records stored in the local SQLite database.  
•	**Interaction:** Hosted within the `Welcome` Activity. Upon a successful login, it navigates to the `Junction` Activity, passing the `User` object as extra data.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `etNameL`, `etPassL` | `EditText` | Input fields for the username and password. | Used in `onCreateView` to retrieve user input. |
| `bLogin` | `Button` | The login button. | Used to trigger the validation logic via a click listener. |
| `helperDB` | `HelperDB` | The database helper instance. | Used to query the user table. |
| `user` | `User` | An object representing the logged-in user. | Passed to the next screen after successful login. |

________________________________________
## 3. Class Methods
### Method Name: onCreateView
•	**Type:** public  
•	**Return Value:** View  
•	**Parameters:** `LayoutInflater`, `ViewGroup`, `Bundle`  
•	**What it does:**  
1. Inflates the `fragment_login` layout.  
2. Links UI elements to variables.  
3. Sets up a click listener for the `bLogin` button.  
4. Validates that fields are not empty.  
5. Calls `isUserFound()` to check credentials.  
6. If the user exists, it creates an `Intent` for `Junction.class`, puts the `User` object into the intent, and starts the activity.

### Method Name: isUserFound
•	**Type:** private  
•	**Return Value:** boolean (true if credentials match, false otherwise)  
•	**Parameters:** `String stName`, `String stPassword`  
•	**What it does:**  
1. Opens the database in readable mode.  
2. Queries the `Users` table.  
3. Iterates through the result set (`Cursor`).  
4. Compares the stored username and password with the provided ones.  
5. If a match is found, it populates the `user` object and returns `true`.

________________________________________
## 4. Lifecycle
•	**onCreateView()**: The primary entry point for fragment setup. This is where UI elements are initialized and listeners are attached.

________________________________________
## 5. UI Interaction
•	**EditText**: Data is extracted using `.getText().toString()`.  
•	**My_Toast**: Used to display error messages in red.  
•	**BaseMenuClass.speak()**: Provides audio feedback for empty fields or failed login attempts.

________________________________________
## 6. Interaction with other components
•	**SQLite**: Direct interaction with `HelperDB` to retrieve user data.  
•	**Intent**: Used for navigation, passing a `Serializable` object.

________________________________________
## 7. General Logic
The user inputs their name and password. The fragment checks the local database records one by one. If a match is found, the user is authenticated and sent to the "Junction" screen. Otherwise, an error is shown and announced.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
Think of `LoginFragment` as a **security guard at a gate**. You show him your ID (name and password). He checks a big ledger (the database) to see if you are on the list. If you are, he lets you through the gate to the next area (`Junction`). If you aren't, he says "Access Denied" and won't let you in.

**Improvement Suggestion:** Currently, `isUserFound` loops through every user in the table. While fine for a few users, it's better to use an SQL `WHERE` clause to find the specific user directly, which is much faster as the database grows.
