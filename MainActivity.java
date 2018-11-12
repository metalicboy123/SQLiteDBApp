package com.example.admin.sqlitedbapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etStdntID,etStdntName,etStdntProg;
        Button btAdd,btDelete,btSearch,btView;
        etStdntID = (EditText)findViewById(R.id.stdnt_id);
        etStdntName = (EditText)findViewById(R.id.stdnt_name);
        etStdntProg = (EditText)findViewById(R.id.stdnt_program);
        btAdd = (Button)findViewById(R.id.add);
        btDelete = (Button)findViewById(R.id.delete);
        btSearch = (Button)findViewById(R.id.search);
        btView = (Button)findViewById(R.id.view);
        final SQLiteDatabase db;
        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE,null);
        db.execSQL("Create table if not exists student(stdnt_id varchar, stdnt_name varchar, stdnt_prog varchar);");
        etStdntID.requestFocus();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    db.execSQL("Insert into student values('"+etStdntID.getText().toString()+"','"+etStdntName.getText().toString()+"','"+etStdntProg.getText().toString()+"');");
                    showMessage("Success","Record Added");
                    clearText();
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Cursor c = db.rawQuery("Select * from student where stdnt_id = '"+etStdntID.getText().toString()+"'",null);
            if(c.moveToFirst()){
                db.execSQL("Delete from student where stdnt_id ='"+etStdntID.getText().toString()+"'");
                showMessage("Success","Record Deleted");
            }
            clearText();
            }
        });
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Cursor c = db.rawQuery("Select * from student where stdnt_id = '"+etStdntID.getText().toString()+"'",null);
        StringBuffer buffer = new StringBuffer();
        if(c.moveToFirst()){
            buffer.append("Name :" + c.getString(1) + "\n");
            buffer.append("Program :" + c.getString(2) + "\n\n");
        }
        showMessage("Students Details :" , buffer.toString());
            }
        });

        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.rawQuery("Select * from student ",null);
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("ID :" + c.getString(0) + "\n");
                    buffer.append("Name :" + c.getString(1) + "\n");
                    buffer.append("Program :" + c.getString(2) + "\n\n");
                }
                showMessage("Students Details :" , buffer.toString());
            }
        });
    }
    public void showMessage(String title, String message){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myScrollView = inflater.inflate(R.layout.scroll_layout, null, false);

        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setCancelable(true);
        builer.setTitle(title);
        builer.setMessage(message);
        builer.show();


    }
    public void clearText(){
        EditText etStdntID,etStdntName,etStdntProg;
        etStdntID = (EditText)findViewById(R.id.stdnt_id);
        etStdntName = (EditText)findViewById(R.id.stdnt_name);
        etStdntProg = (EditText)findViewById(R.id.stdnt_program);
        etStdntID.setText("");
        etStdntName.setText("");
        etStdntProg.setText("");
    }

    @Override
    public void onClick(View view) {

    }
}
