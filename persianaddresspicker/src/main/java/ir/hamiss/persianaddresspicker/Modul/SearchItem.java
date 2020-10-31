package ir.hamiss.persianaddresspicker.Modul;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchItem implements Serializable {

//     "category": "place",
//             "type": "residential_complex",
//             "region": "بخش رودبار قصران، شهرستان شمیرانات، استان تهران",
//             "neighbourhood": "الهیه",
//             "location": {
//        "x": 51.426983,
//                "y": 35.781368,
//                "z": "NaN"
//    },
//            "title": "برج مسکونی الهیه",
//            "address": "نلسون ماندلا، گلنار، ماهرو، پناهی"

    @SerializedName("title")
    private String title;
    @SerializedName("address")
    private String address;
    @SerializedName("location")
    private SearchLocation location;
    @SerializedName("category")
    private String category;

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public SearchLocation getLocation() {
        return location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SearchItem() {
        this.location = new SearchLocation();
    }

    public SearchItem(String title, String address, SearchLocation location) {
        this.title = title;
        this.address = address;
        this.location = location;
    }

    public SearchItem setAddress(String address) {
        this.address = address;
        return this;
    }

    public SearchItem setLocation(SearchLocation location) {
        this.location = location;
        return this;
    }

    @Override
    public String toString() {
        return "SearchItem{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", category='" + category + '\'' +
                '}';
    }
}
