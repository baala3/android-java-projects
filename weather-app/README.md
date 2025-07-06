# ☁️ Weather App

## 📌 What is this project?

**Weather App** is an Android application that displays current weather information for a given city. It fetches live data from an external weather API and presents details such as temperature, humidity, and weather condition in a user-friendly UI. This project was created to practice API integration, JSON parsing, and basic networking in Java using Retrofit.

---

## ✨ Features

- 🌍 **City-Based Weather Lookup**: Allows users to enter a city name and fetch current weather info.
- 🌡️ **Real-Time Weather Data**: Displays temperature, weather condition (sunny/cloudy/etc.), humidity, and pressure.
- 🌐 **REST API Integration**: Pulls data from OpenWeatherMap API or similar weather service.
- ❌ **Basic Error Handling**: Handles invalid city names and API errors gracefully.
- 📱 **Responsive Layout**: Clean and simple design that works across screen sizes.

---

## 🛠 Tech Stack

- **Language**: Java
- **Networking**:
  - `Retrofit` for making HTTP API calls
  - `Gson` for parsing JSON responses
- **UI Components**:
  - `EditText` to input city name
  - `TextView`, `ImageView`, `Button` to display results
  - `ConstraintLayout` for layout structure
- **Permissions**:
  - Internet permission defined in `AndroidManifest.xml`
- **API Source**:
  - [OpenWeatherMap API](https://openweathermap.org/api) (requires API key)

---

## ⚙️ Example Flow

1. User inputs a city name.
2. App sends a GET request via Retrofit.
3. Weather data is parsed and shown in the UI.
4. On error (e.g. invalid city), a toast or message is displayed.
