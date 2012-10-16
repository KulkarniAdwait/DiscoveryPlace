package com.wglxy.example.dash1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class QuizActivity extends DashboardActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitleFromActivityLabel(R.id.title_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }

    public void onClickNext(View v) {
        startActivity(new Intent(getApplicationContext(), Quiz2Activity.class));
    }
}
