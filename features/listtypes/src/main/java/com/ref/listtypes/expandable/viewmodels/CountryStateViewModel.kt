package com.ref.listtypes.expandable.viewmodels

import androidx.lifecycle.*
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.listtypes.expandable.models.ExpandableCountryModel
import com.ref.listtypes.expandable.models.StateCapital
import com.ref.listtypes.expandable.repository.CountryStateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception

class CountryStateViewModel(private val dispatcher: CoroutineDispatcher, private val countryStateRepository: CountryStateRepository) : ViewModel(),
    LifecycleObserver {
    private val LOG_TAG = "CountryStateViewModel"
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _obtainCountryStatesResponse= MutableLiveData<ResultOf<StateCapital>>()
    val  obtainCountryStatesResponse: LiveData<ResultOf<StateCapital>> = _obtainCountryStatesResponse

     fun obtainCountryStateCapitals(){
         loading.postValue(true)

         viewModelScope.launch(dispatcher){
             var  errorCode = -1
             try{
                 var stateCapitalResponse =  countryStateRepository.fetchCountryStateCapitals()
                 stateCapitalResponse?.let {
                     loading.postValue(false)
                     _obtainCountryStatesResponse.postValue(ResultOf.Success(it))
                 }

             }catch (e : Exception){
                 loading.postValue(false)
                 e.printStackTrace()
                 if(errorCode != -1){
                     _obtainCountryStatesResponse.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                 }else{
                     _obtainCountryStatesResponse.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                 }
             }
         }
     }
/*
openShelfModel.add(
                         ExpandableOpenShelfModel(
                             ExpandableOpenShelfModel.PARENT,
                             shelfSequence
                         )
                     )
 */
    fun prepareDataForExpandableAdapter(stateCapital: StateCapital) : MutableList<ExpandableCountryModel>{
        var expandableCountryModel = mutableListOf<ExpandableCountryModel>()
        for (states in stateCapital.countries) {
            expandableCountryModel.add(ExpandableCountryModel(ExpandableCountryModel.PARENT,states))
        }
         return expandableCountryModel
    }


    fun prepareDataForSectionedAdapter(stateCapital: StateCapital) : MutableList<ExpandableCountryModel>{
        var expandableCountryModel = mutableListOf<ExpandableCountryModel>()
        for (states in stateCapital.countries) {
            expandableCountryModel.add(ExpandableCountryModel(ExpandableCountryModel.PARENT,states))
             for(capitals in states.states){
                 expandableCountryModel.add(ExpandableCountryModel(ExpandableCountryModel.CHILD,capitals))
             }
        }
        return expandableCountryModel
    }
}