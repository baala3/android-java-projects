# ❓ Trivia App

## 📌 What is this project?

**Trivia App** is a lightweight Android application that presents users with a multiple-choice quiz. It demonstrates core Android development concepts such as activity navigation, view binding, event listeners, and UI state management using Java.

This project is focused on learning how to build an interactive form-like experience by dynamically responding to user input, validating answers, and giving feedback — all within a multi-screen Android flow.

---

## ✨ Features

- 🧠 **Multiple-Choice Questions**: Displays trivia questions with four answer choices.
- 🟢 **Answer Validation**: Instantly checks the selected answer and provides correct/incorrect feedback.
- 📊 **Score Tracking**: Tracks number of correct answers across questions.
- 🔄 **Restart Quiz**: Option to restart the quiz from the result screen.
- 🧭 **Multi-Activity Navigation**: Uses explicit `Intent` to move between quiz and result screens.

---

## 🛠 Tech Stack

- **Language**: Java
- **UI**:
  - `TextView`, `RadioGroup`, `RadioButton`, `Button` for building the quiz interface
  - `ConstraintLayout` for responsive design
- **Navigation**:
  - `Intent` for moving between activities (quiz screen ↔ result screen)
- **State Handling**:
  - Internal logic to manage question index, user score, and UI updates
- **Architecture**:
  - Simple Activity-based structure, no external libraries or frameworks

---

> 🛠️ Future Enhancements:
> - Load questions dynamically from an API or JSON file
> - Add categories or difficulty levels
> - Timer per question and scoring based on speed
> - Save high scores using SharedPreferences or local DB
