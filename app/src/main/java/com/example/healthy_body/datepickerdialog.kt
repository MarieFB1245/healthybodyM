package com.example.healthy_body

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import java.util.*

class datepickerdialog : DialogFragment() {
    private var listener: DatePickerDialog.OnDateSetListener? = null

    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(getActivity())
        // Get the layout inflater
        val inflater = getActivity()!!.getLayoutInflater()

        val cal = Calendar.getInstance()

        val dialog = inflater.inflate(R.layout.datepickerdialog, null)
        val monthPicker = dialog.findViewById(R.id.picker_month) as NumberPicker
        val yearPicker = dialog.findViewById(R.id.picker_year) as NumberPicker

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = cal.get(Calendar.MONTH) + 1

        val year = cal.get(Calendar.YEAR)
        yearPicker.minValue = 2000
        yearPicker.maxValue = year
        yearPicker.value = year

        builder.setView(dialog)
            // Add action buttons
            .setPositiveButton("ok",
                DialogInterface.OnClickListener { dialog, id ->
                    listener!!.onDateSet(
                        null,
                        yearPicker.value,
                        monthPicker.value,
                        0
                    )
                })
            .setNegativeButton("cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    this@datepickerdialog.getDialog()!!.cancel()
                })
        return builder.create()
    }

    companion object {

        private val MAX_YEAR = 2099
    }
}