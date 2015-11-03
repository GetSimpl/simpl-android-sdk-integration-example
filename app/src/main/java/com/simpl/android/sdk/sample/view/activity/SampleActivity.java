/*
 * Copyright © 2015, Get Simpl Technologies Private Limited
 * All rights reserved.
 *
 * This software is proprietary, commercial software. All use must be licensed. The licensee is given the right to use the software under certain conditions, but is restricted from other uses of the software, such as modification, further distribution, or reverse engineering. Unauthorized use, duplication, reverse engineering, any form of redistribution, or use in part or in whole other than by prior, express, written and signed license for use is subject to civil and criminal prosecution. If you have received this file in error, please notify the copyright holder and destroy this and any other copies as instructed.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER ON AN "AS IS" BASIS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED, AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.simpl.android.sdk.sample.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.simpl.android.sdk.SimplTransaction;
import com.simpl.android.sdk.SimplUser;

/**
 * Main activity of the app for setting up the user and transaction amount
 */
public class SampleActivity extends AppCompatActivity {
    /**
     * TAG for logging
     */
    private static final String TAG = "##SampleActivity##";


    /**
     * Activity lifecycle event
     *
     * @param savedInstanceState {@link Bundle} for saved instance
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.simpl.android.sdk.sample.R.layout.activity_sample);
        findViewById(com.simpl.android.sdk.sample.R.id.set_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = ((EditText) findViewById(com.simpl.android.sdk.sample.R.id.email)).getText().toString();
                String phoneNumber = ((EditText) findViewById(com.simpl.android.sdk.sample.R.id.phone_number)).getText().toString();
                int transactionAmountInPaise = Integer.parseInt(((EditText) findViewById(com.simpl.android.sdk.sample.R.id
                        .amount)).getText().toString());
                startActivity(new Intent(getApplicationContext(), CheckoutActivity.class)
                        .putExtra("transaction", new SimplTransaction(new SimplUser(emailAddress, phoneNumber
                        ), transactionAmountInPaise)));
            }
        });

    }

}
