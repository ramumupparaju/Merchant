package com.incon.connect.ui.splash;

import com.incon.connect.ui.BaseView;

public interface SplashContract {

    interface View extends BaseView {
        void navigateToMainScreen();
    }

    interface Presenter {
        // Empty since presenter not required for splash screen
    }
}
