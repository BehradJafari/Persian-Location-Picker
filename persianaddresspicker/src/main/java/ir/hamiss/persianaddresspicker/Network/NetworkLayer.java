package ir.hamiss.persianaddresspicker.Network;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import ir.hamiss.persianaddresspicker.Modul.SearchLocationResponse;
import ir.hamiss.persianaddresspicker.Network.API.APIClient;
import ir.hamiss.persianaddresspicker.Network.API.APIService;


public class NetworkLayer implements APIClient{
    private APIClient apiClient;
    private static final NetworkLayer ourInstance = new NetworkLayer();

    public static NetworkLayer getInstance() {
        return ourInstance;
    }

    private NetworkLayer() {
        apiClient = APIService.getApiClient();
    }




    @Override
    public Single<SearchLocationResponse> search(String url,String header, String term, double lat, double lng) {
        return apiClient.search(url,header, term, lat, lng);
    }



//    public Single<ArrayList<Shop>> get_shops(double lat, double lon) {
//        return apiClient.get_shops(lat, lon);
//    }
//
//
//    public Single<ArrayList<Address>> get_addresses() {
//
//        return apiClient.get_addresses();
//    }
//
//
//    public Single<Response> add_address(String title, String details, Location location) {
//        return apiClient.add_address(new AddressAdd(title, details, location));
//    }
//
//    public Single<AddToFav> add_to_fav(String title){
//        return apiClient.add_fav(title);
//    }
//
//    public Single<ArrayList<Favorite>> fav_list(){
//        return apiClient.fav_list();
//    }
//
//    public Single<Response> order_create(OrderCreate orderCreate){
//        return apiClient.order_create(orderCreate);
//    }
//
//    public Single<ArrayList<OrderListResponse>> order_list(){
//        return apiClient.order_list();
//    }
//
//    public Single<MyProfile> get_profile(){
//        return apiClient.get_profile();
//    }
//
//    public Single<Response> set_profile(MyProfile profile){
//        return apiClient.set_profile(profile);
//    }
}
