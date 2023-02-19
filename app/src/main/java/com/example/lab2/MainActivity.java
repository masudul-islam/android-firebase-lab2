package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.lab2.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    // instance variables
    ActivityMainBinding binding;
    FirebaseDatabase database;
    DatabaseReference myRef;

    ProgressDialog progressDialog;

    // Create onCreate method and initialize objects (binding, database, myRef)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("lab2");


        //Initialize the View
        initView();

    }

    // calling initView method from onCreate to set the view and initialize the event listener for switches
    private void initView() {

      /*  binding.clParent.setVisibility(View.INVISIBLE);
        binding.pb.setVisibility(View.VISIBLE);*/

        progressDialog = new ProgressDialog(this);

        // ProgressDialog shows a loading indicator while the data is being fetched from the Firebase database
        progressDialog.setMessage("Loding Data..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // myRef.get() method is called to retrieve data from the Firebase database
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                // getValue method is called on it to get the data as an instance of the "Home" class
                Home home = dataSnapshot.getValue(Home.class);
                if (home.obj1){

                    binding.swObj1.setChecked(true);
                    binding.img1.setImageDrawable(getDrawable(R.drawable.lightbulbon));
                    binding.tvObj1State.setText(getString(R.string.object1on));

                }
                if (home.obj2){

                    binding.swObj2.setChecked(true);
                    binding.img2.setImageDrawable(getDrawable(R.drawable.dooropen));
                    binding.tvObj2State.setText(getString(R.string.object2on));

                }
                if (home.obj3){

                    binding.swObj3.setChecked(true);
                    binding.img3.setImageDrawable(getDrawable(R.drawable.windowopen));
                    binding.tvObj3State.setText(getString(R.string.object3on));

                }


                progressDialog.dismiss();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();

            }
        });


        binding.swObj1.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                binding.img1.setImageDrawable(getDrawable(R.drawable.lightbulbon));
                binding.tvObj1State.setText(getString(R.string.object1on));
            } else {
                binding.img1.setImageDrawable(getDrawable(R.drawable.lightbulboff));
                binding.tvObj1State.setText(getString(R.string.object1off));
            }
            // myRef.child() method is called to update the state boolean variable in the Firebase database (0bj1)
            myRef.child("obj1").setValue(isChecked);


        });

        binding.swObj2.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                binding.img2.setImageDrawable(getDrawable(R.drawable.dooropen));
                binding.tvObj2State.setText(getString(R.string.object2on));
            } else {
                binding.img2.setImageDrawable(getDrawable(R.drawable.doorclose));
                binding.tvObj2State.setText(getString(R.string.object2off));
            }

            // myRef.child() method is called to update the state boolean variable in the Firebase database (0bj2)
            myRef.child("obj2").setValue(isChecked);

        });

        binding.swObj3.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                binding.img3.setImageDrawable(getDrawable(R.drawable.windowopen));
                binding.tvObj3State.setText(getString(R.string.object3on));
            } else {
                binding.img3.setImageDrawable(getDrawable(R.drawable.windowclose));
                binding.tvObj3State.setText(getString(R.string.object3off));
            }

            // myRef.child() method is called to update the state boolean variable in the Firebase database (0bj3)
            myRef.child("obj3").setValue(isChecked);
        });

    }
}