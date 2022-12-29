package com.example.numbercounterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import com.example.numbercounterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    // 계수기앱 구현

    var number = 0
    lateinit var numberTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        numberTextView = findViewById<TextView>(R.id.number_txt)

        // number 변수에 0으로 초기화한 값이 count버튼을 누르면 1씩 증가, 텍스트로 표시되도록 세팅
        binding.countBtn.setOnClickListener {
            number++

            numberTextView.text = number.toString()

            // 99까지 계수가되면, 그다음 카운트는 다시 1부터 시작하도록 세팅
            if (number == 10) {
                number = 0
            }
        }

        // 숫자가 증가된 상태이므로, 변수에서 초기화한 0값이 텍스트로 표시되도록 세팅
        binding.intializeBtn.setOnClickListener {
            number = 0
            numberTextView.text = number.toString()
        }
    }


    override fun onSaveInstanceState(outState: Bundle, ) {
        super.onSaveInstanceState(outState)

        outState.putInt("increasedNumber",number)
    }

    //  문제가 발생했던 콜백함수
    //    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    //      super.onSaveInstanceState(outState, outPersistentState)

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        number = savedInstanceState.getInt("increasedNumber")
        numberTextView.setText("$number")
    }
}

/*
새로 배운 것! - 화면전환같이 액티비티가 onDestroy 되었다가 다시 onCreate 될 때, 데이터를 저장 -> 복원하는 방법을 배웠다.

문제 아카이브 - 문제는 위 과정에서 IDE가 제공하는 2개의 onSaveInstanceState 콜백함수 중에,
              outPersistentState: PersistableBundle 라는 파라미터가 달려있는 함수를 호출하였더니 데이터가 계속해서 복원되지 않았다.

해결 방법    -  찾아보니 두번째 파라미터는 PersistableMode로 명시적으로 쓰인 activity에서만 호출이 된다는 것을 알게 되었다.
               결과적으로 outState 파라미터만 달려있는 콜백함수로 다시 호출하여 실행했더니 잘 구동되었다!
 */