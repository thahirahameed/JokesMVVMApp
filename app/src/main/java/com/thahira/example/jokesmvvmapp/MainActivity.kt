package com.thahira.example.jokesmvvmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thahira.example.jokesmvvmapp.view.FirstFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container,FirstFragment.newInstance())
            .commit()
    }


}