# 🖼️ Image Gallery

## 📌 What is this project?

**Image Gallery** is a basic Android app that displays a grid-based collection of images. The app simulates a simple gallery where users can browse images and tap to view them in full screen. It's a beginner project to practice working with `RecyclerView`, `GridLayoutManager`, `Intent`, and dynamic layouts in Java.

---

## ✨ Features

- 🗂️ **Grid-Based Image Gallery**: Displays a scrollable image grid using `RecyclerView` and `GridLayoutManager`.
- 👆 **Full Screen View**: Clicking on any image opens it in a dedicated full-screen view.
- 🎨 **Clean UI**: Uses `CardView` and spacing for a modern and tidy layout.
- 🧭 **Screen Navigation**: Smooth transition between gallery and full image using `Intent`.
- 📱 **Responsive Layout**: Grid adjusts to different screen sizes and orientations.

---

## 🛠 Tech Stack

- **Language**: Java
- **UI Components**:
  - `RecyclerView` with `GridLayoutManager` for image grid
  - `CardView` and `ImageView` for item presentation
  - `ConstraintLayout` for layout management
- **Navigation**:
  - `Intent` to pass selected image to the full-view screen
- **Image Source**:
  - Uses drawable resources for local images (no external loading)
- **Architecture**: Simple Activity-based navigation structure

---

> 🛠️ Future Enhancements:
> - Load images from gallery storage or remote URLs
> - Add zoom/pinch gesture support in full-screen view
> - Implement transition animations between screens
> - Support image categories or albums


API 1:[link](https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&per_page=20&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s)

## Play URL

