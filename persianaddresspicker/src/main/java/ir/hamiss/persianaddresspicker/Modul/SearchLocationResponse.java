package ir.hamiss.persianaddresspicker.Modul;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchLocationResponse {

    @SerializedName("count")
    private int count;
    @SerializedName("items")
    private ArrayList<SearchItem> items;

    public int getCount() {
        return count;
    }

    public ArrayList<SearchItem> getItems() {
        return items;
    }
}
