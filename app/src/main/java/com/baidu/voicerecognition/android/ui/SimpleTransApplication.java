package com.baidu.voicerecognition.android.ui;

import android.app.Application;

/**
 * Created by fujiayi on 2017/10/18.
 */

public class SimpleTransApplication extends Application {
    private DigitalDialogInput digitalDialogInput;


    public DigitalDialogInput getDigitalDialogInput() {
        return digitalDialogInput;
    }

    public void setDigitalDialogInput(DigitalDialogInput digitalDialogInput) {
        this.digitalDialogInput = digitalDialogInput;
    }
}
