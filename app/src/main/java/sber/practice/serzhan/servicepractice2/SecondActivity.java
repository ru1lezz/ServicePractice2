package sber.practice.serzhan.servicepractice2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements MyListener{

    private Button stopServiceButton;
    private TextView textView;
    private MyService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViews();
        initListeners();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("SecondActivity", "Connecting");
            mService = ((MyService.LocalBinder)service).getService();
            mService.registerListener(SecondActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("SecondActivity", "MyService is null");
            mService = null;
        }
    };

    private void init() {
        bindService();
    }
    public void bindService() {
        bindService(MyService.newIntent(SecondActivity.this), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService() {
        mService.unregisterListener(SecondActivity.this);
        unbindService(serviceConnection);
    }

    private void initViews() {
        stopServiceButton = findViewById(R.id.button_stopService);
        textView = findViewById(R.id.textView2);
    }

    private void initListeners() {
        stopServiceButton.setOnClickListener((View v) -> {
            unbindService();
            stopService(MyService.newIntent(SecondActivity.this));
        });
    }


    @Override
    public void update(final Bundle value) {
        this.runOnUiThread(() -> {
            textView.setText(String.valueOf(value.getInt("somevalue", 1)));
        });
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, SecondActivity.class);
    }
}
