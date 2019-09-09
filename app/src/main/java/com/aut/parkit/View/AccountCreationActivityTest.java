package com.aut.parkit.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.aut.parkit.Model.DatabaseManagmentSystem.AccountManager;
import com.aut.parkit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AccountCreationActivityTest extends AppCompatActivity {
    private Button signUp, login;
    EditText fName, lName, eAddress, pWord, cPWork, lp;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation_test);
        signUp = findViewById(R.id.button);
        login = findViewById(R.id.loginBtn_AccCreaa);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        eAddress = findViewById(R.id.eAddress);
        pWord = findViewById(R.id.pWord);
        cPWork = findViewById(R.id.cPWord);
        lp = findViewById(R.id.LP);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String name, lasname, emaladd, passwrd, conpwrd, licenplat;

                final String name = fName.getText().toString();
                final String lasname = lName.getText().toString();
                final String emaladd = eAddress.getText().toString();
                final String passwrd = pWord.getText().toString();
                final String licenplat = lp.getText().toString();

                Toast toast = Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT);
                toast.show();

                mAuth.createUserWithEmailAndPassword(emaladd, passwrd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        AccountManager.createUser(name, lasname, emaladd, licenplat);
                        startActivity(new Intent(AccountCreationActivityTest.this, LoggedInTestActivity.class));
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountCreationActivityTest.this, LoginTestActivity.class));
            }
        });
    }
}
