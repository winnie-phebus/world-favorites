# World Favorites

World Favorites is an Android application that allows users to search, discover, and save their favorite countries from around the world. Built with Jetpack Compose and MVVM architecture, the app fetches country data from the World Bank API and provides local storage for personalized notes about each favorite destination.

## Features

- **Search Countries**: Browse and search through all countries using real-time filtering from the World Bank API
- **Country Details**: View comprehensive information including capital city, region, and ISO country code
- **Save Favorites**: Add countries to your favorites list with personalized descriptions about what makes them special to you
- **Manage Favorites**: Edit descriptions or remove countries from your favorites list
- **Expandable Items**: Tap to expand/collapse favorite country items to view full descriptions and additional details
- **Persistent Storage**: All favorites are saved locally using Room database and persist between app sessions
- **Material Design 3**: Modern UI with edge-to-edge display, custom theming, and smooth animations

## Architecture
### MVVM 
Due to the relatively small scale of this app (2.5 screens), I decided to use MVVM. I have a lot more professional experience with larger scaled applications that use MVP, but there really isn't that much in logic that needs to be abstracted.
I was tempted to make a more sophisticated navigation system, and it still might be a little overcomplicated, but I digress.

### Screens:
#### HomeScreen
- I decided to add more functionality to the HomeScreen than requested. I felt that there was more utility available in letting a user delete/edit the favorite countries from the home screen, and it did increase my enjoyment of interacting with the app in development, which was an added bonus.
- I also did not know if the expectation was for there to be less information for the favorite items, but I got really attached to the idea of showing as much information as I could whenever I could.
- Ideally, if I had the time, I would have added region organization for favorite countries.

#### SearchCountry
- This screen is basically as requested: it's a screen that opens the CountryDetailsBottomSheet when a user selects a country from the list. It was also probably the most complicated of the screens, but it turned out alright.
- I was a bit split between potentially doing a modal or drawer for this scene, but I was already so stuck on the idea of doing the CountryDetailsBottomSheet that I couldn't take the time to explore a different modal type for this scene.
- I also considered adding the flags for each country and looked into an API for it: https://flagsapi.com/#themes, but ultimately decided to focus on polishing the MVP before adding another API.
  - though in hindsight, I could've tried to use the ASCII/emoji values, huh.

#### CountryDetails 
- It didn't make sense to me to make this scene into a screen. I'm glad that I chose to go with a BottomSheet, especially because I was able to reuse it fairly easily in the HomeScreen. 
- Due to being a bottom sheet, pressing the "save" button does not navigate the user, but I included a back button on the SearchScreen so that a user could go see their favorites.
  - This also allows a user to add multiple favorite countries in one session. Though I probably should have included some form of notification to the user that indicated a successful save.
  
## Design
- I had hoped to do a custom color palette, but over time the purples of the default theme actually grew on me!
- If I did find the time to make a custom palette, I would have done something along the lines of an "earthy" theme. In hindsight, I probably would have ended up doing primary (blue), secondary (blue-ish green), tertiary (green).

## References:
- Jetpack Compose
  - https://developer.android.com/develop/ui/compose/setup (for imports)
  - https://developer.android.com/develop/ui/compose/compiler?utm_source=android-studio-app&utm_medium=app (adding Compose Compiler)
  - navigation: https://towardsdev.com/mastering-single-activity-architecture-in-jetpack-compose-say-goodbye-to-multiple-activities-95e816fabaf7
- WorldBanksApi
  - https://publicapi.dev/world-bank-api