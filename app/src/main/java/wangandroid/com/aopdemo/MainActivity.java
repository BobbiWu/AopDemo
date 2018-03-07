package wangandroid.com.aopdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = findViewById(R.id.bt);
    }

    @CheckNet
    public void aopTest(View view) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }
}
