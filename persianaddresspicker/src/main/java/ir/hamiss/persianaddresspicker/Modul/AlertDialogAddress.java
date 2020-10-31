package ir.hamiss.persianaddresspicker.Modul;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;

import ir.hamiss.persianaddresspicker.R;

/**
 *
 *
 * Created by behrad on 6/26/2018.
 */
public class AlertDialogAddress extends Dialog {

    public Activity c;
    public Dialog d;
    public Button yes_btn;

    CardView cardView;
    public EditText name_et;
    public EditText detail_et;
    public TextInputLayout name;
    public TextInputLayout detail;


    public AlertDialogAddress(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog_add_adress);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));




        yes_btn = findViewById(R.id.yes_btn);

        name_et = findViewById(R.id.name_et);
        detail_et = findViewById(R.id.detail_et);
        name = findViewById(R.id.name);
        detail = findViewById(R.id.detail);

        findViewById(R.id.close_im).setOnClickListener(v ->{
            this.dismiss();
        });


    }


}