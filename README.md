# Android-Game

I collaborated with three of my peers to create an Android app.

This app allows users to register, login, and logout. 
When logged in, the user can play through 4 mini-games, 
view their current and past game statistics, 
and compare their statistics with other users. 

The goal of this project was to use design principles, 
design patterns, and packaging to make the code clean 
and extensible.

This project uses Clean Architecture + MVP (Model, View,
Presenter) approach to packaging. 

Design patterns used in this project include 
Dependency Inversion (UserServiceIntf, DataManagerIntf), 
Singleton (AppManager), Observer (GameStateObserver), 
Builder (AppleItemsBuilder), Factory (GameManagerFactory), 
and Strategy (ScoreCalculator). 
