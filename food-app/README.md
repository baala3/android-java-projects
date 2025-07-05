# 🍔 Food App

## 📌 What is this project?

**Food App** is a basic Android application that displays a list of food items along with their details such as name, price, description, and image. It’s a beginner-friendly project created to practice list views, layout design using Kotlin.

This project simulates a simple food menu or catalog screen and helps build foundational Android concepts like `RecyclerView`, `Intent`, and working with static data models.

---

## ✨ Features

- 🍱 **Food Menu Listing**: Displays a scrollable list of food items with images and prices.
- 📄 **Detail View**: Tapping a food item opens a new screen with more detailed information.
- 🖼️ **Image Support**: Each food item is shown with a corresponding image.
- 🧭 **Activity Navigation**: Implements smooth transition between list and detail screens using `Intent`.
- 🎨 **Modern Layout**: Uses `CardView`, `RecyclerView`, and `ConstraintLayout` for a clean UI.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Components**:
  - `RecyclerView` for food list
  - `CardView` for menu item presentation
  - `ConstraintLayout` for flexible layout design
- **Architecture**: Simple Activity-based structure
- **Data Handling**: Uses hardcoded data in Kotlin (no external database or API)
- **Image Loading**: Static drawable resources bundled in the app
- **Navigation**:
  - `Intent` to pass food item details between screens

---

> 🛠️ Future Enhancements:
> - Integrate a backend or Firebase for dynamic data
> - Add "Add to Cart" or "Favorite" functionality
> - Use Glide or Coil for image loading from URLs
> - Improve UI with animations and Material transitions
