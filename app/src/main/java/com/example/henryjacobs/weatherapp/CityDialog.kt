package com.example.henryjacobs.weatherapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.henryjacobs.weatherapp.data.City
import kotlinx.android.synthetic.main.dialog_add_city.view.*
import java.util.*

class CityDialog : DialogFragment(){

    interface CityHandler{
        fun cityCreated(item: City)

    }

    private lateinit var cityHandler: CityHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is CityHandler){
            cityHandler = context
        }
        else{
            throw RuntimeException(getString(R.string.implement_error))
        }
    }

    private lateinit var etCityName: EditText
    private lateinit var btnCancel: Button
    private lateinit var btnOK: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = android.support.v7.app.AlertDialog.Builder(requireContext())

        val rootView = requireActivity().layoutInflater.inflate(R.layout.dialog_add_city, null)
        etCityName = rootView.etCityName
        btnCancel = rootView.btnCancel
        btnOK = rootView.btnOK
        builder.setView(rootView)
        btnOK.setOnClickListener {
            handleCityCreate()
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            handleCityCreate()
            dialog.dismiss()
        }
    }

    private fun handleCityCreate() {
        cityHandler.cityCreated(
            City(
                null,
                etCityName.text.toString()
            )
        )
    }

}