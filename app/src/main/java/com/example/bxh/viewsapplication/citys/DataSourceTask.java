package com.example.bxh.viewsapplication.citys;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.bxh.viewsapplication.MyApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by buxiaohui on 6/22/17.
 */

public class DataSourceTask extends AsyncTask<Void, ArrayList<Data>, ArrayList<Data>> {
    WeakReference<CallBack> callBackWeakReference;

    public void setCallBack(CallBack callBack) {
        callBackWeakReference = new WeakReference<CallBack>(callBack);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Data> datas) {
        if (callBackWeakReference != null && callBackWeakReference.get() != null) {
            callBackWeakReference.get().getData(datas);
        }
    }

    @Override
    protected void onProgressUpdate(ArrayList<Data>... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(ArrayList<Data> datas) {
        super.onCancelled(datas);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected ArrayList<Data> doInBackground(Void... params) {
        AssetManager a = MyApp.context.getAssets();
        InputStream is = null;
        try {
            is = a.open("data_source.json");
            is.toString();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String s = sb.toString();
            Log.v("datasource", s);
            if (!TextUtils.isEmpty(s)) {
                return new Gson().fromJson(s, new TypeToken<List<Data>>() {
                }.getType());
            }

        } catch (Exception e) {

        }

        return null;
    }

    public interface CallBack {
        void
        getData(ArrayList<Data> datas);
    }

}
