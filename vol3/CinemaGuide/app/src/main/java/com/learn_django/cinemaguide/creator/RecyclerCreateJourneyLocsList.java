package com.learn_django.cinemaguide.creator;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn_django.cinemaguide.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerCreateJourneyLocsList extends RecyclerView.Adapter<RecyclerCreateJourneyLocsList.ViewHolder> {

    private static final String TAG = "RecyclerProfileListAdapter";

    private boolean[] checks;

    private ArrayList<Integer> idLocs = new ArrayList<>();
    private ArrayList<Float> costLocs = new ArrayList<>();
    private ArrayList<String> currencyLocs = new ArrayList<>();
    private ArrayList<String> nameRusLocs = new ArrayList<>();
    private ArrayList<String> nameOrigLocs = new ArrayList<>();
    private ArrayList<String> countryLocs = new ArrayList<>();
    private ArrayList<String> photoLocs = new ArrayList<>();

    private Context mContext;

    private List<Integer> selectedItemsId;
    private List<String> selectedItemsNames;
    private int current_selected_idx = -1;

    private OnLocationListener mOnLocListener = null;



    public RecyclerCreateJourneyLocsList(Context context, ArrayList<Integer> idLocsL, ArrayList<String> nameRusLocsL,
                                         ArrayList<String> nameOrigLocsL, ArrayList<String> countryLocsL,
                                         ArrayList<String> photoLocsL, ArrayList<Float> costLocsL, ArrayList<String> currencyLocsL) {
        this.idLocs = idLocsL;
        this.nameRusLocs = nameRusLocsL;
        this.photoLocs = photoLocsL;
        this.costLocs = costLocsL;
        this.nameOrigLocs = nameOrigLocsL;
        this.countryLocs = countryLocsL;
        this.currencyLocs = currencyLocsL;


        mContext = context;

        selectedItemsId = new ArrayList<Integer>();
        checks = new boolean[this.idLocs.size()];

        //selectedItems = new ArrayList<Integer> ();
    }

    @Override
    public RecyclerCreateJourneyLocsList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jc_locations_list_item, parent, false);
        return new ViewHolder(view, mOnLocListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerCreateJourneyLocsList.ViewHolder holder, final int position) {

        holder.tvCJLlNameRus.setText(nameRusLocs.get(position));
        holder.tvCJLlNameOrig.setText(nameOrigLocs.get(position));
        holder.tvCJLlCountry.setText(countryLocs.get(position));
        holder.tvCJLlCost.setText(String.valueOf(costLocs.get(position)));
        holder.tvCJLlCurrency.setText(String.valueOf(currencyLocs.get(position)));

        //holder.parentCJLocsCv.setActivated(selectedItems.get(position, false));

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_main_icon_foreground);

        Glide.with(mContext)
                .load(photoLocs.get(position))
                .apply(requestOptions)
                .into(holder.ivCJLlPhoto);


        holder.parentCJLocsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checkChecks(position);
                //if(checks[position]) {

                   // toggleCheckedIcon(holder, position);
                if (mOnLocListener == null) return;
                mOnLocListener.OnLocationClick(v, position);

            }

        });

        toggleCheckedIcon(holder, position);
    }


    private void checkChecks(int position) {

        notifyItemChanged(position); //обновляем. да, так надо, не знаю почему
        checks[position] = true; //меняем значение в массиве по позиции нажатого айтема
        notifyItemChanged(position);

    }

    private void toggleCheckedIcon(ViewHolder holder, int position) {

        if (!selectedItemsId.contains(idLocs.get(position))) {
            holder.rlLocItemChecked.setVisibility(View.GONE);
            holder.cjLocPhotoHolder.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        } else {
            holder.cjLocPhotoHolder.setVisibility(View.GONE);
            holder.rlLocItemChecked.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        }
    }


    public int getSelectedItemCount() {
        Log.e("количество локаций ", "на экспорт "+String.valueOf(selectedItemsId.size()));
        return selectedItemsId.size();
    }

    public ArrayList<Integer> getSelectedItems() {
        ArrayList<Integer> items = new ArrayList<>(selectedItemsId.size());
        for (int i = 0; i < selectedItemsId.size(); i++) {
            items.add(selectedItemsId.get(i));
        }
        return items;
    }


    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    public void toggleSelection(int position) {
        current_selected_idx = position;
        if (selectedItemsId.contains(idLocs.get(position))){
            selectedItemsId.remove(idLocs.get(position));
            Log.e("удален фильм", nameRusLocs.get(position));
            Log.e("количество фильмов ", String.valueOf(selectedItemsId.size()));
        }
        else {

            selectedItemsId.add(idLocs.get(position));
            Log.e("добавлена локация ", nameRusLocs.get(position));
            Log.e("количество локаций ", String.valueOf(selectedItemsId.size()));
        }
        notifyItemChanged(position);
    }

    public void setOnClickListener(OnLocationListener onClickListener) {
        this.mOnLocListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return idLocs.size();
    }

    public interface OnLocationListener {
        void OnLocationClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener {

        TextView tvCJLlNameRus;
        TextView tvCJLlNameOrig;
        TextView tvCJLlCountry;
        TextView tvCJLlCost;
        TextView tvCJLlCurrency;
        CardView parentCJLocsCv;
        CardView cjLocPhotoHolder;
        ImageView ivCJLlPhoto;

        RelativeLayout rlLocItemChecked;

        OnLocationListener onLocationListenerListener;

        public ViewHolder(View itemView, OnLocationListener onLocationListener) {
            super(itemView);
            tvCJLlNameRus = itemView.findViewById(R.id.tv_cj_ll_name_rus);
            rlLocItemChecked = itemView.findViewById(R.id.rl_loc_checked);
            tvCJLlNameOrig = itemView.findViewById(R.id.tv_cj_ll_name_orig);
            tvCJLlCountry = itemView.findViewById(R.id.tv_cj_ll_country);
            tvCJLlCost = itemView.findViewById(R.id.tv_cj_ll_cost);
            tvCJLlCurrency = itemView.findViewById(R.id.tv_cj_ll_cur);
            ivCJLlPhoto = itemView.findViewById(R.id.iv_cj_locs_list_photo);
            cjLocPhotoHolder = itemView.findViewById(R.id.cv_cj_lliv);
            parentCJLocsCv = itemView.findViewById(R.id.cv_cj_parent_locs);

            this.onLocationListenerListener = onLocationListener;

        }
    }
}
