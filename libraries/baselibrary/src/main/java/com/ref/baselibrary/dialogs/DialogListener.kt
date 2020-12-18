package com.ref.baselibrary.dialogs

interface DialogListener {
  fun   onPositiveButtonClick(actionType : String)
  fun   onNegativeButtonClick(actionType : String)
}