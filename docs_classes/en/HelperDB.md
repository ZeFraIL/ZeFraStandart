# Detailed Class Description: HelperDB

________________________________________
## 1. General Information
•	**Class Name:** HelperDB  
•	**Type:** SQLiteOpenHelper (Database Helper Class)  
•	**Purpose:** This class is responsible for creating, managing, and upgrading the local SQLite database in the application. It defines the structure for user data and application links.  
•	**Interaction:** Used by any component that needs to save or retrieve data, such as `SignUpFragment` or `LinksFragment`.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `FILE_DB_NAME` | `String` | The filename of the database (`info.db`). | In the constructor. |
| `TABLE_USER` | `String` | The name of the users table. | In `onCreate` and queries. |
| `USER_NAME` | `String` | Column name for the username. | In SQL query strings. |
| `LINKS_TABLE` | `String` | The name of the links table. | In `onCreate` and queries. |
| `LINK_ID` | `String` | Column name for the link's unique ID. | In SQL query strings. |

*Note: Most variables are `public static final`, meaning they are constants accessible from anywhere in the app without creating a `HelperDB` object.*

________________________________________
## 3. Class Methods
### Constructor: HelperDB(...)
•	**Type:** public  
•	**Parameters:** `Context context`  
•	**What it does:** Calls the parent `SQLiteOpenHelper` constructor with the database name and version (1). This prepares the app to work with the specific DB file.

### Method Name: onCreate
•	**Type:** public  
•	**Return Value:** void  
•	**Parameters:** `SQLiteDatabase db` (the database object).  
•	**What it does:** Executes SQL `CREATE TABLE` commands to build the `Users` and `Links` tables.  
•	**When called:** Automatically by the system the very first time the database is accessed and the file doesn't exist yet.

### Method Name: onUpgrade
•	**Type:** public  
•	**Parameters:** `db`, `oldVersion`, `newVersion`.  
•	**What it does:** Currently empty. It's intended to handle structural changes to the database when you release a new app version with a different DB schema.

________________________________________
## 4. Lifecycle
While not a UI component, its lifecycle is managed by the system:  
- `onCreate()`: Called when the DB is first created.  
- `onUpgrade()`: Called when the version number increases.

________________________________________
## 5. UI Interaction
Does not interact with UI elements directly. Its data is used by other classes to populate lists or check login credentials.

________________________________________
## 6. Interaction with other components
Any class can instantiate `HelperDB(context)` and call `getWritableDatabase()` to add/edit data or `getReadableDatabase()` to read it.

________________________________________
## 7. General Logic
This class acts as both the **architect** and the **librarian**. It knows exactly which "shelves" (tables) should exist in the "storage room" (database) and how they are labeled. When the app needs data, `HelperDB` opens the door to that storage.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
Imagine `HelperDB` is a **warehouse manager**. When you first arrive at an empty warehouse, he builds the specific racks (`Users` and `Links`). From then on, whenever you want to store a box or take one out, you talk to him. He ensures everything is organized correctly in the `info.db` file.

**Improvement Suggestion:** In a real-world app, you should implement `onUpgrade` to either drop old tables or migrate data to prevent the app from crashing when you change the database structure in a future update.
