package com.sun.makeupwindow.ui.producttype

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.sun.makeupwindow.databinding.DialogPriceBinding
import com.sun.makeupwindow.utlis.RESULT_CODE
import com.sun.makeupwindow.utlis.RETURN_PRICE

class DialogPrice : DialogFragment() {
    private lateinit var binding: DialogPriceBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DialogPriceBinding.inflate(inflater,container, false)
        initAction()
        return binding.root
    }

    private fun initAction() {
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonApply.setOnClickListener {
            val selectId = binding.radioGroup.checkedRadioButtonId
            val radio = binding.root.findViewById<RadioButton>(selectId)

            var resultRadio = radio.text.toString()

            var intent = Intent()
            intent.putExtra(RETURN_PRICE, resultRadio)
            targetFragment?.onActivityResult(
                targetRequestCode, RESULT_CODE, intent
            )
            dismiss()
        }
    }
}
