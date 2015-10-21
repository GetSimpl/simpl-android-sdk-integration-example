package com.simpl.android.sdk.sample.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.simpl.android.sdk.SimplAuthorizeTransactionListener;
import com.simpl.android.sdk.SimplButton;
import com.simpl.android.sdk.SimplTransaction;
import com.simpl.android.sdk.SimplTransactionAuthorization;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.simpl.android.sdk.sample.R.layout.activity_checkout);
        // Creating the transaction from the extras
        final SimplTransaction simplTransaction = getIntent().getExtras().getParcelable
                ("transaction");
        if (simplTransaction == null) {
            Log.e(TAG, "Transaction can not be null");
            finish();
            return;
        }
        // Getting hold of the SimplButton
        SimplButton simplButton = (SimplButton) findViewById(com.simpl.android.sdk.sample.R.id.pay_by_simple);
        simplButton.setTransaction(simplTransaction);
        simplButton.setSimplAuthorizeTransactionListener(new SimplAuthorizeTransactionListener() {
            @Override
            public void onSuccess(SimplTransactionAuthorization transactionAuthorization) {
                Log.d(TAG, transactionAuthorization.toString());
                Toast.makeText(getApplicationContext(), "Transaction is successful with token => " +
                        "" + transactionAuthorization.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(final Throwable throwable) {
                Log.e(TAG, "While authorizing a transaction", throwable);
                Toast.makeText(getApplicationContext(), "Error " + throwable.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
