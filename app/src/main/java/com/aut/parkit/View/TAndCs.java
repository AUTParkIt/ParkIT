package com.aut.parkit.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.aut.parkit.R;

public class TAndCs extends SignupScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_tand_cs);

        TextView popupClose;
        myDialog.setContentView(R.layout.custompopup);
        popupClose = (TextView) myDialog.findViewById(R.id.popupClose);

        popupClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                myDialog.dismiss();
                finish();
            }
        });

        myDialog.show();
    }
}
