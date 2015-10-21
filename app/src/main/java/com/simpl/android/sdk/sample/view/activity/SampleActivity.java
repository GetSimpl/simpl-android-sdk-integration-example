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
