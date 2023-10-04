
# Advance Notes 

In today's fast-paced world, where hectic work schedules often lead to missed tasks and appointments, the Android Advance Notes Application stands as your go-to solution. This innovative app is designed to revolutionize your note-taking experience and enhance your overall organization. Here are its key features are...


## Screenshots


<p>
<img align="center" width="250" height="450" src="https://imgur.com/gkZ8w9u.jpg"> ||
<img align="center" width="250" height="450" src="https://imgur.com/0i1k6SC.jpg"> ||
<img align="center" width="250" height="450" src="https://imgur.com/raF2Xdo.jpg"> ||
<br></br>
<img align="center" width="250" height="450" src="https://imgur.com/NQgTPvo.jpg"> ||
<img align="center" width="250" height="450" src="https://imgur.com/6HCYFh4.jpg"> ||
<img align="center" width="250" height="450" src="https://imgur.com/8QBeTjy.jpg"> ||

 </p>


## Features

- #### Customizable Note Colors: Personalization is at your fingertips. Tailor your notes to your liking by changing their colors, ensuring a visually appealing and organized experience.

- #### Live Location Saving: Seamlessly associate specific locations with your notes. This feature is invaluable for capturing location-based information and adding context to your tasks.

- #### Efficient Note Management: Our intuitive drag-and-drop functionality simplifies the organization of your notes, streamlining your workflow and boosting productivity.

- #### Enhanced Security: Protect your private information with optional password protection, ensuring that sensitive data remains inaccessible to unauthorized users.

- #### Optimized Performance: Utilizing Room Database, the app offers improved speed and responsiveness, guaranteeing efficient data storage and retrieval.

- #### Image Integration: Elevate your notes by adding images, providing a dynamic and visual dimension to your information.

The Android Advance Notes Application empowers you to take control of your schedule, tasks, and priorities. With its user-friendly interface and feature-rich capabilities, it's your dependable personal assistant, ensuring that nothing slips through the cracks in your busy life. 

## Demo

[Download APK](https://drive.google.com/file/d/1F_KlB555PytKlX-NzuXfSHv5RXNmlkp6/view?usp=sharing)




## Run Locally

Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  run in android studio
```

Install dependencies

```bash
  Automatic dependencies on the bases of SDK version 
```

Start the server

```bash
  plugins {
    id 'com.android.application'
}

android {
    compileSdk 32

    defaultConfig {
        applicationId "com.example.simple_notes"
        minSdk 23
        targetSdk 32
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'androidx.room:room-runtime:2.4.2'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'


    implementation "androidx.room:room-common:2.4.2"
    annotationProcessor "androidx.room:room-compiler:2.4.2"

    implementation "androidx.recyclerview:recyclerview:1.2.1"

    implementation 'com.intuit.sdp:sdp-android:1.0.6'
    implementation 'com.intuit.ssp:ssp-android:1.0.6'


    implementation"com.google.android.material:material:1.6.0"

    implementation 'com.makeramen:roundedimageview:2.3.0'

    // https://github.com/bumptech/glide

    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'

    implementation 'com.google.android.gms:play-services-location:19.0.1'

}
```


## Installation


```bash
  minSdk 23
        targetSdk 32
        versionCode 1
        versionName "1.0"
```
    
