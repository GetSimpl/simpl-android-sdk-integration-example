# Simpl Android SDK

Simpl's Android SDK makes it easy for you to integrate Simpl Buy into your Android apps.

## Adding SDK to your project
### Gradle (Android Studio)
* Add Simpl maven repository to your applications build.gradle.

```groovy
    repositories {
        ...
        maven { url 'http://maven.getsimpl.com'}
    }
```
* And then add Simpl SDK as dependency.
```groovy
dependencies {
    compile "com.simpl.android:sdk:1.1.+"
}
```
__Important__ 
> We are using Android Build Tool version : 22.0.1, Min SDK Version : 14, Target SDK version 22 and Compile SDK version 22. 

> Check your build.gradle for the project and module to check if you are on the build tool version >= 22.0.1. 

### Maven
Add following dependency and repository to your pom.xml
```xml
<project ...>
<dependecies>
    ...
    <dependency>
        <groupId>com.simpl.android</groupId>
        <artifactId>sdk</artifactId>
        <version>LATEST</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
      <id>com.simpl</id>
      <url>http://maven.getsimpl.com</url>
    </repository>
 </repositories>
</project>
```

## Update your AndroidManifest.xml
In AndroidManifest.xml of your application project add the following permission (if it's already not there).
```xml
<uses-permission android:name="android.permission.INTERNET"/>
<!-- Required for automatic OTP detection for first time user -->
<uses-permission android:name="android.permission.RECEIVE_SMS"/>
<uses-permission android:name="android.permission.READ_SMS"/>
<application ...>
    <meta-data
        android:name="com.simpl.android.sdk.merchant_id"
        android:value="MERCHANT_ID_FROM_SIMPL_DASHBOARD"/>
</application>
```

## Initialization
```java
/**
 * For initializing Simpl SDK
 *
 * @param application      Current {@link Application} instance  
 */
Simpl.init(application);
```
> We do not store any reference to ```application``` parameter, so there is no possibility of cyclic reference.

### To run Simpl in Sandbox mode
```java
Simpl.getInstance().runInSandboxMode();
```
### If you are working with hashed phone numbers for approval call
```java
Simpl.getInstance().usingHashedPhoneNumber();
```
## Integrating using official Simpl button
### Add following code to your layout file

```xml
<com.simpl.android.sdk.SimplButton
    android:id="@+id/simpl_button"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    android:layout_margin="10dp" />
```
> Add ```android:animateLayoutChanges="true"``` to your parent layout for smooth visibility changes.

### Add following initialization to the corresponding activity
```java
SimplUser user = new SimplUser(emailAddress, phoneNumber);
SimplTransaction transaction = new SimplTransaction(user, amountInPaise);
SimplButton button = (SimplButton) findViewById(R.id.simpl_button);
button.setTransaction(transaction, 
    SimplParam.create("user_location","18.9750,72.8258")
    SimplParam.create("theatre_location","18.9750,72.8258")
    SimplParam.create("member_since","(2016-01-08"));
button.setAuthorizeTransactionListener(new SimplAuthorizeTransactionListener() {
    /**
     * Called when operation is successful
     *
     * @param auth {@link Authorization} module
     */
    void onSuccess(final Authorization auth){
    }
    /**
     * Called when opration is unsuccessful
     *
     * @param throwable reason of the exception. Use throwable.getMessage() to show user readable error
     */
    void onError(final Throwable throwable){
    }
});
```
#### If you are using hashedPhoneNumbers
```java
SimplUser user = new SimplUser(emailAddress, phoneNumber);
SimplTransaction transaction = new SimplTransaction(user, amountInPaise);
SimplButton button = (SimplButton) findViewById(R.id.simpl_button);
button.addUserApprovalParam("user_location","18.9750,72.8258");
button.addUserApprovalParam("member_since","2016-01-08");
button.setTransaction(hashedPhoneNumber, emailAddress, amountInPaise,
    SimplParam.create("phone_number","9977880999")
    SimplParam.create("theatre_location","18.9750,72.8258"));    
button.setAuthorizeTransactionListener(new SimplAuthorizeTransactionListener() {
    /**
     * Called when operation is successful
     *
     * @param auth {@link Authorization} module
     */
    void onSuccess(final Authorization auth){
    }
    /**
     * Called when operation is unsuccessful
     *
     * @param throwable reason of the exception. Use throwable.getMessage() to show user readable error
     */
    void onError(final Throwable throwable){
    }
});
```
__Important__
> These callbacks are returned on a background thread. If you are performing any UI related work on these 
> callbacks, please use [Handler](http://developer.android.com/reference/android/os/Handler.html) or [runOnUIThread](http://developer.android.com/reference/android/app/Activity.html#runOnUiThread(java.lang.Runnable)).

> If user is not approved to use Simpl, SimplButton visibility is set to `GONE`.
> If you want to take control of VISIBILITY then use isUserApproved method explained in the next bit.

### Customizing ```SimplButton```
We allow customizing SimplButton as per your branding needs. For using style attributes (in your XML layout SimplButton tag) add ```xmlns:simpl="http://schemas.android.com/apk/res-auto"``` to the top-most ViewGroup (i.e. parent view) of your layout file. 

Example Layout File:
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:simpl="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.simpl.android.sdk.SimplButton
        android:id="@+id/pay_by_simple"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:layout_margin="10dp"
        simpl:simpl_buttonColor="@android:color/background_dark"
        simpl:simpl_buttonHeight="24dp"
        simpl:simpl_titleTextSize="12sp"/>
</RelativeLayout>
```

Property|Style Attribute| Java Method|
---------|---|------|
Title Color|```simpl:simpl_titleTextColor="@color/black"```|```simplButton.setTitleTextColor(Color.BLUE);```| 
Title Text Size|```simpl:simpl_titleTextSize="12sp"```|```simplButton.setTitleTextSize(spToPx(12));```|
Title Text|```simpl:simpl_titleText="Buy with Simpl"```|```simplButton.setTitleText("Buy with Simpl")```|
Button Color|```simpl:simpl_buttonColor="@color/white"```|```simplButton.setButtonColor(Color.WHITE)```|
Button Height|```simpl:simpl_buttonHeight="24dp"```|```simplButton.setButtonHeight(dpToPx(20))```|
Button Shadow Color|```simpl:simpl_buttonShadowColor="@color/white"```|```simplButton.setButtonShadowColor(Color.BLACK)```|
Title Typeface||```simplButton.setTitleTextTypeface(typeface)```|
Powered By Text Color|```simpl:simpl_poweredByTextColor="@color/white"```|```simplButton.setPoweredByTextColor(Color.BLACK)```|


## Using custom button

### First check if user is approved to use Simpl platform
```java
SimplUser user = new SimplUser(emailAddress, phoneNumber);
Simpl.getInstance().isUserApproved(user)
                    .addParam("user_location","18.9750,72.8258")
                    .addParam("theatre_location","18.9750,72.8258")
                    .addParam("member_since","(2016-01-08")                                                     
                    .execute(new SimplUserApprovalListener(){
                                /**
                                  * Called when operation is successful
                                  *
                                  * @param status                status of approval : true if user is approved and false if he is not.
                                  * @param showSimplIntroduction Boolean to indicate that User should be shown an introduction
                                  *                              modal related to Simpl
                                  */
                                 void onSuccess(final boolean status, final boolean showSimplIntroduction){
                                 }
                                 /**
                                  * Called when opration is unsuccessful
                                  *
                                  * @param throwable reason of the exception. Use throwable.getMessage() to show user readable error
                                  */
                                 void onError(final Throwable throwable){
                                 }
                             });
```
* We track Phone Manufacturer and Model with this API call, which requires no special permissions in the AndroidManifest. Technically we are using `Build.MANUFACTURER` and `Build.MODEL.`
* Also we have routines for tracking location of the device and IMEI number along with this API 
call. But as this requires special permissions in the AndroidManifest, we will check if the 
current package has the corresponding permission or not, and we will track that info only if it 
is available.

__Important__
> These callbacks are returned on a background thread. If you are performing any UI related work on these 
> callbacks, please use [Handler](http://developer.android.com/reference/android/os/Handler.html) or [runOnUIThread](http://developer.android.com/reference/android/app/Activity.html#runOnUiThread(java.lang.Runnable)).

__What is ```showSimplIntroduction``` parameter in ```onSuccess```?__

A. Simpl is a platform, which can be accessed on web as well as on mobiles. So this boolean indicates that if the user has used Simpl before on any other platform. In other words, this boolean indicates that if the user is aware of Simpl payment method or not. If boolean value is ```true``` then user needs to be introduced to Simpl payment method, and in case it is ```false```, user already knows Simpl as he has already transacted using Simpl.

### When user clicks on the custom button
Make sure that you have called ```isUserApproved``` API before calling this API.
```java
/**
  * To authorize a transaction
  *
  * @param context                      Current {@link Context}
  * @param transactionAmountInPaise     Transaction amount in paise  
  */
public SimplAuthorizeTransactionRequest authorizeTransaction(@NonNull final Context context,
                                                             @NonNull final long 
                                                             transactionAmountInPaise) 
                                                             throws SimplException;                               
```
__Example__
```java
// You have called isUserApproved() in this or any previous activity
Button normalButton = (Button) findViewById(R.id.normal_button);
normalButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try{
            Simpl.getInstance().authorizeTransaction(getActivity(), transactionAmountInPaise)
                    //Sending extra params about the transaction
                    .addParam("user_location","18.9750,72.8258")
                    .execute(new SimplAuthorizeTransactionListener() {
                         @Override
                         public void onSuccess(SimplTransactionAuthorization transactionAuthorization) {
                         }
             
                         @Override
                         public void onError(final Throwable throwable) {
                         }
                     })
         }catch(Exception exception){
            //handle exception
         }
    }
});
```

## Using ```SimplSession```
```SimplSession``` is a session storage used for storing Simpl modules to use them across activities. There are three methods provided by ```SimplSession``` class :
```java
public SimplUser getSimplUser();

