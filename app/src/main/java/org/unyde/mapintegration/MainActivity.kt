package org.unyde.mapintegration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import org.unyde.mapintegrationlib.MapIntgrationMain

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // MapIntgrationMain.s(this,"101")
    }
}
