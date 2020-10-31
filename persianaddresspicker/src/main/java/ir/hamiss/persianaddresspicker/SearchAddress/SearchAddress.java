package ir.hamiss.persianaddresspicker.SearchAddress;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import ir.hamiss.persianaddresspicker.Modul.RecyclerItemClickListener;
import ir.hamiss.persianaddresspicker.Modul.SearchItem;
import ir.hamiss.persianaddresspicker.R;


public class SearchAddress extends AppCompatActivity {


    EditText search_et;
    RecyclerView recyclerView;
    ProgressBar progressBar;


    SearchItemsAdapter adapter;


    SearchPresenter presenter = new SearchPresenter();


    ImageButton clear_text;
    String api_key;

    double start_lat;
    double start_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.containsKey(getString(R.string.api_key))) {
                    api_key = extras.getString(getString(R.string.api_key));
                }
                if (extras.containsKey(getString(R.string.start_lat))){
                    start_lat = extras.getDouble(getString(R.string.start_lat));

                }
                if (extras.containsKey(getString(R.string.start_lng))){
                    start_lon = extras.getDouble(getString(R.string.start_lng));
                }
            }
        }


        search_et = findViewById(R.id.search_et);
        recyclerView = findViewById(R.id.recycleview);
        clear_text = findViewById(R.id.clear_text);
        progressBar = findViewById(R.id.progressBar);

        presenter.vis_invis(this, true);


        set_recycle();
        edittext_change();


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                return_data(adapter.versionModels.getValue().get(position));


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        clear_text.setOnClickListener(v -> {
            search_et.setText("");
        });

    }

    private void edittext_change() {

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 2) {
                    presenter.search(charSequence.toString(), SearchAddress.this);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void set_recycle() {


        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SearchItemsAdapter(this);
        recyclerView.setAdapter(adapter);


    }


    private void return_data(SearchItem item) {

        Intent data = new Intent();

        data.putExtra("item", item);


        setResult(RESULT_OK, data);
        finish();

    }

    public void back(View view) {
        onBackPressed();
    }
}
