package com.aut.parkit.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.aut.parkit.R;

public class LoginTestActivity extends AppCompatActivity {

    private Button loginButton, signUpButton;
    private EditText emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
    }
}
