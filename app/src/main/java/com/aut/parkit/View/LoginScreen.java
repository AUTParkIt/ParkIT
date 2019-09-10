package com.aut.parkit.View;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.graphics.Color;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aut.parkit.R;

public class LoginScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        TextView textView2 = findViewById(R.id.textView2);

        String text = "AUT PARKIT";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);
        ss.setSpan(fcsBlack, 8,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(ss);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mailbox, menu);
        return super.onCreateOptionsMenu(menu);
    }

}