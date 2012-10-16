package com.wglxy.example.dash1;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class QuizResultActivity extends DashboardActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz_result, menu);
        return true;
    }

    public void onClickPre(View v) {
        startActivity(new Intent(getApplicationContext(), Quiz3Activity.class));
    }
}
