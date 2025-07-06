# 📝 My Notes

## 📌 What is this project?

**My Notes** is a simple note-taking Android app that allows users to create, view, and delete short text notes. It uses an in-memory data structure to simulate note persistence and is designed as a stepping stone toward more advanced local data storage solutions like Room or SQLite.

This project focuses on mastering fundamental concepts in Android like `RecyclerView`, `ViewModel`, `Intent`, and user input handling, using Java and basic MVVM principles.

---

## ✨ Features

- ➕ **Create Notes**: Users can input a note title and description via a form and save it.
- 🗂️ **View All Notes**: Saved notes are shown in a vertical scrollable list using `RecyclerView`.
- ❌ **Delete Notes**: Each note has a delete button to remove it from the list.
- 🧭 **Multi-Screen Navigation**: Uses explicit `Intent` to switch between the add-note screen and the note list screen.
- ⚙️ **MVVM-lite Structure**: Keeps logic out of UI where possible, separating data model and presentation logic.

---

## 🛠 Tech Stack

- **Language**: Java
- **Architecture**: Lightweight **MVVM** pattern (Model-View-ViewModel)
  - `Note` model class to represent note data
  - Simple in-memory list acting as a data source (no persistence yet)
- **UI Components**:
  - `RecyclerView` with `Adapter` for displaying note list
  - `EditText`, `Button`, and `TextView` for form handling and display
- **Navigation**:
  - `Intent` to navigate between the main list and add-note activity
- **View Layer**:
  - `ConstraintLayout` and `LinearLayout` used for flexible layouts
- **User Input Handling**:
  - Input validation and state updates triggered on button clicks
