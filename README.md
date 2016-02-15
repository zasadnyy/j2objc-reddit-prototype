# J2ObjC Reddit Prototype

I've built this prototype in the scope of the internal RnD project at [GetSocial](https://www.getsocial.im/), when we were looking for a way to share business logic among Android/iOS/Unity/Web SDKs.

#### From this project you can learn
- `j2objc-gradle` plugin configuration and usage
- project folder structure
- work with Google Gson parser from Java and cross compiled Objective-C source
- working with Unit tests and cross compilation
- building Android UI on top of cross compiled business logic
- building iOS UI on top of cross compiled business logic


### Requirnments

1. Gradle installed
2. J2ObjC v0.9.8.2.1 installed
3. CocoaPods v0.39 installed 
3. Mac OSX (to test iOS build)


### How to build

 0) `cd` to project root folder
 
 1) Create `local.properties` with J2ObjC and Android SDK location, so it looks like

```
sdk.dir=~/Library/Android/sdk
j2objc.home=~/j2objcDist/j2objc-0.9.8.2.1
```

 3) Configure Gradle wrapper by executing
 
```
>gradle wrapper
```

 4) Build and cross compile `shared` module
 
```
>./gradlew shared:build
```

 5) Import root folder as a Gradle project into Android Studio to run Android demo
 
 6) Open generated Xcode workspace from `ios/` folder to run iOS demo **(workspace not project!)**


### Useful References
- [Official J2ObjC website](http://j2objc.org) 
- [J2ObjC GitHub repo](https://github.com/google/j2objc)
- [Gradle Plugin GitHub repo](https://github.com/j2objc-contrib/j2objc-gradle)
- [Compatible Libraries](https://github.com/j2objc-contrib/j2objc-common-libs-e2e-test) 

### Licence
The project is published under the [MIT license](https://github.com/zasadnyy/j2objc-reddit-prototype/blob/master/LICENSE). Feel free to clone and modify repo as you want, but don't forget to add a reference to the author :)
