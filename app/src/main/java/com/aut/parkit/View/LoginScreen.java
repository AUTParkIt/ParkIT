package com.aut.parkit.View;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.aut.parkit.R;

public class LoginScreen extends AppCompatActivity
{
    TextView forgotpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        forgotpw = findViewById(R.id.textView);
        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast =
                        Toast.makeText(getApplicationContext(),
                        "Password Restoration Link \nhas been sent to your email!",
                        Toast.LENGTH_LONG);
                toast.show();
                //opens Forgot Password PopUp;
            }
        });

        TextView signup = findViewById(R.id.textView2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openSignUp();
                //startActivity(new Intent(LoginScreen.this, SignIn.class));
            }
        });

        String text = "AUT PARKIT";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);
        ss.setSpan(fcsBlack, 8,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup.setText(ss);

    }

  /*  public void openSignUp() {
        Intent intent = new Intent(this, SignUp.class);
        startSignUp(intent);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}