package colruyt.android.dsa.rayon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ref.bindingfeature.viewmodel.BindingViewModel
import kotlinx.coroutines.Dispatchers

class BindingViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BindingViewModel::class.java)) {
            return BindingViewModel(Dispatchers.IO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}