package com.aut.parkit.View;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aut.parkit.Model.AccountManager;
import com.aut.parkit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountCreationActivityTest extends AppCompatActivity {
    private Button signUp;
    EditText fName, lName, eAddress, pWord, cPWork, lp;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation_test);
        signUp = findViewById(R.id.button);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        eAddress = findViewById(R.id.eAddress);
        pWord = findViewById(R.id.pWord);
        cPWork = findViewById(R.id.cPWord);
        lp = findViewById(R.id.LP);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name, lasname, emaladd, passwrd, conpwrd, licenplat;

                name = fName.getText().toString();
                lasname = lName.getText().toString();
                emaladd = eAddress.getText().toString();
                passwrd = pWord.getText().toString();
                licenplat = lp.getText().toString();

                mAuth.createUserWithEmailAndPassword(emaladd, passwrd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        AccountManager.createUser(name, lasname, emaladd, licenplat);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AccountCreationActivityTest.this, e.toString(), Toast.LENGTH_SHORT);
                    }
                });

            }
        });
    }
}
