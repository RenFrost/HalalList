package com.example.scihalallist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IngredientRVAdapter.IngredientClickInterface {

    // creating variables for fab, firebase database,
    // progress bar, list, adapter,firebase auth,
    // recycler view and relative layout.
    private FloatingActionButton addIngredientFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView ingredientRV;
    private SearchView ingredientSV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private ArrayList<IngredientRVModel> ingredientRVModalArrayList;
    private IngredientRVAdapter ingredientRVAdapter;
    private RelativeLayout homeRL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing all our variables.
        ingredientRV = findViewById(R.id.idRVIngredients);
        ingredientSV = findViewById(R.id.idSVIngredient);
        homeRL = findViewById(R.id.idRLBSheet);
        loadingPB = findViewById(R.id.idPBLoading);
        addIngredientFAB = findViewById(R.id.idFABAddIngredients);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        ingredientRVModalArrayList = new ArrayList<>();
        // on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("Ingredients");
        // on below line adding a click listener for our floating action button.
        addIngredientFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity for adding a course.
                Intent i = new Intent(MainActivity.this, AddIngredientActivity.class);
                startActivity(i);
            }
        });
        // on below line initializing our adapter class.
        ingredientRVAdapter = new IngredientRVAdapter(ingredientRVModalArrayList, this, this::onIngredientClick);
        // setting layout malinger to recycler view on below line.
        ingredientRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view on below line.
        ingredientRV.setAdapter(ingredientRVAdapter);
        // on below line calling a method to fetch courses from database.
        getIngredients();
    }

    // calling on create option menu
    // layout to inflate our menu file.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // on below line we are inflating our menu
        // file for displaying our menu options.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void getIngredients() {
        // on below line clearing our list.
        ingredientRVModalArrayList.clear();
        // on below line we are calling add child event listener method to read the data.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                // adding snapshot to our array list on below line.
                ingredientRVModalArrayList.add(snapshot.getValue(IngredientRVModel.class));
                // notifying our adapter that data has changed.
                ingredientRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                loadingPB.setVisibility(View.GONE);
                ingredientRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                ingredientRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is moved.
                ingredientRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onIngredientClick(int position) {
        // calling a method to display a bottom sheet on below line.
        displayBottomSheet(ingredientRVModalArrayList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // adding a click listener for option selected on below line.
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                // displaying a toast message on user logged out inside on click.
                Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_LONG).show();
                // on below line we are signing out our user.
                mAuth.signOut();
                // on below line we are opening our login activity.
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void displayBottomSheet(IngredientRVModel modal) {
        // on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        // on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        // setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        TextView ingredientNameTV = layout.findViewById(R.id.idTVIngredientName);
        TextView productionByTV = layout.findViewById(R.id.idTVProductionBy);
        TextView ingredientCertificateIdTV = layout.findViewById(R.id.idTVIngredientCertificateId);
        TextView ingredientTypeTV = layout.findViewById(R.id.idTVIngredientType);
        TextView validUntilTV = layout.findViewById(R.id.idTVValidUntil);
        ImageView ingredientIV = layout.findViewById(R.id.idIVIngredient);
        // on below line we are setting data to different views on below line.
        ingredientNameTV.setText(modal.getIngredientName());
        productionByTV.setText(modal.getProductionBy());
        ingredientTypeTV.setText(modal.getIngredientType());
        ingredientCertificateIdTV.setText(modal.getIngredientCertificateId());
        validUntilTV.setText(modal.getValidUntil());
        Picasso.get().load(modal.getIngredientImg()).into(ingredientIV);
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditIngredient);

        // adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening our EditCourseActivity on below line.
                Intent i = new Intent(MainActivity.this, EditIngredientActivity.class);
                // on below line we are passing our course modal
                i.putExtra("ingredient", modal);
                startActivity(i);
            }
        });
        // adding click listener for our view button on below line.
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are navigating to browser
                // for displaying course details from its url
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(modal.getIngredientImg()));
                startActivity(i);
            }
        });
    }
}