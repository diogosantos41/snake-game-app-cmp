# Snake Game

A classic **Snake** game built with **Compose Multiplatform** for **Android** and **iOS**. The goal is simple: move the snake, eat food, grow, and avoid hitting yourself, while keeping your high score, choosing how you play (swipe or direction pad), and customizing the look with theme colors, sound, and haptics.

The app is structured so game rules live in a small domain layer (engine + coordinator), the UI reacts through a single action flow, and preferences persist across launches.

## Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/58603c81-90f8-4447-8baa-af91945328ce" width="24%" />
  <img src="https://github.com/user-attachments/assets/7c80c36f-5ae0-4380-95c7-3f48b59e5351" width="24%" />
  <img src="https://github.com/user-attachments/assets/63aafdbe-e430-41b2-8e5c-046beb1d99ee" width="24%" />
  <img src="https://github.com/user-attachments/assets/57dfb950-44bd-4ebc-b4c4-774c0351205b" width="24%" />
</p>

## Features

- **Cross-platform** — Shared UI and logic on Android and iOS.
- **Gameplay** — Grid based snake, food, score, high score, pause / resume with countdown, game over.
- **Controls** — Swipe or on-screen direction pad.
- **Theme** — Pick a primary color, animate smoothly.
- **Feedback** — Background music, sound effects, and haptics (toggleable).
- **Settings** — Sound, vibration, control mode, game color.
- **Share** — Share your result after a game (platform share sheet).
- **UX** —  Landscape guard on small devices and auto-pause when the app goes to background.

## Tech stack & architecture


- **Language** - Kotlin 
- **UI** - Compose Multiplatform (Material 3) 
- **Architecture** - Layered: presentation (ViewModels, Compose) → domain (engine, coordinator, preferences interfaces) → data (DataStore, engine impl, platform audio/haptics)
- **DI** - Koin
- **Persistence** - DataStore 
- **Platforms** - Android, iOS

## License

This project is **open source** and **free to use** under the **MIT License**. Attribution is appreciated.

See the [`LICENSE`](LICENSE) file for the full text.

## Acknowledgements

Built as a learning / portfolio project. If you fork or reuse parts of it, a link back to this repository is welcome.

