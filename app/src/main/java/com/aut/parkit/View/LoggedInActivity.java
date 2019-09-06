package com.aut.parkit.View;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.aut.parkit.Model.*;
import com.aut.parkit.R;

public class LoggedInActivity extends AppCompatActivity {

    private TextView logInView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        user = new User();

        logInView = findViewById(R.id.textView_loginActivity);

        logInView.setText("Welcome" + user.getFirstName() + " " + user.getLastName());
    }
}
