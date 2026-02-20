# PW-Manager-Android
## Project Objective

Develop a functional Android password manager application using Java and Android Studio. The application must securely authenticate users via Firebase Authentication (Email/Password) and store/retrieve account credentials using Firebase Realtime Database, associating stored data with the logged-in user.

## Core Requirements

### 1. User Authentication System

* **Registration:**
    * Implement an email and password registration screen.
    * Enforce password complexity requirements (see below) during registration.
    * Provide clear feedback to the user upon successful registration (e.g., a `Toast` message or `Snackbar`).
    * After successful registration, navigate the user back to the Login screen.
* **Login:**
    * Implement an email and password login screen.
    * Limit users to **three** incorrect login attempts. After the third failed attempt, the application should close (e.g., using `finishAffinity()`).
* **Bonus:** Implement a "Forgot Password" feature using Firebase's password reset functionality.

### 2. Password Requirements

* All passwords (both for user registration *and* for the accounts being stored) must meet the following criteria:
    * Minimum 8 characters long.
    * Contains at least one uppercase letter (A-Z).
    * Contains at least one lowercase letter (a-z).
    * Contains at least one digit (0-9).
    * Contains at least one special character (e.g., `!@#$%^&*`).
* Implement validation logic to check these requirements and provide feedback to the user if they are not met.
*(Note: This applies primarily to the Email/Password registration and stored account passwords)*

### 3. Storing Account Credentials

* Create a dedicated screen (`Activity` or `Fragment`) for adding new account credentials, separate from the main account list view.
* Each stored account entry must include at least:
    * Account Name (e.g., "Netflix," "Gmail")
    * Email/Username associated with the account
    * Password for the account (meeting the complexity requirements above)
    * Category (e.g., "Social," "Work," "Entertainment," "Finance") - consider using a `Spinner` or similar input.
    * **URL** (Optional website address associated with the account, e.g., `https://www.netflix.com`)
    * Notes (optional additional details)
* Ensure the password input field hides the characters as they are typed.
* Store this account data securely under the authenticated user's unique ID (UID) in the Firebase Realtime Database.
* **Bonus:** Add a toggle button (like an eye icon) next to the password input field to allow the user to show/hide the password they are entering.
* * **Bonus:** Store the paswwords in Firebase encrypted. More advanced encryption, the more bonus points.

### 4. Displaying Stored Accounts

* On the main screen after login, display all the user's stored accounts in a `RecyclerView`.
* Each item in the `RecyclerView` should clearly display the **Account Name** and a **quick description** (you could use the Category or the first few words of the Notes).
* When a user taps on an item in the `RecyclerView`, navigate to a new "Details" screen.
* The "Details" screen must display *all* the saved information for that specific account (Name, Email/Username, Password, Category, **URL (if provided)**, Notes).
    * *Consider how you will display the password securely here - perhaps initially hidden with an option to reveal.*
    * **If a URL was saved** for the account, provide a clear mechanism on this Details screen (e.g., a clickable `TextView` showing the URL, or a dedicated 'Visit Website' button) that allows the user to open the URL in a web browser. This should be implemented using an `Intent` with `Intent.ACTION_VIEW`.
* Implement functionality where a **long-click** on a `RecyclerView` item initiates the process to delete that **specific stored account** (e.g., the "Netflix" entry they saved, not their main user login). This deletion process must:
    * Display a **confirmation dialog** (e.g., `AlertDialog`) asking the user if they are sure they want to delete that specific account entry ("Delete 'Netflix' account?").
    * Upon confirmation, remove the corresponding account data from the Firebase Realtime Database.
    * Ensure the `RecyclerView` updates immediately to reflect the deletion.
* **Bonus:** Implement functionality to automatically fetch and display a relevant logo/image for the account based on its Name (e.g., search for "Netflix logo" if the name is "Netflix"). You might need an external image loading library like `Glide` or `Picasso` and potentially a simple web search API or a predefined mapping.
* **Bonus:** Include a copy button for the phone to copy the password to the clip board.