public void setSimplUser(SimplUser simplUser);

public UserApproval getUserApproval();
```
* You can get access to the current ```SimplSession``` using ```Simpl.getInstance().getSession()``` method. 
* ```getUserApproval()``` will return a null object, unless you use ```Simpl.getInstance().isUserApproved(...)``` method. 
* ```SimplSession``` caches the result of ```Simpl.getInstance().isUserApproved(...)``` call along with the passed ```SimplUser``` object. You can access this ```SimplUser``` instance using ```Simpl.getInstance().getSession().getSimplUser()```.
* The result of  ```Simpl.getInstance().isUserApproved(...)``` is also cached in the current session and reused during current session of ```Simpl```. You can access this result using ```Simpl.getInstance().getSession().getUserApproval()``` method.
* One session is valid for one transaction. As soon as the transaction is over, we destroy the 
session. You will have to call authorize again.
* This feature is still in beta, so ping us @ help@getsimpl.com for any help or issue.



### Helper Methods
```java
/**
 * Method to get  Pixels from Display Pixels
 *
 * @param context Current {@link Context}
 * @param dp      Display Pixels
 * @return Pixel value
 */
public static int dpToPx(final Context context, final int dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context
            .getResources().getDisplayMetrics());
}

/**
 * Method to get  Pixels from screen pixels
 *
 * @param context Current {@link Context}
 * @param sp Screen pixels
 * @return Pixel value
 */
