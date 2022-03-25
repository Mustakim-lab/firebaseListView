package com.example.firebaselistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText nameEdit,phoneEdit;
    Spinner blood_group;
    Button save,show;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit=findViewById(R.id.DonnerNamee);
        phoneEdit=findViewById(R.id.DonnerPhone);
        blood_group=findViewById(R.id.spnner);
        save=findViewById(R.id.saveBtn_ID);
        show=findViewById(R.id.saveShow_ID);

        reference= FirebaseDatabase.getInstance().getReference("Blood Man");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEdit.getText().toString();
                String phoneNumber=phoneEdit.getText().toString();
                String bloodGroup=blood_group.getSelectedItem().toString();
                String id=reference.push().getKey();

                ModelClass modelClass=new ModelClass(id,name,phoneNumber,bloodGroup);

                reference.child(id).setValue(modelClass);

                Toast.makeText(MainActivity.this, "Save Data", Toast.LENGTH_SHORT).show();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowData.class);
                startActivity(intent);
            }
        });
    }
}