package com.aut.parkit.SignUpScreenTests;


import android.app.Dialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.R;
import com.aut.parkit.View.SignupScreen;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SignUpScreenUnitTest  extends AppCompatActivity {

    FirebaseAuth mAuth;
    AppCompatActivity signUpScreen;

    EditText email, fName, lName, vReg, pasWrd, conWord;
    CheckBox terCon;
    Button signUp, goBack;
    Dialog myDialog;

    @Before
    public void setUp() {
        mAuth = FirebaseAuth.getInstance();
        signUpScreen = new SignupScreen();

        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        vReg = findViewById(R.id.vehicleReg);
        pasWrd = findViewById(R.id.password);
        conWord = findViewById(R.id.confirmPassword);
        terCon = findViewById(R.id.termsConditions);
        signUp = findViewById(R.id.signUp);
        goBack = findViewById(R.id.goBack);

        myDialog = new Dialog(this);

    }

    @After
    public void tearDownAfterClass(){
        finish();
    }

    @Test
    public void SignUp(){
        assertEquals(null,mAuth.getUid());

        fName.setText("Unit");
        lName.setText("Test");

        email.setText("unitTest@Unit.test");
        vReg.setText("tes123");

        pasWrd.setText("123456");
        conWord.setText("123456");

        terCon.callOnClick();
        myDialog.dismiss();

    }

}
