package com.example.myprojetsynthese2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myprojetsynthese2.fragment.FragmentIndiceDeMasseCorporelle
import com.example.myprojetsynthese2.fragment.FragmentPoidsIdeal
import com.example.myprojetsynthese2.fragment.FragmentPoucentageDeMatieresGrasseas
import com.example.myprojetsynthese2.fragment.FragmentSommeilSain

class MainActivity : AppCompatActivity() {

    // recuperer les id des boutton
    val btnSommeil: Button by lazy { findViewById(R.id.btnSommeil) }
    val btnPoucentageMatieresGrasses: Button by lazy { findViewById(R.id.btnPoucentageMatieresGrasses) }
    val PoidsIdeal: Button by lazy { findViewById(R.id.btnPoidsIdeal) }
    val btnPointCorporelle: Button by lazy { findViewById(R.id.btnPointCorporelle) }
    val fragment: FrameLayout by lazy { findViewById(R.id.fl) }

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // btnSommeil
        btnSommeil.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl, FragmentSommeilSain() )
                    .commit()
            }
        }

        // btnPoucentageMatieresGrasses
           btnPoucentageMatieresGrasses.setOnClickListener {
               supportFragmentManager.beginTransaction(). apply {
                   replace(R.id.fl, FragmentPoucentageDeMatieresGrasseas())
                       .commit()
               }
           }

        // btnPoucentageMatieresGrasses
        PoidsIdeal.setOnClickListener {
            supportFragmentManager.beginTransaction(). apply {
                replace(R.id.fl, FragmentPoidsIdeal())
                    .commit()
            }
        }

        // btnPoucentageMatieresGrasses
        btnPointCorporelle.setOnClickListener {
            supportFragmentManager.beginTransaction(). apply {
                replace(R.id.fl, FragmentIndiceDeMasseCorporelle())
                    .commit()
            }
        }

    }


}