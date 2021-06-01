package com.sun.makeupwindow.ui.producttype

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.sun.makeupwindow.R
import com.sun.makeupwindow.databinding.DialogSortBinding
import com.sun.makeupwindow.utlis.RESULT_CODE
import com.sun.makeupwindow.utlis.RETURN_SORT
import kotlinx.android.synthetic.main.dialog_price.*
import kotlinx.android.synthetic.main.dialog_sort.view.*

class DialogSort : DialogFragment() {

    private lateinit var binding: DialogSortBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_sort, container, false)
        binding = DialogSortBinding.inflate(inflater, container, false)
        initAction()
        return binding.root
    }

    private fun initAction() {
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonApply.setOnClickListener {
            val selectId = radioGroup.checkedRadioButtonId
            val radio = binding.root.findViewById<RadioButton>(selectId)
            val resultRadio = radio.text.toString()
            var intent = Intent()
            intent.putExtra(RETURN_SORT, resultRadio)
            targetFragment?.onActivityResult(
                targetRequestCode, RESULT_CODE, intent
            )
            dismiss()
        }
    }
}
