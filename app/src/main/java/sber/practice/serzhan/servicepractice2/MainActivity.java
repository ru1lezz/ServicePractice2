package sber.practice.serzhan.servicepractice2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startServiceButton, secondActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
    }

    private void initViews() {
        startServiceButton = findViewById(R.id.button_startService);
        secondActivityButton = findViewById(R.id.button_secondActivity);
    }

    private void initListeners() {
        startServiceButton.setOnClickListener((View v) -> {
            startService(MyService.newIntent(MainActivity.this));
        });
        secondActivityButton.setOnClickListener((View v) -> {
            startActivity(SecondActivity.newIntent(MainActivity.this));
        });
    }

}
