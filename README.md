# Persian-Location-Picker
A simple and easy way to check your internet connection and trace it changes.

# Requirements
* Android 4.0.3  (API lvl 15) or greater
* Android Studio ;)


# Installation 

* in your build.gradle(app) add :
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
        maven { 
            url "https://maven.google.com"
          }
      }
  }
```
* then in dependency part add :
```
   implementation 'com.github.BehradJafari:Persian-Location-Picker:1.0.4'

```
# How it works

this library use ***Neshan*** api for searching and use ***GoogleMap*** for its map.</p>

# Prerequisites
Sign up in [Nehsan developer](https://developers.neshan.org/panel/site/signup) to get your api_key.
then add your app in [developer console](https://console.developers.google.com) to get yout _google_maps_key_ and enable its map sdk.


# How to Use 

* Manifest <br/>
add these tags in your manifest file 
```xml
<application 

     ...
             
     android:usesCleartextTraffic="true"

>
<activity android:name="ir.hamiss.persianaddresspicker.Maps.MapsActivity" />
<activity android:name="ir.hamiss.persianaddresspicker.SearchAddress.SearchAddress" />
<meta-data android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />
<uses-library android:name="org.apache.http.legacy" android:required="false"/>
  
</application>

```

Example usage
```java

     AddressPickerBuilder.getInstance()
                    .setDrawable_src(R.drawable.location_placeholder)
                    .setEnable_info(true)
                    .setHint_info("آدرس محل کار")
                    .setHint_title("نام محل کار")
                    .setDialog_string("جزئیات")
                    .setNeshan_api_key("api_key")
                    .setRequest_code(REQUEST_ADDRESS)
                    .setSearch_alley("جستجو محله")
                    .setZoom_map(14)
                    .build(this);

```

get result in your activity 
```java

@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_ADDRESS){
            if (resultCode==RESULT_OK){
                if (data!=null) {
                    Bundle extras = data.getExtras();
                    if (extras!=null) {

                        SearchItem loc = (SearchItem) extras.get(getString(ir.hamiss.persianaddresspicker.R.string.location_searched));

                       //do your staff with loc methods


                    }
                }
            }
        }

```
# methods
| method | Description |
| --- | --- |
| setDrawable_src | location picker icon in GoogleMap setter |
| setEnable_info | Show second edittext for get info of selected address |
| setHint_title | set title edittext (first edittext ) hint |
| setHint_info | set info edittext (second edittext ) hint |
| setDialog_string | get more detail dialog title setter |
| setNeshan_api_key | set your api_key that you get from neshan |
| setRequest_code | handle for onActivityResult method |
| setSearch_alley | open search alley activty  button text setter  |
| setSearch_alley_drawable | open search alley activty  button drawble setter |
| setStart_latitude | start lat for map and search area for alley searching (default is tehran's lat) | 
| setStart_longitude | start lon for map and search area for alley searching (default is tehran's lon) | 
| setZoom_map | set default zoom in map  |






# Hamiss 
visit our website :
[Hamiss.ir](https://www.hamiss.ir "Hamiss's Homepage")


![alt text](https://hamiss.ir/img/bg-img/logo.png "Logo Title Text 1")

# Finto 
visit our website :
[finto.ir](https://www.finto.ir "finto's Homepage")


![alt text](https://startupforum.ir/images/cdn/2019/01/40.png )
