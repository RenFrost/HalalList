package com.example.scihalallist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IngredientRVAdapter extends RecyclerView.Adapter<IngredientRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<IngredientRVModel> ingredientRVModalArrayList;
    private Context context;
    private IngredientClickInterface ingredientClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public IngredientRVAdapter(ArrayList<IngredientRVModel> ingredientRVModalArrayList, Context context, IngredientClickInterface ingredientClickInterface) {
        this.ingredientRVModalArrayList = ingredientRVModalArrayList;
        this.context = context;
        this.ingredientClickInterface = ingredientClickInterface;
    }

    @NonNull
    @Override
    public IngredientRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_rv_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull IngredientRVAdapter.ViewHolder holder, int position) {
        // setting data to our recycler view item on below line.
        IngredientRVModel ingredientRVModel = ingredientRVModalArrayList.get(position);
        holder.ingredientTV.setText(ingredientRVModel.getIngredientName());
        holder.certificateIdTV.setText(ingredientRVModel.getIngredientCertificateId());
        holder.ingredientDescriptionTV.setText(ingredientRVModel.getIngredientDescription());
        Picasso.get().load(ingredientRVModel.getIngredientImg()).into(holder.ingredientIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.ingredientIV.setOnClickListener(v -> ingredientClickInterface.onIngredientClick(position));
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return ingredientRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView ingredientIV;
        private TextView ingredientTV;
        private TextView certificateIdTV;
        private TextView ingredientDescriptionTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            ingredientIV = itemView.findViewById(R.id.idIVIngredient);
            ingredientTV = itemView.findViewById(R.id.idTVIngredientName);
            certificateIdTV = itemView.findViewById(R.id.idTVIngredientCertificateId);
            ingredientDescriptionTV = itemView.findViewById(R.id.idTVIngredientDescription);
        }
    }

    // creating a interface for on click
    public interface IngredientClickInterface {
        void onIngredientClick(int position);
    }
}
