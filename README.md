![Build](https://github.com/simararora7/BitApp/actions/workflows/android.yml/badge.svg)

# BitApp

BirApp is a an app that shows all the trading pairs that are served through [BitFinex](https://www.bitfinex.com). It currently has two screens :
- Trading Pair List Screen
    - This screen shows a list of all available trading pairs. The data that is shown with each pair is
        - Symbol
        - Last Price
        - Daily Change (Percentage)  
    - The data is served from BitFinex's REST [API](https://docs.bitfinex.com/reference#rest-public-tickers).
- Trading Pair Detials Screen
    - This screen shows the following information related to the selected Trading Pair :
        - Real Time Ticker Information. The information that is shown includes :
            - Open Price
            - Daily Change (Absolute and Relative)
            - Top Bid
            - Top Ask
            - Last Price
            - 24 Hour Range
        - Real Time Trades (10 Latest Trades). The information that is shown for each trade includes:
            - Amount
            - Price
            - Time of the trade
    - This screen also shows drop down of all trading pairs so that trading pair can be changed without navigating to the previous screen.
    - The real time ticker information is served through the [Ticker Web Socket channel](https://docs.bitfinex.com/reference#ws-public-ticker).
    - The real time trades information is served through the [Trades Web Socket channel](https://docs.bitfinex.com/reference#ws-public-trades).
---

### Technologies Used
- The app consist of two screens. Each screen is a separate activity that contains a fragment.
- The app follows [MVVM Architecture](https://blog.mindorks.com/mvvm-architecture-android-tutorial-for-beginners-step-by-step-guide) with Model layer following [Clean Architecture](https://medium.com/swlh/clean-architecture-in-android-a-beginner-approach-be0ce00d806b).
- The app uses Vanilla [Dagger2](https://dagger.dev) for dependncy injection. Each screen has its own component that depends on the global application component.
- The app uses [OkHttp](https://square.github.io/okhttp/) and [Retrofit](https://square.github.io/retrofit/) for networking.
- The app uses [RXJava2](https://github.com/ReactiveX/RxJava) for threading as well as for observable patter.
- The app uses [JUnit4](https://junit.org/junit4/) along with [Mockito](https://site.mockito.org) for Unit Testing.
- The app uses [KTLint](https://ktlint.github.io) for lint checks.
- The app uses [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) for deifining Gradle scripts.

---

### CI Proccess
- The project uses GitHub Actions for CI. There is one workflow that performs the following jobs:
    - Run Lint Checks
    - Run Unit Tests
    - Build Debug APK

---

### Common Commnads
|                 | Make Command    | Gradle Command            |
| --------------- | --------------- | ------------------------- |
| Clean Project   | `make clean`    | `./gradlew clean`         |
| Run Lint Checks | `make lint`     | `./gradlew ktlintCheck`   |
| Run Unit Tests  | `make test`     | `./gradlew testDebug`     |
| Build App       | `make assemble` | `./gradlew assembleDebug` |
| Install App     | `make install`  | `./gradlew installDebug`  |

---

### App Screenshots

<img width="454" alt="Screenshot 2022-01-28 at 4 38 36 PM" src="https://user-images.githubusercontent.com/10595255/151537224-9031dc54-88a0-48f1-a060-e50e28fe0896.png">

<img width="454" alt="Screenshot 2022-01-28 at 4 43 02 PM" src="https://user-images.githubusercontent.com/10595255/151537358-9b4da2e6-f2c6-4e54-811e-331b855c8131.png">

