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

import java.util.Objects;

public class AddIngredientActivity extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addIngredientBtn;
    private TextInputEditText ingredientNameEdt, ingredientTypeEdt, ingredientImgEdt, ingredientCertificateIdEdt, validUntilEdt, productionByEdt, ingredientDescEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String ingredientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        // initializing all our variables.
        addIngredientBtn = findViewById(R.id.idBtnAddIngredient);
        ingredientNameEdt = findViewById(R.id.idEdtIngredientName);
        ingredientTypeEdt = findViewById(R.id.idEdtIngredientType);
        ingredientImgEdt = findViewById(R.id.idEdtIngredientImageLink);
        ingredientCertificateIdEdt = findViewById(R.id.idEdtIngredientCertificateId);
        validUntilEdt = findViewById(R.id.idEdtValidUntil);
        productionByEdt = findViewById(R.id.idEdtProductionBy);
        ingredientDescEdt = findViewById(R.id.idEdtIngredientDescription);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Ingredients");
        // adding click listener for our add course button.
        addIngredientBtn.setOnClickListener(v -> {
            loadingPB.setVisibility(View.VISIBLE);
            // getting data from our edit text.
            String ingredientName = Objects.requireNonNull(ingredientNameEdt.getText()).toString();
            String ingredientType = Objects.requireNonNull(ingredientTypeEdt.getText()).toString();
            String ingredientImg = Objects.requireNonNull(ingredientImgEdt.getText()).toString();
            String ingredientCertificateId = Objects.requireNonNull(ingredientCertificateIdEdt.getText()).toString();
            String validUntil = Objects.requireNonNull(validUntilEdt.getText()).toString();
            String productionBy = Objects.requireNonNull(productionByEdt.getText()).toString();
            String ingredientDesc = Objects.requireNonNull(ingredientDescEdt.getText()).toString();
            ingredientID = ingredientName;
            // on below line we are passing all data to our modal class.
            IngredientRVModel ingredientRVModel = new IngredientRVModel(ingredientID, ingredientName, ingredientType, ingredientImg, ingredientCertificateId, validUntil, productionBy, ingredientDesc);
            // on below line we are calling a add value event
            // to pass data to firebase database.
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // on below line we are setting data in our firebase database.
                    databaseReference.child(ingredientID).setValue(ingredientRVModel);
                    // displaying a toast message.
                    Toast.makeText(AddIngredientActivity.this, "Ingredient Added..", Toast.LENGTH_SHORT).show();
                    // starting a main activity.
                    startActivity(new Intent(AddIngredientActivity.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // displaying a failure message on below line.
                    Toast.makeText(AddIngredientActivity.this, "Fail to add Ingredient..", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}