### 5. Filtering and Searching Accounts

* Implement a mechanism (e.g., a `Spinner` or filter menu) to allow users to filter the displayed `RecyclerView` list based on the **Category** they assigned to each account.
* Implement a search functionality (e.g., using a `SearchView`) that allows users to filter the list by typing in the **Account Name** or the associated **Email/Username**.

### 6. Application Settings & Management

* Include a dedicated "Settings" screen accessible from the main view (e.g., via an options menu).
* **Logout:** Provide a clear logout button/option that signs the user out of Firebase (and Google if implemented) and returns them to the Login screen.
* **Delete Account:** Provide an option for the user to permanently delete their *entire* SecurePass user account (including their Firebase Authentication record and all their stored data in the Realtime Database).
    * **Crucially:** Implement a **confirmation dialog** (e.g., `AlertDialog`) asking the user to confirm this irreversible action before proceeding with the deletion.
* **About Section:** Include an "About" section (perhaps within Settings or as a separate screen/dialog) displaying the app name, version (e.g., 1.0), and developer name(s).

### 7. User Interface (UI) and User Experience (UX)

* Design a clean, intuitive, and consistent user interface.
* Ensure smooth navigation between screens.
* Provide appropriate user feedback for actions (loading indicators, success/error messages).

## Technical Specifications

* **IDE:** Android Studio
* **Language:** Java
* **Minimum SDK:** API 21 (or your specified level)
* **Backend:** Google Firebase
    * Firebase Authentication:
        * Email/Password method (Required)
        * **Bonus:** Implement Google Sign-In as an alternative/additional authentication method.
    * Firebase Realtime Database
* **Target AVD:** Development and testing should primarily use a Pixel device profile (e.g., Pixel 6, Pixel 7) configured in the Android Virtual Device (AVD) Manager.

## Evaluation Criteria

* **Functionality:** Does the application meet all the core requirements? Do all features work as expected?
* **Code Quality:** Is the Java code well-structured, readable, commented, and efficient? Are Android best practices followed?
* **Firebase Integration:** Is Firebase Authentication and Realtime Database implemented correctly and securely? Is data structured appropriately under user UIDs?
* **UI/UX:** Is the application easy and intuitive to use? Is the design appealing and consistent?
* **Error Handling:** Does the app handle potential errors gracefully (e.g., network issues, invalid input, failed login/registration)?
* **Security Considerations:** Are passwords handled securely (hidden input, complexity rules)? Is user data properly associated with their UID? Is deletion handled safely with confirmation?
* **Bonus Features:** Implementation and functionality of any bonus features attempted.

## Submission

To complete this project, you must submit the following items to your designated GitHub repository:

1.  **Complete Project Code:**
    * Push the entire Android Studio project source code to the main branch of your repository.
    * Ensure the project builds successfully and runs without errors based on the included code.
    * Include any necessary configuration files if they differ from standard setups (though standard Firebase integration shouldn't require extra files in the repo itself).

2.  **Demonstration Video:**
    * Record a video of your application running (e.g., using screen recording software or an emulator's recording feature).
    * This video **must clearly demonstrate all core features** working correctly. This includes, but is not limited to:
        * User Registration (showing password requirement enforcement).
        * User Login (showing successful login and the 3-attempt lockout).
        * Adding a new stored account (showing input fields, validation, category selection).
        * Displaying the list of accounts in the `RecyclerView`.
        * Clicking an item to view its details.
        * Long-clicking an item to delete it (showing the confirmation dialog and the list updating).
        * Filtering the list by category.
        * Searching the list by account name or email/username.
        * Using the Logout function.
        * Navigating to the Settings/About screen.
        * *If implemented:* Demonstrating any bonus features (Forgot Password, password visibility toggle, automatic image loading).
    * Upload the video file to the Google Classroom Assignment
