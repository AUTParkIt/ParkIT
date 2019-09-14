package com.aut.parkit.View;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aut.parkit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity
{
    Button login;
    Button signUp;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

    }

    public void spanScreenTitle()
    {
        TextView textView2 = findViewById(R.id.textView2);
        String text = "AUT PARKIT";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);
        ss.setSpan(fcsBlack, 8,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(ss);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mailbox, menu);
        return super.onCreateOptionsMenu(menu);
    }

}