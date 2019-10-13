![alt text](https://github.com/pankaj89/MasterExoPlayer/blob/master/master_exo_player_banner.svg)

# MasterExoPlayer for Recyclerview (build with kotlin)
####  #3 Line code for playing video inside RecyclerView

[![N|Solid](https://img.shields.io/badge/Android%20Arsenal-Simpler%20Recycler%20View%20Adapter-brightgreen.svg)](https://android-arsenal.com/details/1/5354)

#### MasterExoPlayer is lightweight utility for helping Play Video inside RecyclerView.

# Features
- ##### Easy to use (Just 3 line of code)
- ##### No Need to create different view holder to support playing video
- ##### Support for playing video inside horizontal recyclerview inside RecyclerView Item like instagram
- ##### Can handle autoplay, mute, logic to play by area(whether video is 75% visible then starts Play)
- ##### Just attach MasterExoPlayerHelper to recyclerview that's enought, player will play most visible video automatically based on your configuration

### Setup
Include the following dependency in your build.gradle files.
```
// Project level build.gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

// App level build.gradle
dependencies {
    implementation 'com.github.pankaj89:MasterExoPlayer:1.0'
}
	
```
# How to use

#### Attach to RecyclerView

### 1. Add MasterExoPlayer inside RecyclerView Item
```
<com.master.exoplayer.MasterExoPlayer
    android:id="@+id/masterExoPlayer"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### 2. Set url of your video view to MasterExoPlayer inside your RecyclerView Adapter onBindViewHolder
```kotlin
binding.frame.url = model.sources
```

### 3. Attach MasterExoPlayerHelper to RecyclerView
```kotlin
val recyclerView: RecyclerView = ....

val masterExoPlayerHelper = MasterExoPlayerHelper(mContext = this, id = R.id.masterExoPlayer)
masterExoPlayerHelper.makeLifeCycleAware(this)
masterExoPlayerHelper.attachToRecyclerView(recyclerView)

```

# Configuration
#### Constructor parameters for MasterExoPlayerHelper

```
1. id : Int 
   View id of ExoPlayerView used in item layout
```
```
2. autoPlay : Boolean 
    If you want to autoplay video once loaded
```
```
3. playStrategy : Float 
    Visible area from 0 to 1, Which matches to play video, default value = PlayStrategy.DEFAULT i.e 0.75 means 75% area visible to starts play
```
```
4. muteStrategy: Values from MuteStratagy.ALL or MuteStratagy.INDIVIDUAL 
    Defines whether mute/unmute affects all rows or individual
```
```
5. defaultMute:Boolean 
If default video should be muted or not
```
```
6. loop:Int 
    Defines if you want to loop the video, default is unlimited, if set to 1 it will play only 1 time then stoop.
```

### Special Thanks to
###### Exo Player by Google [(<u><i>link</i></u>)](https://github.com/google/ExoPlayer)
###### Simple Adapter for RecyclerView [(<u><i>link</i></u>)](https://github.com/pankaj89/SimpleAdapter)
###### Coil Image Loading library for Kotlin [(<u><i>link</i></u>)](https://github.com/coil-kt/coil)

### My Other Libraries
###### Runtime Permission Helper [(<u><i>link</i></u>)](https://github.com/google/ExoPlayer)
###### Simple Adapter for RecyclerView [(<u><i>link</i></u>)](https://github.com/pankaj89/PermissionHelper)
### License
```
Copyright 2017 Pankaj Sharma

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
