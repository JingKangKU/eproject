package com.ums.eproject.app;



import android.content.Context;

import androidx.core.content.ContextCompat;

import com.ums.eproject.R;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import es.dmoral.toasty.Toasty;


public class AppContext extends LitePalApplication {
    private static AppContext app;
    private static Context context;
    public AppContext() {
        app = this;
    }

    public static synchronized AppContext getInstance() {
        if (app == null) {
            app = new AppContext();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Connector.getDatabase();
        context = getApplicationContext();
        Toasty.Config.getInstance().setInfoColor(ContextCompat.getColor(this, R.color.toasty_info));
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getContext() {
        return context;
    }
}