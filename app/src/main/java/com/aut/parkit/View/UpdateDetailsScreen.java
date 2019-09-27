package com.aut.parkit.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.aut.parkit.R;

public class UpdateDetailsScreen extends AppCompatActivity
{
    EditText currentPassword, newPassword, confirmNewPassword;
    Button cancel, update_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details_screen);

        setupUI();
    }

    public void setupUI()
    {
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);
        cancel = findViewById(R.id.cancel);
        update_password = findViewById(R.id.updatePassword);

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String currentPasswrd = currentPassword.getText().toString().trim();
                final String newPasswrd = newPassword.getText().toString().trim();
                final String cNewPasswrd = confirmNewPassword.getText().toString().trim();

                if (currentPasswrd.isEmpty())
                {
                    currentPassword.setError("Password Required");
                    currentPassword.requestFocusFromTouch();
                    return;
                }

                if (currentPasswrd.length() < 6)
                {
                    currentPassword.setError("Password Requires ^ Characters");
                    currentPassword.requestFocusFromTouch();
                    return;
                }

                if (newPasswrd.isEmpty())
                {
                    newPassword.setError("New Password Required");
                    newPassword.requestFocusFromTouch();
                    return;
                }

                if (newPasswrd.length() < 6)
                {
                    newPassword.setError("New Password Requires ^ Characters");
                    newPassword.requestFocusFromTouch();
                    return;
                }

                if (cNewPasswrd.isEmpty())
                {
                    confirmNewPassword.setError("Confirmation Password Required");
                    confirmNewPassword.requestFocusFromTouch();
                    return;
                }

                if (!newPasswrd.contentEquals(cNewPasswrd))
                {
                    confirmNewPassword.setError("Passwords do not Match");
                    confirmNewPassword.requestFocusFromTouch();
                    return;
                }

                if (!cancel.isPressed())
                {
                    Toast.makeText(getApplicationContext(), "This Feature is Not Yet Functional", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!update_password.isPressed())
                {
                    Toast.makeText(getApplicationContext(), "This Feature is Not Yet Functional", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
}
