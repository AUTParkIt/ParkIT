package com.aut.parkit.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        emailText = findViewById(R.id.email_LoginTest);
        passwordText = findViewById(R.id.password_LoginTest);

        signUpButton = findViewById(R.id.signUp_LoginTest);
        loginButton = findViewById(R.id.login_LoginTest);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AccountCreationActivityTest.class));
            }
        });
    }
}
