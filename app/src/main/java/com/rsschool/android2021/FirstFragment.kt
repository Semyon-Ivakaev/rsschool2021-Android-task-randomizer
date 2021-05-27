package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null

    var min = ""
    var max = ""

    // Interface
    private var generateButtonClickListener: GenerateButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            createValues()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GenerateButtonClickListener) {
            generateButtonClickListener = context
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    private fun createValues() {
        min = minValue?.text.toString()
        max = maxValue?.text.toString()
        if (checkValues(min, max)) generateButtonClickListener?.openSecondFragment(min.toInt(), max.toInt())
        else Toast.makeText(context, "Поля \"Min\" и \"Max\" должны быть заполнены корректно.", Toast.LENGTH_SHORT).show()
    }

    private fun checkValues(minimal: String, maximum: String): Boolean {
        if ((minimal.isNotEmpty() && minimal.isDigitsOnly()) && (maximum.isNotEmpty() && maximum.isDigitsOnly())) {
            try {
                val min = minimal.toInt()
                val max = maximum.toInt()
                if (min < max && max > 0 && min >= 0 && max > 0) {
                    return true
                }
            } catch (e: NumberFormatException) {
                return false
            }
        }
        return false
    }
}