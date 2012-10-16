package com.wglxy.example.dash1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Quiz3Activity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz3, menu);
        return true;
    }

    public void onClickNext(View v) {
        startActivity(new Intent(getApplicationContext(), QuizResultActivity.class));
    }

    public void onClickPre(View v) {
        startActivity(new Intent(getApplicationContext(), Quiz2Activity.class));
    }
}
