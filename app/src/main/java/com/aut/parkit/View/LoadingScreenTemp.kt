package com.aut.parkit.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aut.parkit.R
import com.google.firebase.auth.FirebaseAuth

class LoadingScreenTemp : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        val intent:Intent

        if (mAuth.uid == null){
            intent = Intent(this, SignupScreen::class.java)
        }
        else {
            intent = Intent(this, LoggedInActivity::class.java)
        }

// start your next activity
        startActivity(intent)
    }
}
