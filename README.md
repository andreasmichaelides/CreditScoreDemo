# CreditScoreDemo

This demo was created using the Clean Architecture approach.
It enforces separate layers, which allows multiple developers to work on the same feature, in apps with limited number of screens.
Each dev can work on a different layer, avoiding merging conflicts and help with paralelising work. An example could be the the use of an API endpoint and
displaying data. One dev can work the the Data and Domain layers, creating the API call models and mappers, another dev can work on displaying data, creating the appropriate models and changes in the Presentation layer (Android layout changes). 

The usage of Butterknife avoids the need to call the findViewById() method and reduces the amount of lines of code. Also very handy for click listener, text changed listeners, etc.
Makes activities and ViewHolder implementations cleaner.

The AndroidArchitecture components where used for implementing the Presentation layer, as it negates the need for setting up our own MVP or MVVM implementation that will require the development of a manager for handling Presenters, base Activity or Presenter classes and more.
With less code, we have a robust communication between our View and Business logic.
Activity lifecycle is being handled by the use of LiveData. Since ViewModels do not get destroyed during Activity rotations, data and Activity state can be restored easily.

Retrofit was used for making the necessary API call. It is highly recommended, as it is easy to define API endpoints and simple to setup. It also offers RxJava support.

AutoValue was used for defining Models. Autovalue helps in creating modles faster, by generating the equals() and toString() methods. It also generates immutable models.
