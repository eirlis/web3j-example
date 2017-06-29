package com.github.eirlis.web3jandroid.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.eirlis.web3jandroid.R;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;

/**
 * Created by eirlis on 29.06.17.
 */

public class WalletActivity extends AppCompatActivity {

    public static final String TAG = WalletActivity.class.getName();

    private TextView mWalletAddress;

    private TextView mBalance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Web3j web3j = Web3jFactory.build(new HttpService());

        mWalletAddress = (TextView) findViewById(R.id.account_address);
        mBalance = (TextView) findViewById(R.id.wallet_balance);

        Bundle extras = getIntent().getExtras();
        mWalletAddress.setText(extras.getString("WalletAddress"));
    }
}
