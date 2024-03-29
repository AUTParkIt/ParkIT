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
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.AccountManager;
import com.aut.parkit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupScreen extends AppCompatActivity
{
    EditText firstName,lastName, email,vehReg,password, confirmPassword;
    CheckBox termsConditions;
    Button signUp,goBack;
    static Dialog myDialog;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //String name1, name2, mail, car, pword1, pword2;

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
                final String name = firstName.getText().toString().trim();
                final String lasname = lastName.getText().toString().trim();
                final String emaladd = email.getText().toString().trim();
                final String passwrd = password.getText().toString().trim();
                final String licenplat = vehReg.getText().toString().toUpperCase().trim();
                final String cPasswrd = confirmPassword.getText().toString().trim();

                if (name.isEmpty()){
                    firstName.setError("First Name Required");
                    firstName.requestFocusFromTouch();
                    return;
                }

                if (lasname.isEmpty()){
                    lastName.setError("Last Name Required");
                    lastName.requestFocusFromTouch();
                    return;
                }

                if (emaladd.isEmpty()){
                    email.setError("Email Required");
                    email.requestFocusFromTouch();
                    return;
                }

                if (passwrd.isEmpty()){
                    password.setError("Password Required");
                    password.requestFocusFromTouch();
                    return;
                }

                if (passwrd.length() < 6){
                    password.setError("Password Requires 6 Characters");
                    password.requestFocusFromTouch();
                    return;
                }

                if (licenplat.isEmpty()){
                    vehReg.setError("Licence Plate Required");
                    vehReg.requestFocusFromTouch();
                    return;
                }

                if (cPasswrd.isEmpty()){
                    confirmPassword.setError("Confirmation Password Required");
                    confirmPassword.requestFocusFromTouch();
                    return;
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(emaladd).matches()){
                    email.setError("Email is invalid");
                    email.requestFocusFromTouch();
                    return;
                }

                if (!passwrd.contentEquals(cPasswrd)){
                    confirmPassword.setError("Passwords do not Match");
                    confirmPassword.requestFocusFromTouch();
                    return;
                }

                if (!termsConditions.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please Accept the terms an conditions", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(emaladd, passwrd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        AccountManager.createUser(name, lasname, emaladd, licenplat);
                        startActivity(new Intent(SignupScreen.this, HomeScreen.class));
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

//    private Boolean validate()                                              //completed working correctly
//    {                                                                       //TODO: pad the user input to start typing where hint starts and
//        Boolean result = false;                                             //verify correct email entry only??
//        name1 = firstName.getText().toString();                             //does confirm password require validation??
//        name2 = lastName.getText().toString();
//        mail = email.getText().toString();
//        car = vehReg.getText().toString();
//        pword1 = password.getText().toString();
//        pword2 = confirmPassword.getText().toString();
//
//        if((name1.isEmpty()) || (name2.isEmpty()) || (mail.isEmpty())
//            || (car.isEmpty())  || (pword1.isEmpty()) || (pword2.isEmpty()))
//        {
//            Toast.makeText(SignupScreen.this, "You must complete all fields", Toast.LENGTH_SHORT).show();
//        }
//        else return true;
//
//        return false;
//    }

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

    public static void ShowPopUp(View view)                                        //completed working correctly
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

}
