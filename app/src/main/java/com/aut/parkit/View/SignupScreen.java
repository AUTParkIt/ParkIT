package com.aut.parkit.View;

import android.app.Dialog;
import android.content.Intent;
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

public class SignupScreen extends AppCompatActivity
{
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText vehReg;
    EditText password;
    EditText confirmPassword;
    CheckBox termsConditions;
    CheckBox loggedIn;
    Button signUp;
    Button goBack;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        setupUIViews();
        spanScreenTitle();
        clickCheckBox();
        validate();

        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(validate())
                {
                    //upload details to the database
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SignupScreen.this, LoginTestActivity.class));
            }
        });
    }

    public void setupUIViews()
    {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        vehReg = findViewById(R.id.vehicleReg);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        termsConditions = findViewById(R.id.termsConditions);
        loggedIn = findViewById(R.id.loggedIn);
        signUp = findViewById(R.id.signUp);
        goBack = findViewById(R.id.goBack);
        myDialog = new Dialog(this);
    }

    public Boolean validate()
    {
        Boolean result = false;
        String name1 = firstName.getText().toString();
        String name2 = lastName.getText().toString();
        String mail = email.getText().toString();
        String car = vehReg.getText().toString();
        String pword1 = password.getText().toString();
        String pword2 = confirmPassword.getText().toString();

        if((name1.isEmpty()) || (name2.isEmpty()) || (mail.isEmpty())
            || (car.isEmpty())  || (pword1.isEmpty()) || (pword2.isEmpty()))
        {
            Toast.makeText(SignupScreen.this, "You must complete all fields", Toast.LENGTH_SHORT).show();
        }
        else return true;

        return false;
    }

    public void spanScreenTitle()
    {
        TextView screenTitle = findViewById(R.id.screenTitle);
        String text = "CREATE AUT PARKIT ACCOUNT";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);
        ss.setSpan(fcsBlack, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        screenTitle.setText(ss);
    }

    public void clickCheckBox()
    {
        CheckBox termsConditions = findViewById(R.id.termsConditions);
        String clickableText = "I agree with the Terms & Conditions";
        SpannableString ssClickable = new SpannableString(clickableText);
        ClickableSpan clickableSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View view)
            {
                ShowPopUp(view);
            }
        };
        ssClickable.setSpan(clickableSpan, 17, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsConditions.setText(ssClickable);
        termsConditions.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void onClick(View view)
    {
        Toast.makeText(SignupScreen.this, "Go Back to Login/SignUp Screen", Toast.LENGTH_SHORT).show();
    }

    public void ShowPopUp(View view)
    {
        TextView popupClose;
        myDialog.setContentView(R.layout.custompopup);
        popupClose = (TextView) myDialog.findViewById(R.id.popupClose);

        popupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }

}
