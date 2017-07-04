# Simpl Android SDK migration from old(before 1.1.6) to new(1.1.+)

## Add new permission in your AndroidManifest.xml
In AndroidManifest.xml of your application project add the following permission (if it's already not there).
```xml

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
```
## Changes in approval call

### Old approval method
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

### New Approval method
```java
SimplUser user = new SimplUser(emailAddress, phoneNumber);
Simpl.getInstance().isUserApproved(user)
                   .addParam("user_location", "18.9750,72.8258")
                   .addParam("order_id", "AB12ORD")
                   .addParam("transaction_amount_in_paise", String.valueOf(transactionAmountInPaise))
                   .addParam("member_since", "2017-01-08")
                   .execute(new SimplUserApprovalListenerV2() {
                    /**
                    * Called when operation is successful
                    *
                    * @param status                status of approval : true if user is approved and false if he is
                    *                              not.
                    * @param buttonText            Text that should appear on Simpl payment option
                    * @param showSimplIntroduction Boolean to indicate that User should be shown an introduction
                    *                              modal related to Simpl
                    */
                    @Override
                    public void onSuccess(boolean status, final String buttonText, boolean showSimplIntroduction) {
                        // Check the status, If (status is true-> User is approved and show the payment button
                        // else don't)
                        // Use "buttonText" to show the same on Payment button
                    }
                    /**
                    * Called when opration is unsuccessful
                    *
                    * @param throwable reason of the exception. Use throwable.getMessage() to show user readable
                    *                  error
                    */
                    @Override
                    public void onError(Throwable throwable) {

                    }
                });
```
## Important
Replace callback function ~~SimplUserApprovalListener()~~ with  **SimplUserApprovalListenerV2()**, and override new 
**onSuccess()** method which will have one more argument **String buttonText**

## Changes in Simpl Session
Use ```javaSimpl.getInstance().isSimplApproved()``` to check either user is approved or not in current session.

# Licence
Copyright © 2015, Get Simpl Technologies Private Limited
All rights reserved.

This software is proprietary, commercial software. All use must be licensed. The licensee is given the right to use the software under certain conditions, but is restricted from other uses of the software, such as modification, further distribution, or reverse engineering. Unauthorized use, duplication, reverse engineering, any form of redistribution, or use in part or in whole other than by prior, express, written and signed license for use is subject to civil and criminal prosecution. If you have received this file in error, please notify the copyright holder and destroy this and any other copies as instructed.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER ON AN "AS IS" BASIS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED, AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
