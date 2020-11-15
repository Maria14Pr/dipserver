package com.irij.buyerdesign.goods;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.irij.buyerdesign.ApiRest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irij.buyerdesign.R;
import com.irij.buyerdesign.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoodsCatListFragment extends Fragment implements CategoriesRecyclerAdapter.OnCatListener, View.OnClickListener {

    private static final String TAG = "CatsListActivity";

    // ui components
    private RecyclerView mRecyclerView;
    private Button mShowAllGoodsBtn;

    // vars
    private ArrayList<CategoryModel> mCategories = new ArrayList<>();
    private CategoriesRecyclerAdapter mCategoriesRecyclerAdapter;
    //private NoteRepository mNoteRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View categoriesListRootView = inflater.inflate(R.layout.fragment_all_goods_menu, container, false);

        mRecyclerView = categoriesListRootView.findViewById(R.id.rv_all_cats);
        mShowAllGoodsBtn = categoriesListRootView.findViewById(R.id.btn_show_all_goods);
        mShowAllGoodsBtn.setOnClickListener(this);

        showAllCats();

        Log.e(TAG, "created");

        return categoriesListRootView;
    }

    private void showAllCats() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRest.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRest apiRest= retrofit.create(ApiRest.class);
        Call<List<CategoryModel>> call = apiRest.getCategoryList();

        Log.e(TAG, call.toString());

        call.enqueue(new Callback<List<CategoryModel>>() {

            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {

                Log.e("response body", response.toString());
                if (response.isSuccessful()){

                    Log.d("response body", response.toString());

                    final ProgressDialog loading = ProgressDialog.show(getContext(), "Получение категорий", "Пожалуйста, подождите...", false, false);

                    if (response.body() != null) {
                        Log.d("response", "is Successful");
                        List<CategoryModel> receivedCatList = response.body();

                        for(CategoryModel h:receivedCatList){

                            try {

                                Integer cat_id = h.getCategoryId();
                                String cat_name = h.getCategoryName();
                                if (cat_name.length()>20) {
                                    cat_name = cat_name.substring(0,19)+"...";
                                }
                                CategoryModel element = new CategoryModel(cat_id, cat_name);
                                mCategories.add(element);

                            } catch (Exception exp) {
                                Log.e("Ошибка!", exp.getMessage());
                            }

                        }

                        initCatRecyclerView();
                        loading.dismiss();

                    } else {
                        Log.e("response", "is not Successful");
                    }

                }

            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());

            }
        });

    }

    @Override
    public void onCatClick(int position) {
       Toast toast = Toast.makeText(getContext(),
                "Категория "+mCategories.get(position).getCategoryName(),
                Toast.LENGTH_SHORT);
        toast.show();

        Bundle bundle2 = new Bundle();
        bundle2.putInt("category_id",  mCategories.get(position).getCategoryId());
        bundle2.putString("category_name",  mCategories.get(position).getCategoryName());

        //AppCompatActivity activity = (AppCompatActivity) getContext();
        try {
            Fragment myFragment = new GoodsOfCatFragment();
            myFragment.setArguments(bundle2);

            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.goods_area, myFragment)
                    .commit();
                Log.e(TAG, "Успешно  заменили");




        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }


    private void initCatRecyclerView (){

        Log.e("Cats", "initRecyclerView: init recyclerview.");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCategoriesRecyclerAdapter = new CategoriesRecyclerAdapter(mCategories, this);

        //VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        //mRecyclerView.addItemDecoration(itemDecorator);
        //new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mCategoriesRecyclerAdapter);

    }

    @Override
    public void onClick(View v) {
        Toast toast = Toast.makeText(getContext(),
                "Все товары",     Toast.LENGTH_SHORT);
        toast.show();
    }
}
