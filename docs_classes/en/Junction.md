# Detailed Class Description: Junction

________________________________________
## 1. General Information
•	**Class Name:** Junction  
•	**Type:** Activity  
•	**Purpose:** This is a transition screen (a "junction") that appears after a successful login or registration. Its main feature is a visual animation where the shadow of a text label moves in a circle.  
•	**Interaction:** Receives user data via `Intent` and manages the popup menu using `BaseMenuClass`.

________________________________________
## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `tv1` | `TextView` | The text field that displays the animated shadow. | In the `goShadow` method. |
| `totalTime` | `long` | Total duration of the animation (10 seconds). | In the timer setup. |
| `radius` | `long` | The distance (radius) the shadow moves from the text. | In the mathematical calculations for the shadow position. |
| `ivPUMJ` | `ImageView` | Menu icon button. | In `onCreate`. |
| `baseMenuClass` | `BaseMenuClass` | Helper for menu and voice features. | Used to display the menu. |

________________________________________
## 3. Class Methods
### Method Name: onCreate
•	**What it does:** Initializes the UI components, sets up the menu button, and triggers the `goShadow()` animation.

### Method Name: goShadow
•	**Type:** private  
•	**What it does:**  
1. Starts a `CountDownTimer`.  
2. On every "tick" (every 100ms), it calculates new `dx` and `dy` coordinates for the shadow using circular motion formulas (`cos` and `sin`).  
3. Updates the text's shadow using `setShadowLayer`, making the shadow appear to rotate around the text.  
4. Removes the shadow once the timer finishes.  
•	**Key concept:** Uses trigonometry to create smooth, programmatic animations without using external animation files.

________________________________________
## 4. Lifecycle
•	**onCreate()**: Starts the shadow animation and prepares the menu.  
•	**onDestroy()**: Releases Text-to-Speech resources.

________________________________________
## 5. UI Interaction
•	**TextView (tv1)**: Demonstrates advanced text styling and programmatic shadow manipulation.  
•	**ImageView (ivPUMJ)**: Provides a way to navigate back or restart the app via the popup menu.

________________________________________
## 6. Interaction with other components
•	**BaseMenuClass**: Standardizes the navigation and exit actions across the app.

________________________________________
## 7. General Logic
Upon entering this screen, the user sees a welcome text. For 10 seconds, the shadow of the text moves smoothly in a circle, providing a "living" feel to the interface after the login process.

________________________________________
## 8. Explanation in Simple Words
**Real-life Analogy:**  
`Junction` is like a **waiting lounge** with a cool light show. When you walk in, the sign on the wall "comes alive": its shadow starts spinning around, as if someone is circling the sign with a flashlight. It’s a simple way to make the app feel more interactive and polished.
