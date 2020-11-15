package com.irij.buyerdesign.goods;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irij.buyerdesign.R;
import com.irij.buyerdesign.model.CategoryModel;

import java.util.ArrayList;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolderAllCats>{

    private static final String TAG = "CatRecyclerAdapter";

    private ArrayList<CategoryModel> mCategories;
    private OnCatListener mOnCatListener;

    public CategoriesRecyclerAdapter(ArrayList<CategoryModel> catArray, OnCatListener onCatListener) {
        this.mCategories = catArray;
        this.mOnCatListener = onCatListener;
    }

    @NonNull
    @Override
    public ViewHolderAllCats onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_list_row, parent, false);
        return new ViewHolderAllCats(view, mOnCatListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAllCats holder, int position) {

        try{
            String nameOfCat = mCategories.get(position).getCategoryName();
            holder.nameTV.setText(nameOfCat);
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage() );
        }

    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolderAllCats extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTV;
        OnCatListener mOnCatListener;

        public ViewHolderAllCats(View itemView, OnCatListener onCatListener) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.tv_cat_name);
            //title = itemView.findViewById(R.id.note_title);
            mOnCatListener = onCatListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnCatListener.onCatClick(getAdapterPosition());
        }
    }

    public interface OnCatListener{
        void onCatClick(int position);
    }

}
