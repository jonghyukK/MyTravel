package org.kjh.mytravel

import org.kjh.mytravel.data.model.LoginResponse
import org.kjh.mytravel.data.model.SignUpResponse
import org.kjh.mytravel.domain.Result
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * MyTravel
 * Class: ApiService
 * Created by mac on 2022/01/17.
 *
 * Description:
 */
interface ApiService {

    @POST("user")
    suspend fun createUser(
        @Query("email"   ) email    : String,
        @Query("pw"      ) pw       : String,
        @Query("token") token: String = "test"
    ): SignUpResponse

    @GET("user/login")
    suspend fun login(
        @Query("email") email    : String,
        @Query("pw"   ) pw       : String
    ): Result<LoginResponse>
}