package com.example.a1094_selistevaleriacorina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.a1094_selistevaleriacorina.asyncTask.Callback;
import com.example.a1094_selistevaleriacorina.db.ExpenseService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addBtn;
    ListView listView;
    List<Expense> objList = new ArrayList<>();
    public static final int OPTIUNE1=0;
    boolean cresc = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addBtn);
        listView = findViewById(R.id.lv);

        //============================================================ DISPLAY OBJ FROM DB AT OPENING THE APPLICATION
        ExpenseService expenseService = new ExpenseService(this);
        expenseService.getAll(new Callback<List<Expense>>() {
            @Override
            public void runResultOnUiThread(List<Expense> result) {
                objList = result;
                ArrayAdapter<Expense> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        result);
                listView.setAdapter(adapter);
            }
        });

        //===================================================EDIT OBJ FROM LISTVIEW ON CLICK
        // REDESCHIDE ADDACTIVITY CU DATELE OBIECTULUI SELECTAT CU CLICK
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),AddActivity.class);
                id = objList.get(position).getId();
                intent.putExtra("pos",position);
                intent.putExtra("keyobiect", objList.get(position));
                intent.putExtra("requestCode",201);
                startActivityForResult(intent,201);

            }
        });


        //============================================================= DESCHIDERE AddActivity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, 200);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //===================================================================  INSERT OBJ TO DATABASE
        if (requestCode==200 && resultCode==RESULT_OK && data!=null)
        {
            Expense obiect = data.getParcelableExtra("obiect");
            if(obiect!=null)
            {
                ExpenseService expenseService=new ExpenseService(this);
                expenseService.insert(new Callback<Expense>() {
                    @Override
                    public void runResultOnUiThread(Expense result) {
                        System.out.println(result);
                        objList.add(result);
                        expenseService.getAll(new Callback<List<Expense>>() {
                            @Override
                            public void runResultOnUiThread(List<Expense> result) {
                                ArrayAdapter<Expense> adapter = new ArrayAdapter<>(getApplicationContext(),
                                        android.R.layout.simple_list_item_1,
                                        result);
                                listView.setAdapter(adapter);
                            }
                        });
                    }
                }, obiect);


            }
        }


        //===================================================EDIT OBJ FROM LISTVIEW ON CLICK
        if(requestCode==201 && resultCode==RESULT_OK && data!=null) {
            Expense obiect = data.getParcelableExtra("obiect");
            int position=data.getIntExtra("pos",-1);
            if (obiect != null) {
                ExpenseService expenseService = new ExpenseService(this);
                expenseService.update(new Callback<Expense>() {
                    @Override
                    public void runResultOnUiThread(Expense result) {
                        System.out.println(result);
                        objList.set(position,result);
                        expenseService.getAll(new Callback<List<Expense>>() {
                            @Override
                            public void runResultOnUiThread(List<Expense> result) {
                                ArrayAdapter<Expense> adapter = new ArrayAdapter<>(getApplicationContext(),
                                        android.R.layout.simple_list_item_1,
                                        result);
                                listView.setAdapter(adapter);
                            }
                        });
                    }
                }, obiect);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,OPTIUNE1,0,"Sorteaza");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        ExpenseService expenseService = new ExpenseService(getApplicationContext());

        if (cresc) {
            expenseService.getSorted(new Callback<List<Expense>>() {
                @Override
                public void runResultOnUiThread(List<Expense> result) {
                    ArrayAdapter<Expense> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            result);
                    listView.setAdapter(adapter);
                }
            });
            cresc = false;
        }
        else {
            expenseService.getSortedDesc(new Callback<List<Expense>>() {
                @Override
                public void runResultOnUiThread(List<Expense> result) {
                    ArrayAdapter<Expense> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            result);
                    listView.setAdapter(adapter);
                }
            });
            cresc = true;
        }

        return true;
    }
}