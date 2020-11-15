package com.irij.buyerdesign.goods;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irij.buyerdesign.ApiRest;
import com.irij.buyerdesign.R;
import com.irij.buyerdesign.model.CategoryModel;
import com.irij.buyerdesign.model.FarmerNameForGoodModel;
import com.irij.buyerdesign.model.GoodModel;
import com.irij.buyerdesign.model.MediaModel;
import com.irij.buyerdesign.model.UnitForGoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoodsOfCatFragment extends Fragment implements GoodsOfCategoryRecyclerAdapter.OnGoodClickListener{

    private static final String TAG = "GoodsOfCatActivity";
    private TextView tvCatName, tvGoodsCount;
    private RecyclerView rvGoodsOfCat;
    private ProgressBar pbLoadCatGoods;
    private int catId;
    private String catName, finishOfCount = " товаров";
    private int [] currentGoodsQuantity;

    private ArrayList<GoodModel> mGoodsOfCategory = new ArrayList<>();
    private GoodsOfCategoryRecyclerAdapter mGoodsOfCategoryRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View goodsOfCatsView = inflater.inflate(R.layout.fragment_cats_goods, container, false);
        Log.e(TAG, "--------------------------");

        tvCatName = goodsOfCatsView.findViewById(R.id.tv_det_cat_name);
        rvGoodsOfCat = goodsOfCatsView.findViewById(R.id.rv_goods_of_cat);
        pbLoadCatGoods = goodsOfCatsView.findViewById(R.id.pb_for_cat);
        tvGoodsCount = goodsOfCatsView.findViewById(R.id.tv_goods_count);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            catId = bundle.getInt("category_id");
            catName = bundle.getString("category_name");

            Log.d ("from cat list", catId +" "+catName);
            tvCatName.setText(catName);

            initGoodsOfCatRecyclerView();

            showGoodsOfCategory(catId, catName);

            //solvedLocCost = bundle.getFloatArray("solved_locs_cost");
            //solvedLocsCur = bundle.getStringArrayList("solved_locs_cur");
            //solvedDistancesArray = bundle.getDoubleArray("solved_locs_distances");
        }


        return goodsOfCatsView;
    }

    private void showGoodsOfCategory ( int catId, String catName) {

       //final ProgressDialog loading = ProgressDialog.show(getContext(), "Получение товаров", "Пожалуйста, подождите...", false, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRest.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRest apiRest= retrofit.create(ApiRest.class);
        Call<List<GoodModel>> call = apiRest.getGoodsOfCategory(String.valueOf(catId));

        Log.e(TAG, call.toString());

        call.enqueue(new Callback<List<GoodModel>>() {

            @Override
            public void onResponse(Call<List<GoodModel>> call, Response<List<GoodModel>> response) {

                Log.e("response body", response.toString());

                /*if (response.isSuccessful()&& response.body()!=null){

                    pbLoadCatGoods.setVisibility(View.GONE);

                    try {

                        JSONArray jsonArray = new JSONArray(response.body());
                        parseResult(jsonArray);

                    } catch (Exception exp) {
                        Log.e("JSON error", exp.getMessage());
                    }


                }*/
                if (response.isSuccessful()){

                    pbLoadCatGoods.setVisibility(View.VISIBLE);

                    Log.d("response body", response.toString());

                    if (response.body() != null) {

                        Log.d("response", "is Successful");
                        List<GoodModel> receivedGoodsOfCatList = response.body();
                        Log.d("response ", response.body().toString());

                        if (receivedGoodsOfCatList.size()>0) {

                            Log.e(TAG, receivedGoodsOfCatList.size()+"размер");

                            //mGoodsOfCategory = receivedGoodsOfCatList;

                            tvGoodsCount.setText(receivedGoodsOfCatList.size()+finishOfCount);

                            currentGoodsQuantity = new int[receivedGoodsOfCatList.size()];
                            Arrays.fill(currentGoodsQuantity, 1);

                            for(GoodModel h:receivedGoodsOfCatList){

                                try {

                                    MediaModel [] mediaModels = {new MediaModel("http://iriy.online"+h.getGoodPhoto())};
                                    CategoryModel[] categoryModels = {h.getCategory()};

                                    Log.e(TAG, "картинка "+mediaModels[0].getMediaUrl());


                                    GoodModel element = new GoodModel(h.getGoodId(), h.getGoodName(), h.getGoodMinCount(), h.getCountOfGood(),
                                            h.getGoodPrice(), h.getProteins(), h.getFats(), h.getCarbohydrates(), h.getCalories(), h.getFarmerOfGoodId(),
                                            new UnitForGoodModel(h.getUnitName()), mediaModels,  categoryModels, new FarmerNameForGoodModel(h.getGoodFarmerName()));
                                    mGoodsOfCategory.add(element);

                                } catch (Exception exp) {
                                    Log.e("Ошибка!", exp.getMessage());
                                }

                                if (h==receivedGoodsOfCatList.get(receivedGoodsOfCatList.size()-1)){
                                    pbLoadCatGoods.setVisibility(View.GONE);
                                }

                            }

                            initGoodsOfCatRecyclerView();


                        }
                        else {

                            pbLoadCatGoods.setVisibility(View.GONE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("В данной категории пока нет товаров");
                            builder.setMessage("Вы можете ознакомиться с товарами в других категориях");

                            builder.setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    //deletePost();
                                    dialog.dismiss();
                                    try {
                                        Fragment myFragment = new GoodsCatListFragment();

                                        FragmentManager fragmentManager = getFragmentManager();
                                        assert fragmentManager != null;
                                        fragmentManager.beginTransaction().replace(R.id.goods_area, myFragment)
                                                .commit();
                                        Log.e(TAG, "Успешно  заменили");

                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                    }
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();

                        }





                    } else {
                        Log.e("response", "is not Successful");
                    }

                }

            }

            private void parseResult(JSONArray jsonArray) {
                for (int i=0; i<jsonArray.length();i++){
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GoodModel>> call, Throwable t) {

                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());

            }
        });


    }

    private void initGoodsOfCatRecyclerView (){

        Log.e("Cats", "initRecyclerView: init recyclerview.");

        rvGoodsOfCat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mGoodsOfCategoryRecyclerAdapter = new GoodsOfCategoryRecyclerAdapter(getContext(), mGoodsOfCategory, currentGoodsQuantity, this);

        rvGoodsOfCat.setAdapter(mGoodsOfCategoryRecyclerAdapter);
    }

    @Override
    public void onGoodClick(int position) {


    }

    @Override
    public void onPlusClick(int position) {
        currentGoodsQuantity[position]++;
        mGoodsOfCategoryRecyclerAdapter.notifyItemChanged(position);
    }

    @Override
    public void onMinusClick(int position) {
        currentGoodsQuantity[position]--;
        if (currentGoodsQuantity[position]<1){
            currentGoodsQuantity[position]=1;
        }
        mGoodsOfCategoryRecyclerAdapter.notifyItemChanged(position);
    }

    @Override
    public void onAddCartClick(int position) {

        //SharedPreferences.Editor editor = getSharedPreference

    }
}
