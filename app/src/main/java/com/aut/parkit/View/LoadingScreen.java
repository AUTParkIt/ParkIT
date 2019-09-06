package com.aut.parkit.View;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aut.parkit.R;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        startActivity(new Intent(LoadingScreen.this, SignupScreen.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(LoadingScreen.this, SignupScreen.class));
    }
}