package sber.practice.serzhan.servicepractice2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyService extends Service {

    private List<MyListener> listeners = new ArrayList<>();
    private IBinder mBinder = new LocalBinder();
    private Random value = new Random();

    @Override
    public void onCreate() {
        sendValue();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

//    public void sendUpdate() {
//        new Thread(() -> {
//            while(true) {
//                for( MyListener listener : listeners) {
//                    listener.update(getValue());
//                }
//            }
//        }).start();
//    }

    public void registerListener(MyListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(MyListener listener) {
        listeners.remove(listener);
    }

    public void sendValue() {
        new Thread(() -> {
            while(true) {
                for (MyListener myListener : listeners) {
                    Bundle b = new Bundle();
                    b.putInt("somevalue", value.nextInt(100));
                    myListener.update(b);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, MyService.class);
    }

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
