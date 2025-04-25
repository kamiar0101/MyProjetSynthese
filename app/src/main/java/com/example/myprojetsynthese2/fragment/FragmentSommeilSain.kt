/*
package com.example.myprojetsynthese2.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myprojetsynthese2.R
import java.util.*

class FragmentSommeilSain : Fragment() {

    //private lateinit var etAgeSommeil: EditText
    //private lateinit var tpSommeil: TimePicker
    //private lateinit var btnCalculate: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sommeil_sain, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisation correcte
        val etAgeSommeil: EditText by lazy { view.findViewById(R.id.etAgeSommeil_fragment) }
        val tpSommeil: TimePicker by lazy { view.findViewById(R.id.tPSommeil_fragment) }
        val btnCalculate: Button by lazy { view.findViewById(R.id.btnCalculateSommeil_fragment) }
        val tvDureeSommeilRecommende_Fragment: TextView by lazy { view.findViewById(R.id.tvDureeSommeilRecommende_Fragment) }
        val tvHeureCoucherConsecutive_Fragment: TextView by lazy { view.findViewById(R.id.tvHeureCoucherConsecutive_Fragment) }

        tpSommeil.setIs24HourView(true) // Important si tu veux l'affichage 24h


        @SuppressLint("DefaultLocale")
        fun calculateSleepTime() {
            val ageStr = etAgeSommeil.text.toString()
            if (ageStr.isEmpty()) {
                Toast.makeText(requireContext(), "الرجاء إدخال العمر", Toast.LENGTH_SHORT).show()
                return
            }

            val age = ageStr.toInt()
            val wakeHour = tpSommeil.hour
            val wakeMinute = tpSommeil.minute

            val sleepHours = when {
                age < 5 -> 11
                age < 13 -> 10
                age < 18 -> 9
                age < 65 -> 8
                else -> 7
            }

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, wakeHour)
            calendar.set(Calendar.MINUTE, wakeMinute)
            calendar.add(Calendar.HOUR_OF_DAY, -sleepHours)

            val sleepTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))

            Toast.makeText(requireContext(), "🛌 يجب أن تنام حوالي الساعة $sleepTime", Toast.LENGTH_LONG).show()

            tvDureeSommeilRecommende_Fragment.text = sleepHours.toString()
            tvHeureCoucherConsecutive_Fragment.text = sleepTime.toString()
        }

        btnCalculate.setOnClickListener {
            calculateSleepTime()
        }
    }

}
*/

package com.example.myprojetsynthese2.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myprojetsynthese2.R
import java.text.SimpleDateFormat
import java.util.*

class FragmentSommeilSain : Fragment() {

    private lateinit var etAgeSommeil: EditText
    private lateinit var tpSommeil: TimePicker
    private lateinit var btnCalculate: Button
    private lateinit var tvDureeSommeilRecommende: TextView
    private lateinit var tvHeureCoucherConsecutive: TextView
    private lateinit var btnCycles: List<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sommeil_sain, container, false)
// jytfytft
    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etAgeSommeil = view.findViewById(R.id.etAgeSommeil_fragment)
        tpSommeil = view.findViewById(R.id.tPSommeil_fragment)
        btnCalculate = view.findViewById(R.id.btnCalculateSommeil_fragment)
        tvDureeSommeilRecommende = view.findViewById(R.id.tvDureeSommeilRecommende_Fragment)
        tvHeureCoucherConsecutive = view.findViewById(R.id.tvHeureCoucherConsecutive_Fragment)

        tpSommeil.setIs24HourView(true)

      //  Utilisateur utilisateur= new  Utilisateur();
       // utilisateur.setAge(etAgeSommeil)

        // saveUtilisateur(utilisateur)

        btnCycles = listOf(
            view.findViewById(R.id.tvCoucher1_fragment),
            view.findViewById(R.id.tvCoucher2_fragment),
            view.findViewById(R.id.tvCoucher3_fragment),
            view.findViewById(R.id.tvCoucher4_fragment),
            view.findViewById(R.id.tvCoucher5_fragment),
            view.findViewById(R.id.tvCoucher6_fragment)
        )

        btnCalculate.setOnClickListener {
            val ageStr = etAgeSommeil.text.toString()
            val age = ageStr.toIntOrNull()
            if (age == null) {
                Toast.makeText(requireContext(), "الرجاء إدخال عمر صحيح", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val wakeHour = tpSommeil.hour
            val wakeMinute = tpSommeil.minute

            val sleepHours = when {
                age < 5 -> 11
                age < 13 -> 10
                age < 18 -> 9
                age < 65 -> 8
                else -> 7
            }

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, wakeHour)
            calendar.set(Calendar.MINUTE, wakeMinute)
            calendar.add(Calendar.HOUR_OF_DAY, -sleepHours)

            val sleepTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))

            tvDureeSommeilRecommende.text = "$sleepHours ساعات"
            tvHeureCoucherConsecutive.text = sleepTime

            Toast.makeText(requireContext(), "🛌 يجب أن تنام حوالي الساعة $sleepTime", Toast.LENGTH_LONG).show()

            calculerCyclesSommeil(wakeHour, wakeMinute)
        }
    }

    private fun calculerCyclesSommeil(wakeHour: Int, wakeMinute: Int) {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())

        for (i in btnCycles.indices) {
            val cycleCount = 6 - i
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, wakeHour)
            calendar.set(Calendar.MINUTE, wakeMinute)
            calendar.add(Calendar.MINUTE, -(cycleCount * 90)) // 90 minutes par cycle

            val timeStr = format.format(calendar.time)
            btnCycles[i].text = "$cycleCount دورات\n$timeStr"
        }
    }
}


