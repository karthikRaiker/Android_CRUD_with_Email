package com.example.emaill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Dbhandler myDb;
    EditText editName,editCompany,editModel,editTextId;
    Button btnAddData,btnviewAll,btnDelete,btnviewUpdate,btnEmail;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new Dbhandler(this);
        editName=findViewById(R.id.editText_name);
        editCompany=findViewById(R.id.editText_company);
        editModel=findViewById(R.id.editText_Model);
        editTextId=findViewById(R.id.editText_id);
        btnAddData=findViewById(R.id.button_add);
        btnviewAll=findViewById(R.id.btton_viewAll);
        btnviewUpdate=findViewById(R.id.button_update);
        btnDelete=findViewById(R.id.button_delete);
        btnEmail=findViewById(R.id.button_email);
        AddData();
        ViewALL();
        UpdateData();
        DeleteData();
        Email();
        counter ++;
        editTextId.setText(Integer.toString(counter));
    }

    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=myDb.insertData(editName.getText().toString(),editCompany.getText().toString(),editModel.getText().toString());
                counter ++;
                editTextId.setText(Integer.toString(counter));
                editCompany.setText("");
                editName.setText("");
                editModel.setText("");
            }
        });
    }
    private void Email(){

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent=new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                final PackageManager pm=getPackageManager();
                final List<ResolveInfo>matches=pm.queryIntentActivities(intent,0);
                ResolveInfo best=null;
                for (final ResolveInfo info:matches)
                    if(info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))best=info;
                if(best !=null)
                    intent.setClassName(best.activityInfo.packageName,best.activityInfo.name);
                startActivity(intent);
            }
        });
    }
    public void ViewALL(){
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter --;
                Cursor res=myDb.getALLDATA();
                if(res.getCount()==0){
                    showMessage("Error","NOthing found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id:"+res.getString(0)+"\n");
                    buffer.append("Name"+res.getString(1)+"\n");
                    buffer.append("Company"+res.getString(2)+"\n");
                    buffer.append("Model:"+res.getString(3)+"\n\n");
                }
                showMessage("data",buffer.toString());

            }
        });

    }
    public void UpdateData(){
        btnviewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=myDb.updateData(editTextId.getText().toString(),editName.getText().toString(),editCompany.getText().toString(),editModel.getText().toString());
                editCompany.setText("");
                editName.setText("");
                editModel.setText("");
                if(isUpdate==true)
                    Toast.makeText(MainActivity.this,"Database Update",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Cannot be updated",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextId.setEnabled(true);
                Integer deletedRows=myDb.deleteData(editTextId.getText().toString());
                editCompany.setText("");
                editName.setText("");
                editModel.setText("");
                editTextId.setEnabled(false);
                editTextId.setText(Integer.toString(counter));
                if(deletedRows>0)
                    Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data cannot be Deleted",Toast.LENGTH_LONG).show();;
            }
        });

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
