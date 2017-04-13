package com.tk.viewkiller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void partical_1(View v) {
        show(0);
    }

    public void partical_2(View v) {
        show(1);
    }

    private void show(int flag) {
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("flag", flag);
        startActivity(intent);
    }
}
