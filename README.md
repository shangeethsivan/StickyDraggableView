# StickyDraggableView

[![Preview the app](https://img.shields.io/badge/Preview-Appetize.io-orange.svg)](https://appetize.io/app/bp0hpuq61x1t7hmfjwktarjdnr)

## Created a view which looks alike Google Duo/WhatsApp VideoCall thumbnail which is draggable and sticks to corners on release

<img src="https://raw.githubusercontent.com/shivthepro/StickyDraggableView/master/StickyDraggable.gif" alt="" height="400" />

## Dependency

Available in [Maven](https://search.maven.org/search?q=stickydragview)

Add the library to your module `build.gradle`
```gradle
dependencies {
    implementation 'com.github.shivthepro:stickydragview:0.0.1'
}
```

## Usage
```xml
<!-- Important Note : StickyDragView is a extension of RelativeLayout -->
<co.shrappz.stickydrag.StickyDragView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".VideoCallActivity">

    <!-- Your Other Layouts -->
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/shape" />

    <!-- Content you want to make StickyDraggable [Use framelayout only for the content you want to make dragabble]-->
    <FrameLayout
        android:id="@+id/draggable_content"
        android:layout_width="100dp"
        android:layout_height="150dp"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_margin="10dp"
            android:background="@drawable/shape_2"
            android:gravity="center"
            android:text="Testing Draggable"
            android:textColor="#ffffff" />

    </FrameLayout>

</co.shrappz.stickydrag.StickyDragView>
```
That's it!
