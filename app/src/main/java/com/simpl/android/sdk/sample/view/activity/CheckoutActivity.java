/*
 * Copyright © 2015, Get Simpl Technologies Private Limited
 * All rights reserved.
 *
 * This software is proprietary, commercial software. All use must be licensed. The licensee is given the right to use the software under certain conditions, but is restricted from other uses of the software, such as modification, further distribution, or reverse engineering. Unauthorized use, duplication, reverse engineering, any form of redistribution, or use in part or in whole other than by prior, express, written and signed license for use is subject to civil and criminal prosecution. If you have received this file in error, please notify the copyright holder and destroy this and any other copies as instructed.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER ON AN "AS IS" BASIS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED, AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.simpl.android.sdk.sample.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.simpl.android.sdk.Simpl;
import com.simpl.android.sdk.SimplAuthorizeTransactionListener;
import com.simpl.android.sdk.SimplButton;
import com.simpl.android.sdk.SimplTransaction;
import com.simpl.android.sdk.SimplTransactionAuthorization;
import com.simpl.android.sdk.sample.R;

/**
 * Final checkout activity where {@link SimplButton} is inflated in the view.
 * Simpl SDK checks if the {@link com.simpl.android.sdk.SimplUser} from the provided
 * {@link SimplTransaction} is approved for using Simpl. If he is approved then user will see the
 * button, and if he is not, the {@link SimplButton} will set its visibility to GONE.
 */
public class CheckoutActivity extends AppCompatActivity {
    /**
     * TAG for logging
     */
    private static final String TAG = "##CheckoutActivity##";
    private SimplTransaction mSimplTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.simpl.android.sdk.sample.R.layout.activity_checkout);
        // Creating the transaction from the extras
        final Handler handler = new Handler();
        mSimplTransaction = getIntent().getExtras().getParcelable("transaction");
        Button normalButton = (Button) findViewById(R.id.normal_button);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Simpl.getInstance().authorizeTransaction(CheckoutActivity.this,mSimplTransaction.getAmountInPaise())
                        .execute(new SimplAuthorizeTransactionListener() {
                            @Override
                            public void onSuccess(final SimplTransactionAuthorization
                                                          transactionAuthorization) {
                                Log.d(TAG, transactionAuthorization.toString());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Transaction is successful with token => " +
                                                "" + transactionAuthorization.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }


                            @Override
                            public void onError(final Throwable throwable) {
                                Log.e(TAG, "While authorizing a transaction", throwable);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Error " + throwable.getMessage(), Toast.LENGTH_LONG)
                                                .show();
                                    }
                                });

                            }
                        });
            }
        });
        SimplButton simplButton = (SimplButton) findViewById(R.id.pay_by_simple);
        simplButton.setTransaction(mSimplTransaction);
        simplButton.setSimplAuthorizeTransactionListener(new SimplAuthorizeTransactionListener() {
            @Override
            public void onSuccess(final SimplTransactionAuthorization transactionAuthorization) {
                Log.d(TAG, transactionAuthorization.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Transaction is successful with token => " +
                                "" + transactionAuthorization.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }


            @Override
            public void onError(final Throwable throwable) {
                Log.e(TAG, "While authorizing a transaction", throwable);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error " + throwable.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
