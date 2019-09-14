package com.aut.parkit.View;
/*
 * 6 EditText for user input
 * validated for no user input if 1 EditText field not completed
 * T&C's - clicking on underlined TC will auto check the box
 * Signup Button to be
 * Check if GoBack button returns to the login screen
 * UNIT TESTS!!!!!
*/

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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.aut.parkit.Model.DatabaseManagmentSystem.AccountManager;
import com.aut.parkit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupScreen extends AppCompatActivity
{
    EditText firstName,lastName, email,vehReg,password, confirmPassword;
    CheckBox termsConditions;
    Button signUp,goBack;
    Dialog myDialog;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String name1, name2, mail, car, pword1, pword2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        setupUI();
        spanScreenTitle();
        //validate();
        clickCheckBox();


    }

    public void setupUI()
    {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        vehReg = findViewById(R.id.vehicleReg);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        termsConditions = findViewById(R.id.termsConditions);
        signUp = findViewById(R.id.signUp);
        goBack = findViewById(R.id.goBack);
        myDialog = new Dialog(this);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String name, lasname, emaladd, passwrd, conpwrd, licenplat;
//firstName,lastName, email,vehReg,password, confirmPassword;
                final String name = firstName.getText().toString();
                final String lasname = lastName.getText().toString();
                final String emaladd = email.getText().toString();
                final String passwrd = password.getText().toString();
                final String licenplat = vehReg.getText().toString();
                final String cPasswrd = confirmPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(emaladd, passwrd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        AccountManager.createUser(name, lasname, emaladd, licenplat);
                        startActivity(new Intent(SignupScreen.this, LoggedInTestActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

            }
        });
    }

    private Boolean validate()                                              //completed working correctly
    {                                                                       //TODO: pad the user input to start typing where hint starts and
        Boolean result = false;                                             //verify correct email entry only??
        name1 = firstName.getText().toString();                             //does confirm password require validation??
        name2 = lastName.getText().toString();
        mail = email.getText().toString();
        car = vehReg.getText().toString();
        pword1 = password.getText().toString();
        pword2 = confirmPassword.getText().toString();

        if((name1.isEmpty()) || (name2.isEmpty()) || (mail.isEmpty())
            || (car.isEmpty())  || (pword1.isEmpty()) || (pword2.isEmpty()))
        {
            Toast.makeText(SignupScreen.this, "You must complete all fields", Toast.LENGTH_SHORT).show();
        }
        else return true;

        return false;
    }

    /*public void clickSignUpButton(View view)                                //when user clicks the signup button then data should be sent to the database
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               sendUserData();
            }
        });
    }*/

    /*public void sendUserData()                                             //check with above method
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        SignupUserProfile signupUserProfile = new SignupUserProfile(name1, name2, mail, car, pword1, pword2);
        myRef.setValue(signupUserProfile);
    }*/

    public void spanScreenTitle()                                           //completed working correctly
    {                                                                       //only showing on emulator not on design view
        TextView screenTitle = findViewById(R.id.screenTitle);
        String text = "CREATE AUT PARKIT ACCOUNT";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);
        ss.setSpan(fcsBlack, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        screenTitle.setText(ss);
    }

    public void clickCheckBox()                                             //completed working correctly
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

    public void ShowPopUp(View view)                                        //completed working correctly
    {
        TextView popupClose;
        myDialog.setContentView(R.layout.custompopup);
        popupClose = (TextView) myDialog.findViewById(R.id.popupClose);

        popupClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                myDialog.dismiss();
            }
        });

        myDialog.show();

    }

/*    public void onClick(View view)                                          //completed working correctly
    {
        Toast.makeText(SignupScreen.this, "Go Back to Login/SignUp Screen", Toast.LENGTH_SHORT).show();
    }*/

    /*public void returnToLogInScreen(View view)                          //check if this changes to LoginScreen
    {
        goBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SignupScreen.this, LoginScreen.class));

            }

        });
        //finish();
    }*/

}
