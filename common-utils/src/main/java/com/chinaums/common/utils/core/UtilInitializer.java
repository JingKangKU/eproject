package com.chinaums.common.utils.core;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.List;

import static java.util.Collections.emptyList;

public class UtilInitializer implements Initializer<Utils> {

    @NonNull
    @Override
    public Utils create(@NonNull Context context) {
        return Utils.getInstance(context);
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return emptyList();
    }
}
