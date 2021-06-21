package com.example.alarmmanager


import android.app.*
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.alarmmanager.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_main.*
import java.time.MonthDay
import java.time.Year
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class MainActivity : AppCompatActivity()  {
    private  lateinit var  binding :ActivityMainBinding
    private  lateinit var picker :MaterialTimePicker
    private  lateinit var calender : Calendar
    private  lateinit var alarmManager: AlarmManager
    private  lateinit var pendingIntent: PendingIntent
    var curday: Int =1
    var curmonthDay: Int =1
    var curyear: Int =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationchannel()
        binding.selectdate.setOnClickListener {
            showDatePicker()
        }
        binding.selecttime.setOnClickListener {
            showTimePicker()

        }
        binding.setalarm.setOnClickListener {

            setAlarm()

        }
        binding.cancealarm.setOnClickListener {

            cancelAlarm()

        }

    }

    private fun showDatePicker() {
        Toast.makeText(this,"date picker",Toast.LENGTH_LONG).show()
        val cal  =Calendar.getInstance()
        val year =cal.get(Calendar.YEAR)
        val month =cal.get(Calendar.MONTH)
        val date =cal.get(Calendar.DATE)
          val  datepickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
              selectdatetext.text= "Date:" + dayOfMonth + "/" + (month+1) + "/" + year
              curday= dayOfMonth
              curmonthDay = month
              curyear = year

          },year,month,date)
        datepickerDialog.show()

    }

    private fun cancelAlarm() {

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this,AlarmReciever::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        alarmManager.cancel(pendingIntent)

        Toast.makeText(this,"alarm cancelled ",Toast.LENGTH_LONG).show()



    }

    private fun setAlarm() {

       alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this,AlarmReciever::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calender.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent

        )
        Toast.makeText(this,"alarm set successfully",Toast.LENGTH_LONG).show()




    }

    private fun showTimePicker() {
        picker =MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager,"AlarmManager")

        picker.addOnPositiveButtonClickListener {
            if(picker.hour>12){
                binding.selecttimetext.text= String.format("%02d",picker.hour- 12)+ " : " + String.format("%02d",picker.minute) + "PM"

            }
            else{
                binding.selecttimetext.text = String.format("%02d",picker.hour)+ " : " + String.format("%02d",picker.minute) + "AM"

            }
            calender = Calendar.getInstance()
            calender[Calendar.DATE] = curday
            calender[Calendar.MONTH] = curmonthDay
            calender[Calendar.YEAR] = curyear
            calender[Calendar.DATE] = curday
            calender[Calendar.HOUR_OF_DAY] = picker.hour
            calender[Calendar.MINUTE] = picker.minute
            calender[Calendar.SECOND] = 0
            calender[Calendar.MILLISECOND] = 0
        }


    }

    private  fun createNotificationchannel(){

       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           val name:CharSequence = "ALARM CHANNEL"
           val description = "Channel for alarm manager"
           val importance =NotificationManager.IMPORTANCE_HIGH
           val channel =NotificationChannel ("AlarmManager",name ,importance)
           channel.description= description
           val notificationManager = getSystemService(
               NotificationManager::class.java
           )
           notificationManager.createNotificationChannel(channel)
       }
   }

}