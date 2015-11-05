# Simpl Android SDK

Simpl's Android SDK makes it easy for you to integrate Simpl Buy into your apps in on the Android platform.

## Adding SDK to your project
### Gradle (Android Studio)
* Add Simpl maven repository to your applications build.gradle.
```groovy
    repositories {
        ...
        maven { url 'http://maven.getsimpl.com'}
    }
```
* Add Simpl as dependency in your project gradle file
```
dependencies {
    compile "com.simpl.android:sdk:1.0.0"
}
```
> __Important__ 

> We are using latest stable Android Build Tool version : 23.0.1, Min SDK Version : 14, Target SDK version 23 and Compile SDK version 23. 

> Check your build.gradle for the project and module to check if you are on the latest versions (> 23.0.1). 

> We are choosing the latest build tool version (23) for Android Marshmallow support. (For release notes : [link]( http://developer.android.com/tools/revisions/build-tools.html))

### Maven
Add following dependency and repository to your pom.xml
```xml
<project ...>
<dependecies>
    ...
    <dependency>
        <groupId>com.simpl.android</groupId>
        <artifactId>sdk</artifactId>
        <version>1.0.0</version>
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
 * @param applicationContext application context of the current application
 * @param runInSandboxMode boolean flag that indicates if SDK should run in Sandbox mode. If true SDK will run in Sandbox
 * mode else It will run in Production mode
 */
Simpl.init(applicationContext, runInSandboxMode);
```
## Integrating using official Simpl button
### Add following code to your layout file

```xml
<com.simpl.android.sdk.SimplButton
    android:id="@+id/simpl_buy_button"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    android:layout_margin="10dp" />
```
> Add ```android:animateLayoutChanges="true"``` to your parent layout for smooth visibility changes.

### Add following initialization to the corresponding activity
```java
SimplUser user = SimplUser.create(emailAddress, phoneNumber);
SimplTransaction transaction = SimplTransaction.create(user, amountInPaise);
SimplBuyButton button = (SimplBuyButton) findViewById(R.id.simpl_buy_button);
button.setTransaction(transaction);
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
> __Important__ 
> If user is not approved to use Simpl, SimplBuyButton visibility is set to `GONE`.
> If you want to take control of VISIBILITY then use isUserApproved method explained in the next bit.

### For checking if user is approved
```java
SimplUser user = SimplUser.create(emailAddress, phoneNumber);
Simpl.getInstance().isUserApproved(user, new SimplUserApprovalListener(){
  /**
     * Called when operation is successful
     *
     * @param status status of approval : true if user is approved and false if he is not.
     */
    void onSuccess(final boolean status){
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

# FAQs

1) I see an error saying "Couldn't resolve depenedncy "com.simpl.android:data:<version>" on the Android Studio console.

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

# Licence
Copyright © 2015, Get Simpl Technologies Private Limited
All rights reserved.

This software is proprietary, commercial software. All use must be licensed. The licensee is given the right to use the software under certain conditions, but is restricted from other uses of the software, such as modification, further distribution, or reverse engineering. Unauthorized use, duplication, reverse engineering, any form of redistribution, or use in part or in whole other than by prior, express, written and signed license for use is subject to civil and criminal prosecution. If you have received this file in error, please notify the copyright holder and destroy this and any other copies as instructed.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER ON AN "AS IS" BASIS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED, AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
