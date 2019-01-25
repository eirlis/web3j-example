package com.github.eirlis.web3jandroid.generation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.eirlis.web3jandroid.R;
import com.github.eirlis.web3jandroid.wallet.WalletActivity;

public class GenerationActivity extends AppCompatActivity implements GenerationContract.View {

    public static final String TAG = GenerationActivity.class.getName();

    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 0;

    private GenerationContract.Presenter mWalletPresenter;

    private Button mGenerateWalletButton;

    private String mWalletAddress;

    private EditText mPassword;

    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation);

        mGenerateWalletButton = (Button) findViewById(R.id.generate_wallet_button);
        mPassword = (EditText) findViewById(R.id.password);
        mProgressBar = (ProgressBar) findViewById(R.id.generate_wallet_progress);

        mGenerateWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(GenerationActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            GenerationActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION_WRITE_STORAGE);
                }
                else if (mPassword.getText().toString().length() <= 0){
                    Toast.makeText(GenerationActivity.this,"Password field cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {

                    mProgressBar.setVisibility(View.VISIBLE);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mWalletPresenter = new GenerationPresenter(GenerationActivity.this,
                                    mPassword.getText().toString());
                            mWalletPresenter.generateWallet(mPassword.getText().toString());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(GenerationActivity.this, WalletActivity.class);
                                    intent.putExtra("WalletAddress", mWalletAddress);
                                    startActivity(intent);

                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    public void setPresenter(GenerationContract.Presenter presenter) {
        mWalletPresenter = presenter;
    }

    @Override
    public void showGeneratedWallet(String address) {
        mWalletAddress = address;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_STORAGE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                } else {
                    mWalletPresenter.generateWallet(mPassword.getText().toString());
                }
                break;
            }
        }
    }
}
