# Pokemon App

This is a Pokemon app built using modern Android development practices and technologies. It allows
users to view a list of Pokemon and see detailed information about each one.

## Features

* **Pokemon List:** Displays a scrollable list of Pokemon names.
* **Pokemon Details:** Shows detailed information about a selected Pokemon, including its name,
  height, and an image.
* **Pagination:** Loads more Pokemon as the user scrolls down the list.
* **Error Handling:** Gracefully handles network errors and displays appropriate messages.
* **Loading State**: Shows the loading state.

## App Architecture

This app follows a clean architecture, which promotes separation of concerns, testability, and
maintainability. The architecture is divided into the following layers:

* **Presentation Layer (UI):**
    * Responsible for displaying data to the user and handling user interactions.
    * Consists of Activities, Composables, and ViewModels.
    * Uses Jetpack Compose for building the UI declaratively.
    * Uses the `ViewModel` component to hold UI-related data and handle UI logic.
    * Uses UI state pattern
* **Domain Layer (Use Cases):**
    * Contains the business logic and use cases of the application.
    * Defines the rules and operations that the app can perform.
    * Independent of the UI and data layers.
    * The use cases receive the repositories to work.
* **Data Layer (Repositories, Data Sources, Mappers):**
    * Responsible for retrieving and managing data from various sources (e.g., network, database).
    * **Repositories:** Provide a clean API for the Domain layer to access data, abstracting the
      data source implementation details.
    * **Data Sources:** Handle the actual data retrieval from different sources. This app uses a
      remote data source.
    * **Mappers:** Map data between different models (e.g., network responses to domain models).

## Key Libraries and Technologies

This app leverages several modern Android libraries and technologies:

* **Jetpack Compose:**
    * A modern UI toolkit for building native Android UI declaratively.
    * Used for all UI components in the app.
    * Provides efficiency and readability.
* **Kotlin Coroutines:**
    * For asynchronous programming and managing background tasks efficiently.
    * Used for network requests, data fetching, and other time-consuming operations.
* **Flow:**
    * Kotlin's reactive stream API for handling asynchronous data streams.
    * Used to represent the state of the UI, the data, and the errors.
* **Hilt:**
    * Dependency injection library for Android that reduces boilerplate code.
    * Used to manage the dependencies of the application, allowing you to easily provide the
      dependencies to the needed objects.
* **Retrofit:**
    * A type-safe HTTP client for Android and Java.
    * Used to make network requests to the Pokemon API.
    * Used to retrieve the pokemon details.
* **OkHttp:**
    * An HTTP client that underlies Retrofit.
    * Provides powerful features for handling network requests and responses.
* **JUnit Jupiter:**
    * A testing framework for Java and Kotlin.
    * Used to write unit tests.
* **Mockk:**
    * A mocking library for Kotlin.
    * Used to create mock objects for testing.
* **AndroidX test**:
    * The AndroidX test library for testing android components.
* **Lifecycle**:
    * The androidx lifecycle library to handle the states of the components.
* **Navigation**:
    * The androidx navigation library to navigate between the screens.
* **Coil**:
    * For efficient image loading.
* **Clean Architecture:**
    * A software design pattern for creating maintainable and scalable applications.
    * Used to separate the app into distinct layers (Presentation, Domain, Data).
* **MVVM (Model-View-ViewModel):**
    * An architectural pattern used to structure the Presentation layer.
    * Separates UI logic from the UI components themselves.

## API

* The app uses the [PokeAPI](https://pokeapi.co/) to get Pokemon data.

## Testing

* The app includes extensive unit tests to ensure the correctness of the business logic and data
  mapping.
* The `ViewModel` are being tested.
* The repository is being tested.
* The mapper is being tested.
* The use cases are being mocked.
* **TestFactory** is being used.
* **TestDispatcher** is being used.
* **AdvanceUntilIdle** is being used.

## Setup and Run

1. **Clone the repository:** `git clone https://github.com/rtippisetty/pokemon.git`
2.  **Open the project in Android Studio.**
3.  **Build and run the app on an emulator or a physical device.**

## License

[License information]
