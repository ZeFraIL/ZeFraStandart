# Detailed Class Description: Link

________________________________________
## 1. General Information
•	**Class Name:** Link  
•	**Type:** Normal Class (Data Model)  
•	**Purpose:** This class represents a "Link" entity. It is used to store information about an internet resource or a video to be displayed in a list.  
•	**Interaction:** Objects of this class are instantiated in `LinksFragment` while reading from the SQLite database.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `linkName` | `String` | The display name of the link. | Shown in the `ListView`. |
| `linkDescription` | `String` | A brief description of the resource. | Initialized in the constructor. |
| `linkType` | `String` | The category ("video" or "web"). | Determines if the link opens in a video player or a browser. |
| `linkID` | `String` | The URL or YouTube video ID. | Used to load the actual content. |

________________________________________
## 3. Class Methods
### Constructor: Link(...)
•	**Type:** public  
•	**Parameters:** `linkName`, `linkDescription`, `linkType`, `linkID`.  
•	**What it does:** Initializes a new `Link` object with the provided metadata.

### Getters and Setters (e.g., `getLinkName`, `setLinkName`)
•	**Type:** public  
•	**What they do:** Provide access to the private fields of the class.

### Method: toString()
•	**Type:** public  
•	**What it does:** Returns a string summary of the link's data, primarily for logging and debugging purposes.

________________________________________
## 4. Lifecycle
Not applicable (Data model).

________________________________________
## 5. UI Interaction
No direct interaction. Its properties are read by `LinksFragment` to populate UI components like an `ArrayAdapter`.

________________________________________
## 6. Interaction with other components
Used in conjunction with `HelperDB` (source of data) and `CustomAlertDialog` (destination for displaying the content).

________________________________________
## 7. General Logic
The class serves as a simple "container" or POJO to keep related link data together in memory.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
A `Link` object is like a **bookmark** in a physical book. The bookmark has a title ("Chapter 1") and tells you exactly where to go (the page number). It doesn't open the book for you, but it keeps the information ready so you know which page to turn to.
