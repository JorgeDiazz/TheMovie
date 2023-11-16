# Overview: TheMovie (Android app)

Welcome to TheMovie, an Android application that consumes [The Movie Database](https://developer.themoviedb.org/docs) to retrieve up-to-date information about movies, enabling an exploration of the cinematic world. 


## What's included

An Android app with _modular architecture_, clean architecture, SOLID principles and _MVVM_.

![app_architectural_pattern](docs/images/app_architectural_pattern.png)

Furthermore, some libraries and frameworks such as:

* _Splash screen api_ and container _HomeActivity_.
* _Network connectivity interceptor_ for HTTP requests.
* _Hilt_ for dependencies injection.
* _Room_ for local storage.
* _Navigation Component_ as app navigation framework.
* _Coil_ for loading and caching images.
* _Retrofit2_ for API requests.
* _Paging3_ for local & remote data pagination.
* _PagingSource_ for pagination orchestration.
* _Coroutines_ and _Flow_ for Reactive Functional Programming.
* _StateFlow_ and _SharedFlow_ for observing and updating data.
* _ViewBinding_ for activities and fragments.
* _Timber_ for debug logging purposes.
* Android Studio _EditorConfig_ file to maintain consistent coding styles.
* Gradle’s Kotlin _DSL_.
* _ktlint_ for static code analysis.
* _LeakCanary_ for memory leaks detection.
* _jUnit_, _MockK_ and _Turbine_ for unit testing.
* _dokka_ for Kotlin's documentation generation.

## Installation

Clone this repository and import it into **Android Studio**

```bash  
git clone https://github.com/JorgeDiazz/android-rappipay-tech-test.git
```  

## Build variants

Herein you can find multiple targets that the app takes into account:

|          |Staging    |Production  |
|----------|-----------|------------|  
|`Internal`|Debug      |Debug       |
|`External`|Release     |Release    |

Where the following formed variants are built for staging purposes:

- stagingInternalDebug
- stagingInternalRelease

And these ones for production purposes:

- productionInternalDebug
- productionInternalRelease
- productionExternalDebug
- productionExternalRelease

**_Sidenote:_** choose productionExternalDebug before executing the app



## Using the app

### Launching the app

<img src="docs/images/1.gif" width="350" height="600"/>

### Upcoming movies supporting pagination

<img src="docs/images/2.gif" width="350" height="600"/>

### Top rated movies supporting pagination

<img src="docs/images/3.gif" width="350" height="600"/>

### Suggested movies recycler view uses top rated cached movies to get updates in real-time

<img src="docs/images/4.gif" width="350" height="600"/>

### Filters are populated in real-time as per current top rated cached movies

<img src="docs/images/5.gif" width="350" height="600"/>

### Selecting several filters

<img src="docs/images/6.gif" width="350" height="600"/>

### Movie Details Screen

<img src="docs/images/7.gif" width="350" height="600"/>

### Watching Movie's trailer

<img src="docs/images/8.gif" width="350" height="600"/>

### Launching the app using airplane mode (offline mode)

<img src="docs/images/9.gif" width="350" height="600"/>

### Retrying movies fetching when Internet connection recovered

<img src="docs/images/10.gif" width="350" height="600"/>

### Movie Details Screen (offline mode)

<img src="docs/images/11.gif" width="350" height="600"/>

### Persisting screen state (selected filters) in landscape mode 

<img src="docs/images/12.gif" width="350" height="600"/>

### Movie Details Screen in landscape mode

<img src="docs/images/13.gif" width="350" height="600"/>


## Others

1. Project's CodeStyle can be found [here](docs/codestyle.md).
2. Project utilities file can be found [here](docs/utilities.md).
