package com.aut.parkit.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aut.parkit.R

class LoadingScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val intent = Intent(this, AccountCreationActivityTest::class.java)
// start your next activity
        startActivity(intent)
    }
}
