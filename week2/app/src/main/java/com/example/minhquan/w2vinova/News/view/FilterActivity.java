package com.example.minhquan.w2vinova.News.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.minhquan.w2vinova.DatePicker.DatePickerFragment;
import com.example.minhquan.w2vinova.News.presenter.ListNewsPresenter;
import com.example.minhquan.w2vinova.News.presenter.ListNewsPresenterImpl;
import com.example.minhquan.w2vinova.News.repository.NewsRepository;
import com.example.minhquan.w2vinova.News.repository.NewsRepositoryImpl;
import com.example.minhquan.w2vinova.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.edtDatePicker)
    EditText datePicker;
    @BindView(R.id.btnSave)
    Button save;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.cbArts)
    CheckBox arts;
    @BindView(R.id.cbFashionStyle)
    CheckBox fashion_style;
    @BindView(R.id.cbSports)
    CheckBox sports;
    String date;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
        ButterKnife.bind(this);

        Bundle bundle2 = getIntent().getExtras();
        if(bundle2.getString("SortOrder") != null){
            datePicker.setText(bundle2.getString("DatePicker"));

            if(bundle2.getString("SortOrder").equals("Oldest")){
                spinner.setSelection(0);
            }else {
                spinner.setSelection(1);
            }

            arts.setChecked(bundle2.getBoolean("Arts"));
            fashion_style.setChecked(bundle2.getBoolean("FashionStyle"));
            sports.setChecked(bundle2.getBoolean("Sports"));
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Date", date);
                bundle.putString("DatePicker", datePicker.getText().toString());
                bundle.putString("SortOrder", spinner.getSelectedItem().toString());
                bundle.putBoolean("Arts", arts.isChecked());
                bundle.putBoolean("FashionStyle", fashion_style.isChecked());
                bundle.putBoolean("Sports", sports.isChecked());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    @OnClick(R.id.edtDatePicker)
    public void getDate() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "Date Picker");
    }

    @OnClick(R.id.btnCancel)
    public void closeActivity() {
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        datePicker.setText(month + "/" + dayOfMonth + "/" + year);
        date = year + "" + (month<10?(0+""+month):month) + "" + (dayOfMonth<10?(0+""+dayOfMonth):dayOfMonth) + "";

    }
}
