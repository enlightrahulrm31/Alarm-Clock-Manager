package com.example.alarmmanager

import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmmanager.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_destination.*

class DestinationActivity : AppCompatActivity() {
    private  lateinit var binding: AlarmReciever
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)
        buttonstop.setOnClickListener {
              binding.r.stop()
        }
    }
}