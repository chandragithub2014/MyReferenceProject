package com.ref.bindingfeature.databindinglistdetailnetwork.interfaces

import com.ref.bindingfeature.databindinglistdetailnetwork.model.GitUserModel

interface ItemClickListener {
    fun gitUserClicked(clickedUser: GitUserModel.GitUserModelItem)
}