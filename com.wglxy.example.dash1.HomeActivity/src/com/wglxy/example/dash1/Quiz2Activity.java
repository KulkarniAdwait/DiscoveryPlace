package com.wglxy.example.dash1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Quiz2Activity extends DashboardActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz2, menu);
        return true;
    }

    public void onClickNext(View v) {
        startActivity(new Intent(getApplicationContext(), Quiz3Activity.class));
    }

    public void onClickPre(View v) {
        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
    }
}
