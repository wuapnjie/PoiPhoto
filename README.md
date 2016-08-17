# PoiPhoto
A simple Photo Selecter

## What is PoiPhoto
PoiPhoto is a simple lib to select photos for Android.
<br>
![](http://7xrqmj.com1.z0.glb.clouddn.com/anim.gif)
##How to use
###Gradle
```groovy
<<<<<<< HEAD
compile 'com.flying.xiaopo:poiphoto:0.4.0'
=======
compile 'com.flying.xiaopo:poiphoto:0.3.2'
>>>>>>> 94faccb9db0462cb27aed12e2c0aff3c2ad9b0d7
```
###AndroidManifest.xml
```xml
<uses-permission
    android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="23"/>
<uses-permission
    android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="23"/>
```
###Java
```java
PhotoPicker.newInstance()
          .setAlbumTitle("Album")
          .setPhotoTitle("Photo")
          .setToolbarColor(Color.BLACK)
          .setToolbarTitleColor(Color.WHITE)
          .setMaxNotice("can not select more") //the message when user selected photos too more
          .setStatusBarColor(Color.BLACK)   //when sdk >21 ,it will work
          .setMaxCount(6)             //max count of selected count
          .pick(MainActivity.this);   //context
  ```
  also
```java
PhotoPicker.newInstance()
                .inflate(RecyclerView, RecyclerView.LayoutManager);
```
or just get data
```java
PhotoManager photoManager = new PhotoManager(context);
```
###To Get Data
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == Define.DEFAULT_REQUEST_CODE) {
        //to get path of the selected photos
        List<String> paths = data.getStringArrayListExtra(Define.PATHS);
        //to get datatype of photo of the selected photos
        List<Photo> photos = data.getParcelableArrayListExtra(Define.PHOTOS);
    }
}
```
