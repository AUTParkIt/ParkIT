package com.aut.parkit.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aut.parkit.R
import com.google.firebase.auth.FirebaseAuth

class LoadingScreen : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        val intent:Intent

        mAuth.signOut()

        if (mAuth.uid == null){
            intent = Intent(this, LoginTestActivity::class.java)
        }
        else {
            intent = Intent(this, HomeScreen::class.java)
        }

// start your next activity
        startActivity(intent)
    }
}
