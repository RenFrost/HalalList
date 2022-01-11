package com.example.scihalallist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditIngredientActivity extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    private TextInputEditText ingredientNameEdt, ingredientDescEdt, ingredientTypeEdt, ingredientCertificateIdEdt, ingredientImgEdt, validUntilEdt, productionByEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    IngredientRVModel ingredientRVModel;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String ingredientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient);
        // initializing all our variables on below line.
        Button editIngredientBtn = findViewById(R.id.idBtnEditIngredient);
        ingredientNameEdt = findViewById(R.id.idEdtIngredientName);
        ingredientDescEdt = findViewById(R.id.idEdtIngredientDescription);
        ingredientTypeEdt = findViewById(R.id.idEdtIngredientType);
        ingredientCertificateIdEdt = findViewById(R.id.idEdtIngredientCertificateId);
        ingredientImgEdt = findViewById(R.id.idEdtIngredientImageLink);
        validUntilEdt = findViewById(R.id.idEdtValidUntil);
        productionByEdt = findViewById(R.id.idEdtProductionBy);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        ingredientRVModel = getIntent().getParcelableExtra("ingredient");
        Button deleteIngredientBtn = findViewById(R.id.idBtnDeleteIngredient);

        if (ingredientRVModel != null) {
            // on below line we are setting data to our edit text from our modal class.
            ingredientNameEdt.setText(ingredientRVModel.getIngredientName());
            ingredientDescEdt.setText(ingredientRVModel.getIngredientDescription());
            ingredientTypeEdt.setText(ingredientRVModel.getIngredientType());
            ingredientImgEdt.setText(ingredientRVModel.getIngredientImg());
            ingredientCertificateIdEdt.setText(ingredientRVModel.getIngredientCertificateId());
            validUntilEdt.setText(ingredientRVModel.getValidUntil());
            productionByEdt.setText(ingredientRVModel.getProductionBy());
            ingredientID = ingredientRVModel.getIngredientID();
        }

        // on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Ingredients").child(ingredientID);
        // on below line we are adding click listener for our add course button.
        editIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String ingredientName = Objects.requireNonNull(ingredientNameEdt.getText()).toString();
                String ingredientDesc = Objects.requireNonNull(ingredientDescEdt.getText()).toString();
                String ingredientType = Objects.requireNonNull(ingredientTypeEdt.getText()).toString();
                String ingredientCertificateId = Objects.requireNonNull(ingredientCertificateIdEdt.getText()).toString();
                String validUntil = Objects.requireNonNull(validUntilEdt.getText()).toString();
                String productionBy = Objects.requireNonNull(productionByEdt.getText()).toString();
                String ingredientImg = Objects.requireNonNull(ingredientImgEdt.getText()).toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("ingredientName", ingredientName);
                map.put("ingredientDescription", ingredientDesc);
                map.put("ingredientType", ingredientType);
                map.put("ingredientCertificateID", ingredientCertificateId);
                map.put("validUntil", validUntil);
                map.put("ingredientImg", ingredientImg);
                map.put("productionBy", productionBy);
                map.put("ingredientID", ingredientID);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditIngredientActivity.this, "Ingredient Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditIngredientActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditIngredientActivity.this, "Fail to update ingredient..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete course button.
        deleteIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteIngredient();
            }
        });

    }

    private void deleteIngredient() {
        // on below line calling a method to delete the course.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Ingredient Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditIngredientActivity.this, MainActivity.class));
    }
}