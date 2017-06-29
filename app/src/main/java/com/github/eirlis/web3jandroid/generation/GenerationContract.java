package com.github.eirlis.web3jandroid.generation;

import com.github.eirlis.web3jandroid.BasePresenter;
import com.github.eirlis.web3jandroid.BaseView;

/**
 * Created by eirlis on 29.06.17.
 */

public interface GenerationContract {

    interface View extends BaseView<Presenter> {

        void showGeneratedWallet(String walletAddress);
    }

    interface Presenter extends BasePresenter {

        void generateWallet(String password);
    }
}
