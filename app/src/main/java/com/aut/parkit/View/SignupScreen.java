package com.aut.parkit.View;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aut.parkit.R;

public class SignupScreen extends AppCompatActivity implements View.OnClickListener {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    CheckBox termsConditions;
    CheckBox loggedIn;
    Button signUp;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        termsConditions = findViewById(R.id.termsConditions);
        loggedIn = findViewById(R.id.loggedIn);
        signUp = findViewById(R.id.signUp);
        goBack = findViewById(R.id.goBack);

        TextView screenTitle = findViewById(R.id.screenTitle);
        String text = "CREATE AUT PARKIT ACCOUNT";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);
        ss.setSpan(fcsBlack, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        screenTitle.setText(ss);

        CheckBox termsConditions = findViewById(R.id.termsConditions);
        String clickableText = "I agree with the Terms & Conditions";
        SpannableString ssClickable = new SpannableString(clickableText);
        ClickableSpan clickableSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {   //toast is a short popup message that pops up near the bottom of the screen
                Toast.makeText(SignupScreen.this, "These are the Terms & Conditions", Toast.LENGTH_SHORT).show();

                //this stops the checkbox being checked if clicking on T&C's
                widget.cancelPendingInputEvents();
            }
        };
        ssClickable.setSpan(clickableSpan, 17, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsConditions.setText(ssClickable);
        termsConditions.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public void onClick(View view) {

        Toast.makeText(SignupScreen.this, "Go Back to Login/SignUp Screen", Toast.LENGTH_SHORT).show();
    }
}
