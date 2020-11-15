package com.irij.buyerdesign.goods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.irij.buyerdesign.R;

public class GoodsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View creatorRootView = inflater.inflate(R.layout.fragment_goods, null);

        Fragment myFragment = new GoodsCatListFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        fragmentManager.beginTransaction().replace(R.id.goods_area, myFragment)
                .commit();

        return creatorRootView;
    }
}
