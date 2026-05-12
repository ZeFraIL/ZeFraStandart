# Detailed Class Description: LinksFragment

________________________________________
## 1. General Information
•	**Class Name:** LinksFragment  
•	**Type:** Fragment  
•	**Purpose:** Displays a list of useful links stored in the database. It allows users to watch videos in a built-in player or browse websites using a WebView.  
•	**Interaction:** Loads data via `HelperDB`, utilizes `InternetConnectionReceiver` to monitor connectivity, and uses `CustomAlertDialog` to display content.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `lvLinks` | `ListView` | The UI component that displays the list of links. | Initialized in `onCreateView`. |
| `all_links` | `ArrayList<Link>` | A collection of `Link` objects containing all details. | Used in `buildLinks` and on item clicks. |
| `links_names` | `ArrayList<String>` | A list of link names only, used for the adapter. | To populate the `ListView`. |
| `internetConnectionReceiver` | `InternetConnectionReceiver` | Monitors the real-time internet status. | Registered in `onStart`, unregistered in `onStop`. |

________________________________________
## 3. Class Methods
### Method Name: onCreateView
•	**What it does:**  
1. Inflates the fragment layout and finds the `lvLinks` view.  
2. Calls `buildLinks()` to fetch data from the database.  
3. Initializes the `InternetConnectionReceiver` and links it to the list (disables the list if there's no internet).  
4. Sets an `OnItemClickListener`: depending on whether the clicked item is a "video" or "web" type, it calls the appropriate method in `CustomAlertDialog`.

### Method Name: buildLinks
•	**Type:** private  
•	**What it does:**  
1. Opens the database using `HelperDB`.  
2. Queries the `Links` table.  
3. Iterates through the results, creates `Link` objects, and adds them to `all_links`.  
4. Sets up an `ArrayAdapter` to display the names in the `ListView`.

________________________________________
## 4. Lifecycle
•	**onStart()**: Registers the `internetConnectionReceiver` to start listening for connectivity changes when the fragment becomes visible.  
•	**onStop()**: Unregisters the receiver to save system resources and battery.

________________________________________
## 5. UI Interaction
•	**ListView**: The primary way users interact with the links. It uses a standard Android list item layout.

________________________________________
## 6. Interaction with other components
•	**SQLite**: Reads the `Links` table to populate the screen.  
•	**BroadcastReceiver**: Checks for internet availability dynamically.  
•	**CustomAlertDialog**: A helper class that handles the complex task of displaying YouTube videos or Web pages.

________________________________________
## 7. General Logic
When the screen opens, it reads all stored links from the database and shows them as a simple list. If you tap an item while connected to the internet, a pop-up appears showing the specific video or website.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`LinksFragment` is like a **catalog in a library**. It lists all the available movies and books. You can browse the titles, but if you want to actually watch a movie (open a link), you need a key (internet connection). If you don't have the key, the catalog is locked, and you can only look at the titles.
