package ms.fitcalculator.model.local.remote

import retrofit2.http.GET
import io.reactivex.Observable
import ms.fitcalculator.model.remote.FoodNutrients
import retrofit2.http.Query

interface GETData {
    @GET("parser?")
    fun getData (@Query("ingr") foodName:String,@Query("app_id") id:String,@Query("app_key") key:String) :Observable<FoodNutrients>
}