
package ir.hamiss.persianaddresspicker.SearchAddress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


import ir.hamiss.persianaddresspicker.GLOBAL;
import ir.hamiss.persianaddresspicker.Modul.SearchItem;
import ir.hamiss.persianaddresspicker.R;


public class SearchItemsAdapter extends RecyclerView.Adapter<SearchItemsAdapter.VersionViewHolder> {
    Context context;
    CompositeDisposable bag = new CompositeDisposable();
    BehaviorRelay<ArrayList<SearchItem>> versionModels = BehaviorRelay.createDefault(new ArrayList<SearchItem>());
    int width;

    {
        versionModels.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<SearchItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        bag.add(d);
                    }

                    @Override
                    public void onNext(ArrayList<SearchItem> SearchItems) {

                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public SearchItemsAdapter(Context context) {
        this.context = context;

        width = GLOBAL.screenWidth(1,1);


    }


    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_add_base, parent, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder holder, final int position) {




        SearchItem s = versionModels.getValue().get(position);

        if (s.getAddress()!=null&& s.getAddress().trim().length()!=0)
        holder.address_tv.setText(s.getAddress());
        else holder.address_tv.setText("-");


        if (s.getTitle()!=null)
            holder.title_tv.setText(s.getTitle());
        else holder.title_tv.setText("-");

        switch (s.getCategory()){
            case "place":
                holder.icon_iv.setImageResource(R.drawable.place);

                break;
            case "municipal":
                holder.icon_iv.setImageResource(R.drawable.street);

                break;
            case "region":
                holder.icon_iv.setImageResource(R.drawable.flag);

                break;
                default:
                    holder.icon_iv.setImageResource(R.drawable.place);


        }



    }


    @Override
    public int getItemCount() {
        return versionModels == null ? 0 : versionModels.getValue().size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {


        ImageView icon_iv;
        TextView title_tv;
        TextView address_tv;






        public VersionViewHolder(View itemView) {
            super(itemView);



            title_tv = itemView.findViewById(R.id.title_tv);
            address_tv = itemView.findViewById(R.id.address_tv);
            icon_iv = itemView.findViewById(R.id.icon_iv);




        }

    }


}
