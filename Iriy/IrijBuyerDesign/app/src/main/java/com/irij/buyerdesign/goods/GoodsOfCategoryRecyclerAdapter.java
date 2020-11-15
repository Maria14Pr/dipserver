package com.irij.buyerdesign.goods;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.irij.buyerdesign.R;
import com.irij.buyerdesign.model.GoodModel;

import java.util.ArrayList;

public class GoodsOfCategoryRecyclerAdapter extends RecyclerView.Adapter<GoodsOfCategoryRecyclerAdapter.ViewHolderGoodsOfCat>{

    private static final String TAG = "GOCRecAdapter";
    private OnGoodClickListener mOnGoodClickListener;
    private ArrayList<GoodModel> goodsOfCatArray;
    private Context mContext;
    private int [] goodsQuantity;

    public GoodsOfCategoryRecyclerAdapter ( Context context,
            ArrayList<GoodModel> goodsOfCatArray, int[] goodsQuantity, OnGoodClickListener onGoodClickListener) {
        this.goodsOfCatArray = goodsOfCatArray;
        this.mOnGoodClickListener = onGoodClickListener;
        this.mContext = context;
        this.goodsQuantity = goodsQuantity;
    }

    @NonNull
    @Override
    public ViewHolderGoodsOfCat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cats_good_list_item, parent, false);
        return new ViewHolderGoodsOfCat(view, mOnGoodClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderGoodsOfCat holder, int position) {
        try{

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.ic_default_good);

            Glide.with(mContext)
                    .load(goodsOfCatArray.get(position).getGoodPhoto())
                    .apply(requestOptions)
                    .into(holder.ivGoodPicture);

            String pricePerUnit = "";
            pricePerUnit += goodsOfCatArray.get(position).getGoodPrice()+" руб / "+goodsOfCatArray.get(position).getUnitName();
            holder.tvGoodName.setText(goodsOfCatArray.get(position).getGoodName());
            holder.tvFarmerName.setText(goodsOfCatArray.get(position).getGoodFarmerName());
            holder.tvPricePerUnit.setText(pricePerUnit);
            holder.tvCountOfGood.setText(String.valueOf(goodsQuantity[position]));

            final int currentCount = Integer.parseInt(holder.tvCountOfGood.getText().toString());
            Log.e(TAG, "Количество "+currentCount);
            final int[] newCount = {0};

        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return goodsOfCatArray.size();
    }

    public class ViewHolderGoodsOfCat extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvGoodName, tvCountOfGood, tvFarmerName, tvPricePerUnit;
        ImageView ivGoodPicture;
        Button btnAddToCart, btnPlus, btnMinus;
        OnGoodClickListener mOnGoodClickListener;

        public ViewHolderGoodsOfCat(@NonNull View itemView, final OnGoodClickListener onGoodClickListener) {
            super(itemView);
            tvGoodName = itemView.findViewById(R.id.tv_cg_name_good);
            tvCountOfGood = itemView.findViewById(R.id.textViewCount);
            tvFarmerName = itemView.findViewById(R.id.tv_cg_farmer);
            tvPricePerUnit = itemView.findViewById(R.id.tv_cg_price);
            ivGoodPicture = itemView.findViewById(R.id.iv_good_image);
            btnMinus = itemView.findViewById(R.id.bt_goc_minus);
            btnPlus = itemView.findViewById(R.id.bt_goc_plus);
            btnAddToCart = itemView.findViewById(R.id.bt_add_to_cart);
            mOnGoodClickListener = onGoodClickListener;

            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onGoodClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onGoodClickListener.onMinusClick(position);
                        }
                    }
                }
            });

            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onGoodClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onGoodClickListener.onPlusClick(position);
                        }
                    }
                }
            });

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onGoodClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onGoodClickListener.onAddCartClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnGoodClickListener.onGoodClick(getAdapterPosition());
        }
/*
         holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCount>1){
                    try {
                        int position = holder.getAdapterPosition();
                        newCount[0] = setGoodCount(currentCount, false);
                        holder.tvCountOfGood.setText(String.valueOf(newCount[0]));
                    } catch (Exception exp) {
                        Log.e("Count error", exp.getMessage());
                    }

                }
            }
        });

            holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    newCount[0] = setGoodCount(currentCount, true);
                    holder.tvCountOfGood.setText(String.valueOf(newCount[0]));
                } catch (Exception exp){
                    Log.e("Count error", exp.getMessage());
                }

            }
        });
*/
    }

    private int setGoodCount (int currentCount, boolean add) {
        int newCount = currentCount;
        if (add) {
            newCount++;
        } else {
            newCount--;
        }
        return newCount;
    }

    public interface OnGoodClickListener{
        void onGoodClick(int position);

        void onPlusClick(int position);
        void onMinusClick(int position);
        void onAddCartClick(int position);

    }

}
