package com.github.eirlis.web3jandroid;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 0;

    private Button mGenerateWalletButton;

    private EditText mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Web3j web3j = Web3jFactory.build(new HttpService());

        mGenerateWalletButton = (Button) findViewById(R.id.generate_wallet_button);
        mPassword = (EditText) findViewById(R.id.password);

        mGenerateWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                        .PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION_WRITE_STORAGE);
                } else {
                    generateWallet();
                }
            }
        });
    }

    public void generateWallet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fileName;
                try {
                    String password = mPassword.getText().toString();
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    if (!path.exists()) {
                        path.mkdir();
                    }
                    fileName = WalletUtils.generateLightNewWalletFile(password, new File(String.valueOf(path)));
                    Log.e("TAG", "generateWallet: " + path+ "/" + fileName);
                    Credentials credentials =
                            WalletUtils.loadCredentials(
                                    password,
                                    path + "/" + fileName);
                    Log.e("TAG", "generateWallet: " + credentials.getAddress() + " " + credentials.getEcKeyPair().getPublicKey());
                } catch (NoSuchAlgorithmException
                        | NoSuchProviderException
                        | InvalidAlgorithmParameterException
                        | IOException
                        | CipherException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_STORAGE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                } else {
                    generateWallet();
                }
                break;
            }
        }
    }
}
