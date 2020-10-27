package com.ref.bindingfeature.databindingrecyclerview.interfaces

import com.ref.bindingfeature.databindingrecyclerview.model.GitUserModel

interface ItemClickListener {
    fun gitUserClicked(clickedUser: GitUserModel.GitUserModelItem)
}