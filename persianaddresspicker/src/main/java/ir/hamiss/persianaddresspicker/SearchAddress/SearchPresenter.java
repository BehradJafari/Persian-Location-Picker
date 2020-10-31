package ir.hamiss.persianaddresspicker.SearchAddress;


import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.hamiss.persianaddresspicker.Modul.SearchItem;
import ir.hamiss.persianaddresspicker.Modul.SearchLocationResponse;
import ir.hamiss.persianaddresspicker.Network.NetworkLayer;


public class SearchPresenter {


    void search(String text, final SearchAddress activity) {




        vis_invis(activity,false);


        NetworkLayer.getInstance().search("https://api.neshan.org/v1/search",activity.api_key,text,activity.start_lat,activity.start_lon)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<SearchLocationResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(SearchLocationResponse searchLocationResponse) {

                        activity.adapter.versionModels.accept(searchLocationResponse.getItems());
                        vis_invis(activity,true);

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("LocationPicker",e.toString());
                    }
                });


    }


    private ArrayList<SearchItem> gson(String json) {
        return new Gson().fromJson(json, SearchLocationResponse.class).getItems();
    }


     void vis_invis(SearchAddress activity,boolean vis){
        if (vis){
            activity.progressBar.setVisibility(View.GONE);
            activity.recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            activity.progressBar.setVisibility(View.VISIBLE);
            activity.recyclerView.setVisibility(View.GONE);

        }


    }


}
