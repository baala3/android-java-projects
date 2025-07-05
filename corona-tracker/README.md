# 🦠 Corona Tracker

## 📌 What is this project?

**Corona Tracker** is an Android application that displays COVID-19 case statistics per country. The app fetches real-time data from a public API and presents it in a clean, scrollable list. It was built to practice integrating RESTful APIs in Android apps using **Retrofit** and **RecyclerView**, along with applying basic MVVM principles for better code organization.

This project is part of a hands-on learning journey in Android development with Java and focuses on networking and UI presentation.

---

## ✨ Features

- 🌍 **Global Country Stats**: Shows a list of countries with live COVID-19 data including total cases, deaths, and recoveries.
- 🔄 **Real-time API Integration**: Fetches data dynamically using Retrofit from the [disease.sh](https://disease.sh) COVID-19 API.
- 🧭 **Navigation Support**: Launches directly into the dashboard view with COVID stats.
- 🎨 **Material UI Components**: Uses CardView and RecyclerView for a modern, responsive layout.
- 💥 **Error Handling**: Handles basic API errors and failures gracefully.

---

## 🛠 Tech Stack

- **Language**: Java
- **Architecture**: Basic MVVM (Model-View-ViewModel) pattern
- **Networking**: 
  - [`Retrofit`](https://square.github.io/retrofit/) for REST API calls
  - `Gson` for JSON parsing
- **UI Components**:
  - `RecyclerView` for displaying lists
  - `CardView` for visual item containers
  - `ConstraintLayout` for responsive design
- **Concurrency**:
  - `Coroutines` for asynchronous API handling
- **API Source**:
  - [disease.sh - Open Disease Data API](https://disease.sh/docs/#/COVID-19%3A%20JHUCSSE/get_v3_covid_19_countries)

---

> 🛠️ Future Enhancements: 
> - Add search/filter by country name  
> - Add chart visualizations using MPAndroidChart  
> - Improve UI/UX with loading indicators and better error messages
