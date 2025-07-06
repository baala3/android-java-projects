# 🤖 Face Detector

## 📌 What is this project?

**Face Detector** is an Android application that uses machine learning to detect human faces in real-time using the device's camera. It uses **Google ML Kit's Face Detection API** to identify facial features and draw overlays for visual feedback.

---

## ✨ Features

- 📷 **Real-time Face Detection**: Detects faces from the live camera feed with fast and accurate results.
- 🧠 **ML Kit Integration**: Uses Google's on-device ML library to detect facial landmarks and contours.
- 🟦 **Visual Overlays**: Draws bounding boxes around detected faces in the camera preview.
- 🔒 **On-Device Inference**: No data is sent to a server — all face detection happens locally for better privacy and performance.
- 🔄 **CameraX Support**: Uses modern CameraX API for lifecycle-aware camera handling.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **Camera API**: [`CameraX`](https://developer.android.com/training/camerax) for preview and image capture
- **ML Library**: [`Google ML Kit`](https://developers.google.com/ml-kit/vision/face-detection/android)
  - Face Detection with landmark tracking and contour detection
- **UI**:
  - `PreviewView` for displaying the camera feed
  - Custom overlay view for drawing bounding boxes
- **Concurrency**:
  - `Coroutines` or `ExecutorService` (based on implementation) for image processing off the main thread
- **Permissions**:
  - Runtime camera permission handling using Android's permission model

---

> 🛠️ Future Enhancements:
> - Add detection of facial expressions (e.g., smiling, eye open/closed)
> - Capture and save face images to gallery
> - Add multi-face tracking or face ID labeling

