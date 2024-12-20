# ABN AMRO Repositories Viewer

This application fetches and displays the list of ABN AMRO GitHub repositories using the GitHub API. It supports offline-first behavior, pagination, and detailed repository information. Users can also open the repository's GitHub page directly in their browser.

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [How to Run](#how-to-run)
- [Code Overview](#code-overview)
- [Videos](#videos)
- [Further Development](#further-development)

---

## Features
- **Fetch ABN AMRO Repositories**: Fetches GitHub repositories of ABN AMRO using the GitHub API.
- **Pagination Support**: Displays repositories in a paginated list.
- **Repository Details**: Detailed information for each repository is shown upon clicking a list item.
- **Open in Browser**: A button allows users to view the repository in an external browser.
- **Offline-first Support**: Caching ensures the application works seamlessly offline.
- **Database as Source of Truth**: The local database serves as the primary data source. Network updates the database whenever new data is fetched.
- **Dark Mode Support**: The application supports dark mode for a better user experience in low-light environments.

---

## Architecture
The project follows **Clean Architecture** and uses the **MVI (Model-View-Intent)** pattern for the presentation layer to maintain unidirectional communication between View and ViewModel.

### Key Principles:
1. **Separation of Concerns**: Clear separation between UI, business logic, and data layers.
2. **Offline-First**: Local caching via Room serves as the source of truth, and the database is updated with the latest data from the network.
3. **MVI Pattern**: Ensures clear communication between UI and ViewModel.

---

## Tech Stack
- **Kotlin**: Programming language.
- **Room**: For local database storage.
- **Retrofit**: For network calls to the GitHub API.
- **Coroutines**: For asynchronous programming.
- **Hilt**: Dependency injection.
- **Flow**: To observe and manage data streams.
- **Jetpack Libraries**: Including ViewModel, LiveData, Compose and Navigation components.
- **Design System**: Custom module containing atomic components, themes, colors, and fonts.

---

## Code Overview
The project is structured as follows:

```
ABNAMROAssignment/
|
├── data/                # Data layer: repositories, API services, and database
│   ├── api/             # Retrofit API interface
│   ├── db/              # Room Database and DAO
│   ├── repository/      # Repository classes
|
├── domain/              # Domain layer: Use cases and business logic
|                        # The Domain is a platform-independent Kotlin module containing
│
├── feature/             # Presentation layer: MVI pattern implementation
│   ├── ui/              # UI components
│   ├── intent/          # User intents for MVI
│   ├── state/           # State classes for MVI
│   ├── viewmodel/       # ViewModel classes
|
└── design-system/       # Design System: atomic components, themes, colors, and fonts
```

---

## Videos
This section contains different videos showcasing the working application.
*(Add links to demo videos here)*

---

## Further Development
The following features can be added to enhance the application:
1. **Writing Tests for Presentation Layer**: Implement unit tests for ViewModels and UI components.
2. **Enhanced Repository Detail Page**: Add more detailed information about repositories, such as contributors and stars.
3. **Improved Pagination**: Use the Paging Library to handle pagination more efficiently.
4. **Refresh and Pull-to-Refresh Functionality**: Allow users to manually refresh the list to fetch the latest data.

---
