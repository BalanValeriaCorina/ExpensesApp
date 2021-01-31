package com.example.a1094_selistevaleriacorina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    EditText et1;
    EditText et2;
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    TimePicker timePicker;
    Spinner spinner;
    Button btnSave;

     long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                if(valid())
                {
                    Expense obiect=new Expense(Double.parseDouble(et2.getText().toString()),et1.getText().toString(),new Time(timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0),spinner.getSelectedItem().toString(),radioGroup.getCheckedRadioButtonId()==radioButton1.getId()?radioButton1.getText().toString():radioButton2.getText().toString());
                    //EDITARE================================EDITARE PASTRARE DATE LA DESCHIDERE
                    if(intent.getIntExtra("requestCode",0)==201){
                       intent.putExtra("pos",intent.getIntExtra("pos",-1));
                       obiect.setId(id);
                        intent.putExtra("obiect",obiect);
                        setResult(RESULT_OK,intent);
                        finish();
                        return;
                    }
                    //====================================================
                    intent.putExtra("obiect",obiect);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    public void init() {
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        timePicker = findViewById(R.id.timePK);
        spinner = findViewById(R.id.spinner);
        btnSave = findViewById(R.id.btnSave);
        List<String> spinnerList=new ArrayList<>();
        spinnerList.add("cumparaturi");
        spinnerList.add("facturi");
        spinnerList.add("jocuri");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinnerList);
        spinner.setAdapter(arrayAdapter);

        //================================EDITARE PASTRARE DATE LA DESCHIDERE
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle==null)return;
        Expense obiect=bundle.getParcelable("keyobiect");
        if(obiect!=null)
        {
            id= obiect.getId();

            et1.setText(obiect.getName());
            et2.setText(Double.valueOf(obiect.getSum()).toString());
            if(!obiect.getStatus().equals("Done"))
            {
                radioButton1.toggle();
                radioButton2.toggle();

            }
            timePicker.setCurrentHour(obiect.getTime().getHours());
            timePicker.setCurrentMinute(obiect.getTime().getMinutes());

            switch(obiect.getCategory()) {
                case "cumparaturi":
                    spinner.setSelection(0);
                    break;
                case "facturi":
                    spinner.setSelection(1);
                    break;
                case "jocuri":
                    spinner.setSelection(2);
                    break;
            }
        }
    }


    public boolean valid()
    {

        if (et1 == null || et1.getText().toString() == null || et1.getText().toString().trim().length() < 3) {
            return false;
        }
        if (et2 == null || et2.getText().toString() == null || Double.parseDouble(et2.getText().toString()) <= 0) {
            return false;
        }
        if(radioGroup==null ||radioButton1==null ||radioButton2==null ||spinner==null)
            return false;

        return timePicker != null;
    }

}