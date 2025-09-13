
# Cycling Mobile App

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)

A mobile application designed to help cyclists track their speed, distance, and capture memorable moments during their trips. Developed as a final project for the Mobile Systems course during Erasmus+ exchange at Schmalkalden University.

## 🚴 Features

- **Real-time Speed Tracking**: Monitor your cycling speed using GPS sensors
- **Distance Measurement**: Track the total path length of your cycling trips
- **Photo Capture**: Save memories by taking photos during your rides
- **Trip Records**: Save and view your cycling records
- **User-friendly Interface**: Intuitive design for easy navigation

## 📱 Screenshots

<!-- Add screenshots here when available -->
*Coming soon - Screenshots of the app interface*

## 🛠️ Technologies Used

- **Language**: Java
- **Platform**: Android
- **Build System**: Gradle
- **Sensors**: GPS, Accelerometer
- **Camera**: Android Camera API

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- Android SDK API 21+ (Android 5.0 Lollipop or higher)
- Java Development Kit (JDK) 8 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/rfurkan37/CyclingApp2.git
   ```

2. Open the project in Android Studio:
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. Build the project:
   - Wait for Gradle sync to complete
   - Build > Make Project (or Ctrl+F9)

4. Run on device/emulator:
   - Connect an Android device or start an emulator
   - Run > Run 'app' (or Shift+F10)

## 📁 Project Structure

```
CyclingApp2/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/cyclingapp2/
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── HomePage.java
│   │   │   │   ├── Records.java
│   │   │   │   ├── SaveRecord.java
│   │   │   │   ├── DisplayDataActivity.java
│   │   │   │   ├── Detail.java
│   │   │   │   ├── ConfirmationDialogFragment.java
│   │   │   │   ├── Splash.java
│   │   │   │   └── ui/
│   │   │   │       ├── CyclingData.java
│   │   │   │       └── theme/
│   │   │   │           ├── Color.kt
│   │   │   │           ├── Theme.kt
│   │   │   │           └── Type.kt
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       ├── values/
│   │   │       └── drawable/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🤝 Contributing

This project was developed by:
- **Recep Furkan Akın** (rfurkan37)
- **Emre** (Contributor)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Developed as part of the Mobile Systems course at Schmalkalden University
- Erasmus+ Exchange Program
- Special thanks to our instructors and peers for their support

---

*Built with ❤️ for cyclists everywhere*




