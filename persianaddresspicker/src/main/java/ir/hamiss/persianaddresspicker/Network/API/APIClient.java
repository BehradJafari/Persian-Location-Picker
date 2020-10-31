package ir.hamiss.persianaddresspicker.Network.API;

import io.reactivex.Single;
import ir.hamiss.persianaddresspicker.Modul.SearchLocationResponse;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIClient {










    //region search location
    @Headers("Api-Key:service.zVh8wVLrTTqGuVBCS5ETDeOYcVHtsdS189aF5V6Y")
    @GET
    Single<SearchLocationResponse> search(@Url String url, @Header("Api-Key") String api_key, @Query("term") String term, @Query("lat") double lat, @Query("lng") double lng);
    //endregion





}
