package com.github.eirlis.web3jandroid.generation;

import android.os.Environment;
import android.util.Log;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by eirlis on 29.06.17.
 */

public class GenerationPresenter implements GenerationContract.Presenter {

    private final GenerationContract.View mWalletGenerationView;

    private String mPassword;

    public GenerationPresenter(GenerationContract.View walletGenerationView, String password) {
        mWalletGenerationView = walletGenerationView;
        mPassword = password;
    }

    @Override
    public void generateWallet(final String password) {
        String fileName;
        try {
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
            mWalletGenerationView.showGeneratedWallet(credentials.getAddress());
            Log.e("TAG", "generateWallet: " + credentials.getAddress() + " " + credentials.getEcKeyPair().getPublicKey());
        } catch (NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | IOException
                | CipherException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        generateWallet(mPassword);
    }
}
