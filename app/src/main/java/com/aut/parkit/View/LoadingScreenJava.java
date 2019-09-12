package com.aut.parkit.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.R;

public class LoadingScreenJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        startActivity(new Intent(LoadingScreenJava.this, SignupScreen.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(LoadingScreenJava.this, SignupScreen.class));
    }
}