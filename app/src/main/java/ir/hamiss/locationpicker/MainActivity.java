package ir.hamiss.locationpicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ir.hamiss.persianaddresspicker.Modul.AddressPickerBuilder;
import ir.hamiss.persianaddresspicker.Modul.SearchItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==123){
            if (resultCode ==RESULT_OK){
                if (data!=null) {
                    Bundle extras = data.getExtras();
                    if (extras!=null) {

                        SearchItem loc = (SearchItem) extras.get(getString(ir.hamiss.persianaddresspicker.R.string.location_searched));

                        if (loc!=null)
                        Log.e("location get from map:",loc.toString());


                    }
                }
            }
        }
    }

    public void get_loc(View view) {
        AddressPickerBuilder.getInstance()
                .setDrawable_src(R.drawable.location_placeholder)
                .setEnable_info(false)

                .setHint_title("fdfs")
                .setDialog_string("تایتل")
                .setNeshan_api_key("service.zVh8wVLrTTqGuVBCS5ETDeOYcVHtsdS189aF5V6Y")
                .setRequest_code(123)
                .setSearch_alley("جستجو محله")

                .setZoom_map(17)
                .build(this);
    }


}
