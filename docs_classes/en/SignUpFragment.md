# Detailed Class Description: SignUpFragment

________________________________________
## 1. General Information
•	**Class Name:** SignUpFragment  
•	**Type:** Fragment  
•	**Purpose:** This fragment handles the registration of new users. It collects the user's name, password, email, and phone number, validates the inputs, and stores them in the local database.  
•	**Interaction:** Hosted by the `Welcome` Activity. After a successful signup, it navigates to the `Junction` Activity. It uses background processing for database write operations.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `etNameSU` et al. | `EditText` | Input fields for registration details. | Used in `onCreateView` to gather text data. |
| `helperDB` | `HelperDB` | Database management object. | Used in `saveUser` and `isUserFound` methods. |
| `user` | `User` | Object to hold new user data. | Used to pass data to the `Junction` screen. |

________________________________________
## 3. Class Methods
### Method Name: onCreateView
•	**What it does:**  
1. Initializes the input fields and the signup button.  
2. Sets a click listener that checks if any field is empty. If so, it shows a warning toast.  
3. Calls `isUserFound()` to check if the username/password is already taken.  
4. If the credentials are unique, it calls `saveUser()` and transitions to the next activity.

### Method Name: saveUser
•	**Type:** private  
•	**Parameters:** `User user`  
•	**What it does:**  
1. Creates a **background thread** using `ExecutorService`. This prevents the UI from freezing during the database write.  
2. Uses `ContentValues` to package the user's data.  
3. Calls `db.insert()` to save the new record into the `Users` table.  
4. Switches back to the main thread (`runOnUiThread`) to display a success message to the user.  
•	**Key concept:** Database operations should always happen off the main (UI) thread to ensure a smooth user experience.

### Method Name: isUserFound
•	**Type:** private  
•	**What it does:** Checks the database to see if a user with the same credentials already exists, preventing duplicate registrations.

________________________________________
## 4. Lifecycle
•	**onCreateView()**: Handles the inflation of the layout and the setup of the registration logic.

________________________________________
## 5. UI Interaction
•	**EditText**: Gathers four distinct pieces of information from the user.  
•	**My_Toast**: Informs the user about validation errors or successful database writes.

________________________________________
## 6. Interaction with other components
•	**SQLite**: Uses `getWritableDatabase()` for inserting new data.  
•	**ExecutorService**: Implements multithreading to optimize performance.  
•	**Intent**: Navigates to the `Junction` Activity.

________________________________________
## 7. General Logic
The user fills out a registration form. The app verifies if the user is already "in the books". If not, the app uses a background worker to record the data in the "warehouse" (database) and welcomes the user by moving to the main application area.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`SignUpFragment` is like a **Human Resources department**. When you apply for a job, you fill out a form. The clerk checks if you've ever worked there before. If you're new, they send an assistant (`executor`) to take your papers to the archives (database). While the assistant is walking to the archives, the clerk can keep talking to you, so you don't have to stand there waiting for the archive door to lock.
