package com.ckh.cutetimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView
import com.ckh.cutetimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  val remainMinutes:TextView by lazy{
        findViewById(R.id.remainMinutesTextView)
    }
    private  val remainSeconds:TextView by lazy{
        findViewById(R.id.remainSecondTextView)
    }
    private  val seekBar:SeekBar by lazy{
        findViewById(R.id.seekBar)
    }
    private var currentCountDownTimer: CountDownTimer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        bindView()
    }
    private fun bindView(){
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if(p2){
                        updateRemainTime(p1 * 60 *1000L)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    currentCountDownTimer?.cancel()
                    currentCountDownTimer = null
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    p0?: return
                    currentCountDownTimer = createCountDown(p0.progress*60 *1000L)
                    currentCountDownTimer?.start()
                }
            }
        )
    }
    private fun createCountDown(initialMills:Long):CountDownTimer{
        return  object: CountDownTimer(initialMills, 1000L){
            override fun onTick(p0: Long) {
                updateRemainTime(p0)
            }

            override fun onFinish() {

            }
        }
    }
    private fun updateRemainTime(remainMills:Long) {
        val remainSecond = remainMills / 1000
        remainMinutes.text = "%02d'".format(remainSecond/60)
        remainSeconds.text = "%02d".format(remainSecond % 60)
    }
    private fun updateSeekbar(remainMills: Long){
        seekBar.progress = (remainMills/1000/60).toInt()
    }

}