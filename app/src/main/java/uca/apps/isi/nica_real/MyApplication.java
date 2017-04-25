package uca.apps.isi.nica_real;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Edwin-Rosales on 25/4/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
