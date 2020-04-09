package edu.washington.haoyac2.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var randomPlayNumber = Random.nextInt(1000, 50000)
    private var showApplyBtn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val songNumber = findViewById<TextView>(R.id.songNumber)
        songNumber.text = "$randomPlayNumber plays"
        Log.i("songNumber", "started")
        previousBtn.setOnClickListener{
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
        nextBtn.setOnClickListener{
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
        songImage.setOnLongClickListener{
            userName.setTextColor(getMyColor(R.color.lightBlue))
            songTitle.setTextColor(getMyColor(R.color.lightBlue))
            artist.setTextColor(getMyColor(R.color.lightBlue))
            songNumber.setTextColor(getMyColor(R.color.lightBlue))
             true
        }

        musicPlayBtn.setOnClickListener{
            Log.i("eric2", "clicked")
            randomPlayNumber += 1
            songNumber.text = "$randomPlayNumber plays"
        }

        btnChangeUser.setOnClickListener{
            if (!showApplyBtn) {
                Log.i("eric3", "show")
                btnChangeUser.text = "Apply"
                showApplyBtn = true
                userNameInput.setText(userName.text)
                userName.visibility = View.GONE
                userNameInput.visibility = View.VISIBLE
            } else {
                if (userNameInput.text.toString().isNotEmpty()) {
                    btnChangeUser.text = "CHANGE USER"
                    showApplyBtn = false
                    userName.setText(userNameInput.text)
                    userNameInput.visibility = View.GONE
                    userName.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "Username may not be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getMyColor(resID: Int): Int {
        return ContextCompat.getColor(this, resID)
    }
}
