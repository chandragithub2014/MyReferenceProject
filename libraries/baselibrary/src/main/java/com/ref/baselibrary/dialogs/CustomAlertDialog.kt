package com.ref.baselibrary.dialogs

import android.app.AlertDialog


object CustomAlertDialog {

    private lateinit var dialogListener: DialogListener
    fun showAlertDialog(dialogModel: DialogModel, dialogListener: DialogListener){
        CustomAlertDialog.dialogListener = dialogListener
        val builder: AlertDialog.Builder = AlertDialog.Builder(dialogModel.context)
        builder.setTitle(dialogModel.title)
        builder.setMessage(dialogModel.content)
        builder.setCancelable(false)
        builder
            .setPositiveButton(
                dialogModel.positiveButton
            ) { dialog, which -> // When the user click yes button
                // then app will close
                dialogListener.onPositiveButtonClick(dialogModel.actionType)
            }

        builder
            .setNegativeButton(
                dialogModel.negativeButton
            ) { dialog, which -> // When the user click yes button
                // then app will close
                 dialog.cancel()
                dialogListener.onNegativeButtonClick(dialogModel.actionType)
            }
        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()



    }
}