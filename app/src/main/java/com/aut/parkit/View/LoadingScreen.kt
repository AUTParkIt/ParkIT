package com.aut.parkit.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aut.parkit.Model.DatabaseManagmentSystem.User
import com.aut.parkit.R
import com.google.firebase.auth.FirebaseAuth

class LoadingScreen : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)




        if (mAuth.uid == null){
            startActivity(Intent(this, LoginTestActivity::class.java))
        }
        else {
            Thread(Runnable {
                val user = User()
                val intent:Intent

                if (user.currentParkingSession != null){
                    intent = Intent(this, RemainingTimeScreen::class.java)
                }
                else {
                    intent = Intent(this, HomeScreen::class.java)
                }

                runOnUiThread(Runnable {
                    startActivity(intent)
                })
            }).start()

        }

// start your next activity

    }
}
