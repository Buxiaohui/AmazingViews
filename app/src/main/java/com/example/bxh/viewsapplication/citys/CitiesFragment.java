package com.example.bxh.viewsapplication.citys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bxh.viewsapplication.R;

import java.util.ArrayList;

/**
 * Created by buxiaohui on 6/22/17.
 */

public class CitiesFragment extends Fragment implements DataSourceTask.CallBack{
    @Override
    public void getData(ArrayList<Data> datas) {
        //TODO
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataSourceTask task = new DataSourceTask();
        task.setCallBack(this);
        task.execute();
    }
}
