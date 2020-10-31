package ir.hamiss.persianaddresspicker.Modul;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;

import ir.hamiss.persianaddresspicker.G;
import ir.hamiss.persianaddresspicker.Maps.MapsActivity;
import ir.hamiss.persianaddresspicker.R;

public class AddressPickerBuilder {


    //default req code

    public static final int REQ_ADDRESS = 139;


    //set_visibility info edit_text
    private boolean enable_info ;

    //hint for dialog alert
    private String hint_info;


    //hint for dialog alert
    private String hint_title;

    //neshan api_key
    private String neshan_api_key;


    //zoom in map int
    private int zoom_map;

    //request_code
    private int request_code;


    //drawable src
    private int drawable_src;

    //start latitude
    private double start_latitude = 35.68;

    //start longitude
    private double start_longitude = 51.38;


    //search alley text
    private String search_alley;


    //search alley drawable
    private String search_alley_drawable;


    //error empty field

    private String empty_field;


    //typeFace
    private Typeface tf;


    public AddressPickerBuilder setEmpty_field(String empty_field) {
        this.empty_field = empty_field;
        return this;
    }

    public AddressPickerBuilder setSearch_alley(String search_alley) {
        this.search_alley = search_alley;
        return this;
    }

    //


    public AddressPickerBuilder setEnable_info(boolean enable_info) {
        this.enable_info = enable_info;
        return this;
    }

    public AddressPickerBuilder setHint_info(String hint_info) {
        this.hint_info = hint_info;
        return this;
    }

    public AddressPickerBuilder setHint_title(String hint_title) {
        this.hint_title = hint_title;
        return this;
    }

    public AddressPickerBuilder setNeshan_api_key(String neshan_api_key) {
        this.neshan_api_key = neshan_api_key;
        return this;
    }

    public AddressPickerBuilder setZoom_map(int zoom_map) {
        this.zoom_map = zoom_map;
        return this;
    }

    public AddressPickerBuilder setRequest_code(int request_code) {
        this.request_code = request_code;
        return this;
    }

    public AddressPickerBuilder setDrawable_src(int drawable_src) {
        this.drawable_src = drawable_src;
        return this;
    }

    public AddressPickerBuilder setStart_latitude(double start_latitude) {
        this.start_latitude = start_latitude;
        return this;
    }

    public AddressPickerBuilder setStart_longitude(double start_longitude) {
        this.start_longitude = start_longitude;
        return this;
    }

    public AddressPickerBuilder setSearch_alley_drawable(String search_alley_drawable) {
        this.search_alley_drawable = search_alley_drawable;
        return this;
    }

    /***
     * start activity for result
     * @return
     */

    public void build(Activity activity){
        Intent i = new Intent(activity, MapsActivity.class);


        set_extra(R.string.hint_title,hint_title,i);
        set_extra(R.string.hint_info,hint_info,i);
        set_extra(R.string.api_key,neshan_api_key,i);
        set_extra(R.string.hint_title,hint_title,i);

        set_extra(R.string.map_zoom,zoom_map,i);
        set_extra(R.string.drawable_int,drawable_src,i);

        set_extra(R.string.start_lat,start_latitude,i);
        set_extra(R.string.start_lng,start_longitude,i);

        set_extra(R.string.enable_info,enable_info,i);

        set_extra(R.string.search_alley,search_alley,i);
        set_extra(R.string.search_alley_drawable,search_alley_drawable,i);




        if (request_code == 0){

            request_code = REQ_ADDRESS;
        }

        activity.startActivityForResult(i,request_code);
    }

    private void set_extra(int string , String put,Intent i){
        if (put!=null){
            i.putExtra(G.context.getString(string),put);
        }
    }
    private void set_extra(int string , int put,Intent i){

            i.putExtra(G.context.getString(string),put);


    }

    private void set_extra(int string , double put,Intent i){

        i.putExtra(G.context.getString(string),put);
    }

    private void set_extra(int string , boolean put,Intent i){

        i.putExtra(G.context.getString(string),put);
    }


    public boolean isEnable_info() {
        return enable_info;
    }

    public String getHint_info() {
        return hint_info;
    }

    public String getHint_title() {
        return hint_title;
    }

    public String getNeshan_api_key() {
        return neshan_api_key;
    }

    public int getZoom_map() {
        return zoom_map;
    }

    public int getRequest_code() {
        return request_code;
    }

    public int getDrawable_src() {
        return drawable_src;
    }

    public double getStart_latitude() {
        return start_latitude;
    }

    public double getStart_longitude() {
        return start_longitude;
    }

    public Typeface getTf() {
        return tf;
    }

    public AddressPickerBuilder setTf(Typeface tf) {
        this.tf = tf;
        return this;
    }

    public static AddressPickerBuilder getOurInstance() {
        return ourInstance;
    }

    private static final AddressPickerBuilder ourInstance = new AddressPickerBuilder();

    public static AddressPickerBuilder getInstance() {
        return ourInstance;
    }

    private AddressPickerBuilder() {
    }


}
