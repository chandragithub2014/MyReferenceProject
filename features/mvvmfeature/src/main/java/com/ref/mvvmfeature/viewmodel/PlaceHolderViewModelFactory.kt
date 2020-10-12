package colruyt.android.dsa.rayon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ref.mvvmfeature.dependency.DependencyUtils
import com.ref.mvvmfeature.repository.PlaceHolderRepository
import kotlinx.coroutines.Dispatchers

class PlaceHolderViewModelFactory : ViewModelProvider.Factory {
    lateinit var placeHolderRepository: PlaceHolderRepository
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        placeHolderRepository = DependencyUtils.providePlaceHolderRepository()
        if (modelClass.isAssignableFrom(PlaceHolderViewModel::class.java)) {
            return PlaceHolderViewModel(Dispatchers.IO,placeHolderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}