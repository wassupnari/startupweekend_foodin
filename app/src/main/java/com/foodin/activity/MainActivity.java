package com.foodin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.foodin.R;
import com.foodin.adapter.ItemListAdapter;
import com.foodin.pojo.CityPojo;
import com.foodin.pojo.ItemPojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends Activity {

    private List<ItemPojo> mItemList = new ArrayList<ItemPojo>();

    private List<CityPojo> mCityList = new ArrayList<CityPojo>();

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Gson gson = new Gson();
        String busan = readTextFile(this, R.raw.busan);
        String bergamo = readTextFile(this, R.raw.bergamo);
        String mumbai = readTextFile(this, R.raw.mumbai);
        Type listType = new TypeToken<CityPojo>(){}.getType();
        CityPojo city = (CityPojo) gson.fromJson(busan, listType);
        mCityList.add(city);
        city = (CityPojo) gson.fromJson(bergamo, listType);
        mCityList.add(city);
        city = (CityPojo) gson.fromJson(mumbai, listType);
        mCityList.add(city);

        setupHeader();
        setupUI();

    }

    private void setupHeader() {
        ViewStub stub = (ViewStub) findViewById(R.id.main_header);
        View view = stub.inflate();

        final EditText editText = (EditText) view.findViewById(R.id.header_main_edit);
        ImageView searchBtn = (ImageView) view.findViewById(R.id.header_main_search);
        ImageView login = (ImageView) view.findViewById(R.id.header_login);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                if (inputManager.isAcceptingText() && getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                String word = editText.getText().toString();
                if (word.equalsIgnoreCase("Busan")) {
                    refreshListview(0);
                } else if (word.equalsIgnoreCase("Bergamo")) {
                    refreshListview(1);
                } else if (word.equalsIgnoreCase("Mumbai")) {
                    refreshListview(2);
                }
            }
        });

        login.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

//        TextView title = (TextView) view.findViewById(R.id.header_main_title);
//        title.setText("San Francisco, CA");

    }

    public void setupUI() {
        listview = (ListView) findViewById(R.id.main_item_listview);

        ItemListAdapter adapter = new ItemListAdapter(this, mCityList.get(2));
        listview.setAdapter(adapter);

    }

    private void refreshListview(int id) {

        listview = (ListView) findViewById(R.id.main_item_listview);

        ItemListAdapter adapter = new ItemListAdapter(this, mCityList.get(id));
        listview.setAdapter(adapter);


    }

    public String readTextFile(Context ctx, int resId)
    {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader bufferedreader = new BufferedReader(inputreader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            while (( line = bufferedreader.readLine()) != null)
            {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        }
        catch (IOException e)
        {
            return null;
        }
        return stringBuilder.toString();
    }
}
