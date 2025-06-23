# WRITEIT - List your products before shopping !
##### *This README.md will allow you to know the features of the project, and will permit you to make changes later if necessary.*


### Summary
1. [General Information](#general-information)
2. [Screenshots](#screenshots)
3. [Technologies Used](#technologies-used)
4. [Architecture](#architecture)
5. [How to Run](#how-to-run)
6. [Features Developed](#features-developed)
7. [What's Next](#whats-next)
8. [Android Studio Version](#android-studio-version)

***
#### General information
***
*WRITE IT is an app that frees your mind by helping you plan your shopping list. From the basics, I've created this app, cause because I'm the kind of person who… writing everything down to free my mind and don't forget anything.*


***
#### Screenshots
***
* [Shopping list Preview](screenshots/shopping_list_preview.png) 
* [Selection Mode for deletion](screenshots/delete_selection_mode.png)
* [No product to delete](screenshots/no_product_selected_to_delete.png)
* [Add your product](screenshots/add_your_product.png)
* [Filter to come](filter.png)

***
#### Technologies used
***
A number of dependencies and libraries were used for this project, among them:
  * [Coroutines](https://github.com/Kotlin/kotlinx.coroutines): Version 1.5.0 - 1.7.3
  * [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=fr): Version 2.56.2
  * [ROOM](https://developer.android.com/jetpack/androidx/releases/room?hl=fr): Version 2.7.0-rc03

Additionally: 
  * [Material 3](https://developer.android.com/develop/ui/compose/designsystems/material3?hl=fr): Version 1.3.2

***
#### Architecture
***
This project follows the MVVM (Model-View-ViewModel) architecture using:
- State management with StateFlow and MutableSharedFlow
- Jetpack Compose for UI
- Room for local data storage
- Hilt for dependency injection

***
#### How to run
***
1. Download the repository at the following URL: https://github.com/mmarliacy/WriteIt.
2. Extract the "WriteIt-main" folder to your desired destination (e.g., Desktop)..
3. Open Android Studio.
4. In order to open the program on Android Studio, follow the following instructions from the Android Studio menu bar: **File → Open → Navigate to your extracted project folder → Select it**
5. Android Studio, with the intervention of Gradle will synchronize the project, in case of outdated technologies or dependencies, update the project, clicking again on: **File -> Sync Project with Gradle Files**.

***
#### Features developed
***

* Easily plan and list the products you need before going shopping.
* Get an estimation of your total cost.
* Full CRUD operations are available to manage your shopping list: create, edit, delete, and view items.
* Also you can archive a product (with its parameters - price, quantity...) if you don't need it at some point, but take it back to your list whenever you feel so.

***
#### What's next
***

* Add "category" variable in "Product" item to get a better products' apportionment  
* Setting a budget with an gradual animation with Jetpack Compose to respect maximum total cost depend on your wallet.
* Integrate filter to ensure a better management.
* Make the app responsive on at least 2 more differents résolutions.
* Export the list to external apps such as WhatsApp or SMS.


***
#### Android Studio version
***
> This project was made using Android Studio Ladybug | 2024.2.1 Patch 2. 