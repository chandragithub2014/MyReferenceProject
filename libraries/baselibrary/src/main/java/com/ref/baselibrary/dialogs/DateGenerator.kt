package com.ref.baselibrary.dialogs

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

 fun setDate(context: Context, editText: EditText){
        var selectedDate:String? = null
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in TextView
            val  selectedDate = "$dayOfMonth/${monthOfYear+1}/$year"
            editText.setText(selectedDate)
        }, year, month, day)
        dpd.show()
    }




