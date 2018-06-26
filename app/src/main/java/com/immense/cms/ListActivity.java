package com.immense.cms;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
Button listactivity,addcontact,logout;
    ArrayAdapter adapter;
ListView listView;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final Sqlitedatahandler sqlitedatahandler = new Sqlitedatahandler(getApplicationContext());
        logout = findViewById(R.id.logout);
        addcontact = findViewById(R.id.addcontact);
        listactivity= findViewById(R.id.listactivity);
        listView = findViewById(R.id.listview);
        session = new SessionManager(getApplicationContext());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLogin(false);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddContact.class);
                startActivity(i);
                finish();
            }
        });

        listactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> array = sqlitedatahandler.listContact();
                if(array.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Records Found!", Toast.LENGTH_SHORT).show();

                }
                else {
                    adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, array);
                    listView.setAdapter(adapter);
                }
            }
        });


        listactivity.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
               showDialog();
            return false;
            }

            private void showDialog() {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ListActivity.this);
                builder1.setMessage("Do you want to delete all the records");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sqlitedatahandler.deleteUsers();
                                ArrayList<String> arrayList =sqlitedatahandler.listContact();
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.activity_listview,arrayList);
                                listView.setAdapter(arrayAdapter);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


    }
}
