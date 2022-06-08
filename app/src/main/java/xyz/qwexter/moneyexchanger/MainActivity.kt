package xyz.qwexter.moneyexchanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.qwexter.moneyexchanger.exchangerx.ui.ExchangeFragment

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportFragmentManager.findFragmentById(R.id.main_activity_fragment_container) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity_fragment_container, ExchangeFragment::class.java, null)
                .commit()
        }
    }
}