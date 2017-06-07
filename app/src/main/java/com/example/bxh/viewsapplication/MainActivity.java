package com.example.bxh.viewsapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        menu.add(0, 0, 2, R.string.origin);
        menu.add(0, 1, 2, R.string.pull_anim);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == 0) {
            return replace(item.getItemId());
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean replace(int index){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment  fragment= null;
        String tag = "fragment"+index;
        switch (index){
            case 0:
                fragment = new OriginFragment();
                Toast.makeText(MainActivity.this,R.string.origin,Toast.LENGTH_SHORT).show();
                break;
            case 1:
                fragment = new PullFragment();
                Toast.makeText(MainActivity.this,R.string.pull_anim,Toast.LENGTH_SHORT).show();
                break;
        }
        if(fragment != null){
            transaction.replace(R.id.container,fragment,tag);
            transaction.commit();
            return false;
        }
        return true;
    }
}
