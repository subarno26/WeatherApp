# WeatherApp

Objectives: To build an application encompassing the following:
1. Dependecy injection using Dagger.
2. Navigation component
3. Work Manager
4. Jetpack Datastore

Methodology:
1. Creation of a weather app which fetches current weather based on location.
2. Using navigation component to handle all the navigations throughout the application.
3. Fetching weather data using retrofit, also saving some values to datastore for future use.
4. Architecture used : MVVM with Data binding.
5. Using safeargs to send location from search screen to weather screen.
6. When the application is offline, the values are fetched from datastore.
7. Using dagger for injecting dependencies of various components like Retrofit, Viewmodels etc.
8. Using work manager to download a PDF file containing weather information, with constraints set to Wifi.
