package com.example.firebaselistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuLayout;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowData extends AppCompatActivity {
    SwipeMenuListView listView;
    List<ModelClass> list_array;
    DatabaseReference reference;
    AdapterClass adapterClass;

    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        list_array=new ArrayList<>();
        adapterClass=new AdapterClass(this,list_array);

        reference= FirebaseDatabase.getInstance().getReference("Blood Man");
        query=reference.orderByChild("bloodGroup").equalTo("A+");

        listView=findViewById(R.id.listView);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_array.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ModelClass modelClass=snapshot.getValue(ModelClass.class);
                    list_array.add(modelClass);
                }

                listView.setAdapter(adapterClass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(200);
                // set item title
                openItem.setIcon(R.drawable.ic_baseline_edit_24);
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_baseline_delete_24);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };


// set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ModelClass modelClass=list_array.get(position);
                switch (index) {
                    case 0:
                        showUpdateDialuge(modelClass.getId(),position);
                        break;
                    case 1:
                        reference=FirebaseDatabase.getInstance().getReference("Blood Man").child(modelClass.getId());
                        reference.removeValue();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }

    private void showUpdateDialuge(String id,int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialouge_layout,null,false);
        builder.setView(view);

        AlertDialog alertDialog=builder.show();

        EditText nameEdit=view.findViewById(R.id.updateName);
        EditText phoneEdit=view.findViewById(R.id.updatePhone);
        Spinner bloodSpinner=view.findViewById(R.id.updatespnner);
        Button button=view.findViewById(R.id.updatesaveBtn_ID);

        ModelClass modelClass=list_array.get(position);
        nameEdit.setText(modelClass.getName());
        phoneEdit.setText(modelClass.getPhoneNumber());
        String[] bloodgroup=getResources().getStringArray(R.array.Blood_groups);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,bloodgroup);

        bloodSpinner.setAdapter(arrayAdapter);

        int x=arrayAdapter.getPosition(modelClass.getBloodGroup());

        bloodSpinner.setSelection(x);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name=nameEdit.getText().toString();
                //String phoneNumber=phoneEdit.getText().toString();
                //String bloodGroup=bloodSpinner.getSelectedItem().toString();

                reference=FirebaseDatabase.getInstance().getReference("Blood Man").child(id);
                ModelClass modelClass=new ModelClass(id,nameEdit.getText().toString(),phoneEdit.getText().toString(),bloodSpinner.getSelectedItem().toString());
                reference.setValue(modelClass);

                Toast.makeText(ShowData.this, "data updated", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });


    }
}