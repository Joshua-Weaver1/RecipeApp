# Recipe App
## App Purpose

The purpose of this app is to serve as a simple and convenient recipe app in which users are able to find some recipe inspiration or search for a recipe by either using an ingredient or dish name.

## Design Rational
### Features

- There are multiple screens, and users can navigate between screens
- Uses explicit and implicit intents
- Has Menus
- Uses RecycleView
- Uses Data storage
- Uses Internet
- The app opens when the device has no connection to the internet

In the app, multiple screens were utilised by needing to see components of different recipes. Otherwise, the main page would become too cluttered with information and would be unappealing to look at. This was done by creating another activity, called DishInformationActivity, which would show another screen when a user selects a recipe.
There was a need for the use of menus because it gave the user the ability to quicky filter the recipes according to what they would like to eat. One design option considered was the use of an ImageButton to hold the filter options. However, I decided on the use of a Spinner as this had the same functionality but also showed the current option selected, meaning the user would be able to see the current setting. Another option added was a SearchView, meaning if there was a specific ingredient or name the user wanted, they would be able to find recipes for that.
The app utilises many RecycleViewers, because otherwise the users would only be able to see two recipes at a time when on the main page. I also implemented it with the Ingredients for the recipes. This allowed them to only take up a strip across the screen, as the users are able to scroll horizontally to see them. This saved lots of space on the Recipe Page, as otherwise the ingredients could sometimes take up half the page if there were enough of them.
The app uses data storage through SharedPreference, in which the users are able to choose between two themes. I originally had tried to implement a switch to control the themes, as there are only two. However, it did not suit the look of the app as well as two buttons did, so the decision was to have a button for each theme. The user would also be able to see the name of each theme that way.

## Novel Features

One novel feature that was included in the app was a button which takes the user to the api used for the application. This is a useful feature for the user as it allows them too see where the information on the application is coming from.

## Challenges Faced

The greatest challenged I faced was implementing the api. There was a lots of information that was being returned from the different requests. Initially, a lot of information I was displaying on the app contained tags and other things that I did not wish to be included. I solved this issue by creating java models to hold all the information returned so that I could work with it easier, through the use of adapters.
Another challenged faced was changing all the different aspects of the app when the user changed theme. I decided to change my approach and make some parts of the app a neutral Gray which looks good with both the red and blue themes.

## How to Improve in the Future

One way I would like to see the app improved in the future is to implement recipe downloads, so users are able to keep offline their favourite recipes to use when they do not have an internet connection. This would be held in a separate menu option which could be easily navigated to on the main activity.
The second way I would like to see the app improved is to implement the themes in such a way that the design of the app completely changes with the different options, meaning thay the users would be able to choose a version that they like the most.

## Author

Joshua Weaver