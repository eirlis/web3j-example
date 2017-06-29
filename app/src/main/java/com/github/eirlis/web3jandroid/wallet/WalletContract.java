package com.github.eirlis.web3jandroid.wallet;

import com.github.eirlis.web3jandroid.BasePresenter;
import com.github.eirlis.web3jandroid.BaseView;

/**
 * Created by eirlis on 29.06.17.
 */

public class WalletContract {

    interface View extends BaseView<WalletContract.Presenter> {

        void showBalance();

        void showWalletAddress();
    }

    interface Presenter extends BasePresenter {

        void getWalletBalance();

        void getWalletAddress();
    }
}
