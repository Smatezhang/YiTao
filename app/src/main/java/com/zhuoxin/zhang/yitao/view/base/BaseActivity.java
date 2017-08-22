package com.zhuoxin.zhang.yitao.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/11.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
       // getSupportActionBar();
    }
    public void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class mclass) {
        Intent intent = new Intent(getBaseContext(), mclass);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
