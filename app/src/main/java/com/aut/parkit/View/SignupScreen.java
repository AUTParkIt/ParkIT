package com.aut.parkit.View;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aut.parkit.R;

public class SignupScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        TextView textView = findViewById(R.id.textView2);

        String text = "CREATE AUT PARKIT ACCOUNT";

        SpannableString ss = new SpannableString(text);

        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);

        ss.setSpan(fcsBlack, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
    }
}
