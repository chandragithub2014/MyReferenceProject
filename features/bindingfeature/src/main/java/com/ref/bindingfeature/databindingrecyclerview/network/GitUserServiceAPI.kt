package colruyt.android.dsa.rayon.network


import com.ref.bindingfeature.databindingrecyclerview.model.GitUserModel
import retrofit2.Response
import retrofit2.http.*

interface GitUserServiceAPI {

    @GET("users")
   suspend fun fetchUsers() : Response<GitUserModel>



}