public static float spToPx(final Context context, float sp){
    final float scale = context.getResources().getDisplayMetrics().scaledDensity;
    return sp * scale;
}
```

## Proguard rules
No need of any changes specific to us. We carry our proguard file along with the AAR.

# Example
Please refer [this](https://github.com/GetSimpl/simpl-android-sdk-integration-example) repository while integrating Simpl Android SDK into your App.

# Issues
You can report issues/feature requests related with Android SDK [here](https://github.com/GetSimpl/simpl-android-sdk-integration-example/issues?q=is%3Aopen+is%3Aissue).

# FAQs

1) I see an error saying "Couldn't resolve dependency "com.simpl.android:data:<version>" on the Android Studio console.

-> It's sometimes because of the redirect failure. Just retry building the app once or twice. If the issue persists contact help@getsimpl.com

2) I see following error in the terminal while building the app using gradle command
   ```console
   Starting a new Gradle Daemon for this build (subsequent builds will be faster).
   Parallel execution with configuration on demand is an incubating feature.
   
   FAILURE: Build failed with an exception.
   
   * What went wrong:
   A problem occurred configuring project ':app'.
   > Could not download data.aar (com.simpl.android:data:0.0.1)
      > Could not get resource 'http://maven.getsimpl.com/com/simpl/android/data/0.0.1/data-0.0.1.aar'.
         > Could not HEAD 'http://maven.getsimpl.com/com/simpl/android/data/0.0.1/data-0.0.1.aar'. Received status code 502 from server: Bad Gateway
   
   * Try:
   Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output.
   
   BUILD FAILED
   
   Total time: 2 mins 44.481 secs
  ```

-> This is also the result of redirect failure. Just retry building the app once or twice. If the issue persists contact help@getsimpl.com.

3) [x] button on SDK activity is not closing the screen?

-> All the SDK callbacks are returned on a background thread. If you are performing any UI related work on these  callbacks, please use [Handler](http://developer.android.com/reference/android/os/Handler.html) or [runOnUIThread](http://developer.android.com/reference/android/app/Activity.html#runOnUiThread(java.lang.Runnable)).


# Licence
Copyright © 2015, Get Simpl Technologies Private Limited
All rights reserved.

This software is proprietary, commercial software. All use must be licensed. The licensee is given the right to use the software under certain conditions, but is restricted from other uses of the software, such as modification, further distribution, or reverse engineering. Unauthorized use, duplication, reverse engineering, any form of redistribution, or use in part or in whole other than by prior, express, written and signed license for use is subject to civil and criminal prosecution. If you have received this file in error, please notify the copyright holder and destroy this and any other copies as instructed.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER ON AN "AS IS" BASIS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED, AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
