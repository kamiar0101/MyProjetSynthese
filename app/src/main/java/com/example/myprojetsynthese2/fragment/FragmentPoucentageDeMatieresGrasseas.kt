package com.example.myprojetsynthese2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myprojetsynthese2.R
import kotlin.math.log10

class FragmentPoucentageDeMatieresGrasseas : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poucentage_de_matieres_grasseas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupération des vues
        val etAge_fragment = view.findViewById<EditText>(R.id.etAge_fragment)
        val etTaille = view.findViewById<EditText>(R.id.etTaille_fragment)
        val etPoids = view.findViewById<EditText>(R.id.etPoids_fragment)
        val etCou = view.findViewById<EditText>(R.id.etCou_fragment)
        val etCeinture = view.findViewById<EditText>(R.id.etTailleCeinture_fragment)
        val etHanche = view.findViewById<EditText>(R.id.etHanche_fragment)
        val rgGender = view.findViewById<RadioGroup>(R.id.rgGender_fragment)
        val rbHomme = view.findViewById<RadioButton>(R.id.rbMale_fragment)
        val rbFemme = view.findViewById<RadioButton>(R.id.rbFemale_fragment)
        val btnCalculer = view.findViewById<Button>(R.id.btnCalculerGraisse_fragment)
        val pourcentageText = view.findViewById<TextView>(R.id.percentageText)
        val typeText = view.findViewById<TextView>(R.id.typeText)
        val progressBar = view.findViewById<ProgressBar>(R.id.pv_fragment)
        val tvHanche_fragment: TextView = view.findViewById(R.id.tvHanche_fragment)

        // Vérification du sexe au changement
        rgGender.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbMale_fragment) {
                // Cacher le champ de hanche si sexe est homme
                etHanche.visibility = View.GONE
                tvHanche_fragment.visibility = View.GONE
            } else if (checkedId == R.id.rbFemale_fragment) {
                // Afficher le champ de hanche si sexe est femme
                etHanche.visibility = View.VISIBLE
                tvHanche_fragment.visibility = View.VISIBLE
            }
        }

        btnCalculer.setOnClickListener {
            try {
                val age = etAge_fragment.text.toString().toInt()
                val poids = etPoids.text.toString().toDouble()
                val taille = etTaille.text.toString().toDouble()
                val cou = etCou.text.toString().toDouble()
                val ceinture = etCeinture.text.toString().toDouble()

                // إخفاء/إظهار حقل الورك بناءً على الجنس المحدد
                val hanche: Double = if (rgGender.checkedRadioButtonId == R.id.rbFemale_fragment) {
                    etHanche.text.toString().toDoubleOrNull() ?: 90.0 // la valeur par defaut les femmes
                } else {
                    90.0 // la valeur par defaut pour les hommes
                }

                val pourcentageGraisse = when (rgGender.checkedRadioButtonId) {
                    R.id.rbMale_fragment -> {
                        495 / (1.0324 - 0.19077 * log10(ceinture - cou) + 0.15456 * log10(taille)) - 450
                    }
                    R.id.rbFemale_fragment -> {
                        495 / (1.29579 - 0.35004 * log10(ceinture + hanche - cou) + 0.221 * log10(taille)) - 450
                    }
                    else -> {
                        Toast.makeText(requireContext(), "يرجى اختيار الجنس", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                val graisseFinale = pourcentageGraisse.coerceIn(0.0, 40.0)
                val arrondi = String.format("%.1f", graisseFinale)
                progressBar.progress = graisseFinale.toInt()
                pourcentageText.text = "$arrondi%"

                typeText.text = when {
                    graisseFinale < 6 -> "دهون أساسية"
                    graisseFinale in 6.0..13.0 -> "رياضي"
                    graisseFinale in 14.0..17.0 -> "جيد"
                    graisseFinale in 18.0..24.0 -> "متوسط"
                    else -> "مرتفع"
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "يرجى ملء جميع الحقول بشكل صحيح", Toast.LENGTH_SHORT).show()
            }
        }



    }
}








/*
package com.example.myprojetsynthese2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myprojetsynthese2.R
import kotlin.math.log10

class FragmentPoucentageDeMatieresGrasseas : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poucentage_de_matieres_grasseas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupération des vues
        val rgGender = view.findViewById<RadioGroup>(R.id.rgGender_fragment)
        val rbMale = view.findViewById<RadioButton>(R.id.rbMale_fragment)
        val rbFemale = view.findViewById<RadioButton>(R.id.rbFemale_fragment)
        val etAge = view.findViewById<EditText>(R.id.etAge_fragment)
        val etPoids = view.findViewById<EditText>(R.id.etPoids_fragment)
        val etTaille = view.findViewById<EditText>(R.id.etTaille_fragment)
        val etCou = view.findViewById<EditText>(R.id.etCou_fragment)
        val etCeinture = view.findViewById<EditText>(R.id.etTailleCeinture_fragment)
        val btnCalculer = view.findViewById<Button>(R.id.btnCalculerGraisse_fragment)
        val progressBar = view.findViewById<ProgressBar>(R.id.pv_fragment)
        val percentageText = view.findViewById<TextView>(R.id.percentageText)
        val typeText = view.findViewById<TextView>(R.id.typeText)

        btnCalculer.setOnClickListener {
            try {
                val age = etAge.text.toString().toInt()
                val poids = etPoids.text.toString().toDouble()
                val taille = etTaille.text.toString().toDouble()
                val cou = etCou.text.toString().toDouble()
                val ceinture = etCeinture.text.toString().toDouble()

                val pourcentageGraisse = when (rgGender.checkedRadioButtonId) {
                    R.id.rbMale_fragment -> {
                        495 / (1.0324 - 0.19077 * log10(ceinture - cou) + 0.15456 * log10(taille)) - 450
                    }
                    R.id.rbFemale_fragment -> {
                        val hanche = 90.0 // Valeur par défaut
                        495 / (1.29579 - 0.35004 * log10(ceinture + hanche - cou) + 0.221 * log10(taille)) - 450
                    }
                    else -> {
                        Toast.makeText(requireContext(), "يرجى اختيار الجنس", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                val graisseFinale = pourcentageGraisse.coerceIn(0.0, 40.0)
                val arrondi = String.format("%.1f", graisseFinale)
                progressBar.progress = graisseFinale.toInt()
                percentageText.text = "$arrondi%"

                typeText.text = when {
                    graisseFinale < 6 -> "دهون أساسية"
                    graisseFinale in 6.0..13.0 -> "رياضي"
                    graisseFinale in 14.0..17.0 -> "جيد"
                    graisseFinale in 18.0..24.0 -> "متوسط"
                    else -> "مرتفع"
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "يرجى ملء جميع الحقول بشكل صحيح", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
*/
