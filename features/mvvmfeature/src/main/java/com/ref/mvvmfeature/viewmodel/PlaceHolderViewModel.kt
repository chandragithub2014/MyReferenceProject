package colruyt.android.dsa.rayon.viewmodel

import androidx.lifecycle.*
import com.ref.mvvmfeature.model.PlaceHolderCommentsModel
import com.ref.mvvmfeature.model.PlaceHolderPostsDataModel
import com.ref.mvvmfeature.repository.PlaceHolderRepository
import kotlinx.coroutines.*


class PlaceHolderViewModel(private val dispatcher: CoroutineDispatcher, private val placeHolderRepository: PlaceHolderRepository) : ViewModel(), LifecycleObserver {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val errorOnAPI = MutableLiveData<String>()
     private val errorMessage = MutableLiveData<String>()
    var postsResponseMutableLiveData  = MutableLiveData<PlaceHolderPostsDataModel>()
   // val errorResponse = MutableLiveData<ErrorModel>()
    var commentsResponseMutableLiveData  = MutableLiveData<PlaceHolderCommentsModel>()

    fun fetchPostsAndComments(){

        viewModelScope.launch(dispatcher){
            try{

                supervisorScope   {
                    loading.postValue(true)
                    var postsRepositoryCall = async { placeHolderRepository.fetchPosts() }
                    val coomentsRepositoryCall  = async { placeHolderRepository.fetchComments() }
                    val postsResponse = postsRepositoryCall.await()
                    val commentsResponse = coomentsRepositoryCall.await()
                    if (postsResponse.isSuccessful) {
                        println("Response is ::: ${postsResponse.body()}")
                        postsResponseMutableLiveData.postValue(postsResponse.body())
                     } else {

                        println("Received Response::::")
                        println("Response code ${postsResponse.code()}")
                        println("Response Error body is  ${postsResponse.errorBody()}")
                        println("Response Error Message is  ${postsResponse.message()}")
                        // errorResponse.postValue(response.message())
                     /*   val error: ErrorModel = ErrorUtils.parseError(response)
                        errorResponse.postValue(error)*/
                      }
                    if(commentsResponse.isSuccessful){

                        println("Response is ::: ${commentsResponse.body()}")
                        commentsResponseMutableLiveData.postValue(commentsResponse.body())
                    }else{

                        println("Received Closed Shelf Response::::")
                        println("Response code ${commentsResponse.code()}")
                        println("Response Error body is  ${commentsResponse.errorBody()}")
                        println("Response Error Message is  ${commentsResponse.message()}")
                       /* val error: ErrorModel = ErrorUtils.parseError(response)
                        errorResponse.postValue(error)*/
                    }
                    loading.postValue(false)
                }
           }
            catch (e:java.net.UnknownHostException){
           //     errorMessage.postValue(colruyt.android.dsa.ui_components.R.string.unknownhost_error_message.toString())
                e.printStackTrace()
                loading.postValue(false)


            }
            catch (e:Exception){
                e.printStackTrace()
                loading.postValue(false)


            }
        }

    }


    fun fetchPostsFromService(){
        try{
            viewModelScope.launch(Dispatchers.Main) {
                var postsRepositoryResponse= placeHolderRepository.fetchPosts()
                if(postsRepositoryResponse.isSuccessful){
                    postsResponseMutableLiveData.postValue(postsRepositoryResponse.body())
                   // loading.postValue(false)
                }else{
                    println("Response code ${postsRepositoryResponse.code()}")
                    println("Response Error body is  ${postsRepositoryResponse.errorBody()}")
                    println("Response Error Message is  ${postsRepositoryResponse.message()}")
                    loading.postValue(false)
                }

            }

        }catch (e:java.lang.Exception){
            e.printStackTrace()
            loading.postValue(false)
        }
    }

    fun fetchCommentsFromService(){
        try{
            viewModelScope.launch(Dispatchers.Main) {
                var commentsRepositoryResponse= placeHolderRepository.fetchComments()
                if(commentsRepositoryResponse.isSuccessful){
                    commentsResponseMutableLiveData.postValue(commentsRepositoryResponse.body())
                    loading.postValue(false)
                }else{
                    println("Response code ${commentsRepositoryResponse.code()}")
                    println("Response Error body is  ${commentsRepositoryResponse.errorBody()}")
                    println("Response Error Message is  ${commentsRepositoryResponse.message()}")
                    loading.postValue(false)
                }

            }

        }catch (e:java.lang.Exception){
            e.printStackTrace()
            loading.postValue(false)
        }
    }

   /* fun fetchPostsAndCommentsFromService(){
        loading.postValue(true)
        try{
           viewModelScope.launch{
            coroutineScope {
                var postsRepositoryCall = async { placeHolderRepository.fetchPosts() }
                val commentsRepositoryCall = async { placeHolderRepository.fetchComments() }
                if (postsRepositoryCall.await().isSuccessful && commentsRepositoryCall.await().isSuccessful) {
                    postsRepositoryCall.await().body().let {
                        commentsRepositoryCall.await().body().let { it1 ->
                            println("Comments Response $it1")
                            println("Posts Response $it")
                            commentsResponseMutableLiveData.postValue(it1)
                            postsResponseMutableLiveData.postValue(it)
                            loading.postValue(false)
                        }
                    }
                } else {
                    loading.postValue(false)
                    println("Response code ${postsRepositoryCall.await().code()}")
                    println("Response Error body is  ${postsRepositoryCall.await().errorBody()}")
                    println("Response Error Message is  ${postsRepositoryCall.await().message()}")

                    println("Response code ${commentsRepositoryCall.await().code()}")
                    println("Response Error body is  ${commentsRepositoryCall.await().errorBody()}")
                    println(
                        "Response Error Message is  ${commentsRepositoryCall.await().message()}"
                    )
                }
            }
        }
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            loading.postValue(false)
        }
    }*/

     fun fetchPostsLiveData():LiveData<PlaceHolderPostsDataModel>  = postsResponseMutableLiveData
     fun fetchCommentsLiveData():LiveData<PlaceHolderCommentsModel> = commentsResponseMutableLiveData
     fun fetchLoadStatus(): LiveData<Boolean> = loading




/*
Response:
{
  "shelfSequence": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "type": "CS",
      "timestamp": 1596533801,
      "shelves": [
        154,
        18,
        64,
        75,
        77,
        46,
        35,
        17,
        241,
        15,
        16
      ],
      "lockedBy": "3C1W"
    },
    {
      "id": "550e8400-e29b-41d4-a716-446655440001",
      "type": "OP",
      "timestamp": 1596533740,
      "shelves": [
        777,
        45,
        87,
        12,
        28,
        96,
        74,
        44,
        75,
        34,
        33
      ],
      "lockedBy": "E4R3"
    }
  ]
}
 */
    /*
     var openShelfModel = mutableListOf<OpenShelfModel>()

        var  temp = ClosedShelfParent("Telling Collect & Go",20,"7u10",createChild())
        openShelfModel.add(OpenShelfModel(OpenShelfModel.PARENT,temp))
      /*  temp = ClosedShelfParent("",0,"",mutableListOf<ClosedShelfChild>())
        openShelfModel.add(OpenShelfModel(OpenShelfModel.BUTTONLAYOUT,temp))*/
        temp = ClosedShelfParent("Telling Intern",20,"7u10",createChild())
        openShelfModel.add(OpenShelfModel(OpenShelfModel.PARENT,temp))
        return  openShelfModel
     */
}