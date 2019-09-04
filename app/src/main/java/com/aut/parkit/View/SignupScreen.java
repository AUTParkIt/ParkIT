package com.aut.parkit.View;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aut.parkit.R;

public class SignupScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        TextView textView = findViewById(R.id.text_View2);
        String text = "CREATE AUT PARKIT ACCOUNT";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);
        ss.setSpan(fcsBlack, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);

        CheckBox checkBox = findViewById(R.id.check_Box);
        String clickableText = "I agree with the Terms & Conditions";
        SpannableString ssClickable = new SpannableString(clickableText);
        ClickableSpan clickableSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {   //toast is a short popup message that pops up near the bottom of the screen
                Toast.makeText(SignupScreen.this, "These are the Terms & Conditions", Toast.LENGTH_SHORT).show();

                //this cancels the checkbox being checked if clicking on T&C's
                widget.cancelPendingInputEvents();
            }
        };
        ssClickable.setSpan(clickableSpan, 17, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox.setText(ssClickable);
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
