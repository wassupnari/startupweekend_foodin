package com.foodin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodin.R;
import com.foodin.pojo.ItemPojo;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wassupnari on 10/18/14.
 */
public class ItemDetail extends Activity {

    private ExpandableListView mExpandableListview;

    private List<String> mGroup = new ArrayList<String>();
    private List<List<String>> mChildWrapper = new ArrayList<List<String>>();
    private List<String> mChild = new ArrayList<String>();

    private ImageView mMap;

    private ItemPojo mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item_detail);

        JsonParser parser = new JsonParser();
        String str = getIntent().getStringExtra("item_info");
        mItem = new Gson().fromJson(parser.parse(str).getAsJsonObject(), ItemPojo.class);

        setupHeader();
        setupUI();
    }

    private void setupHeader() {
        ViewStub stub = (ViewStub) findViewById(R.id.detail_header);
        View view = stub.inflate();

        ImageView back = (ImageView) view.findViewById(R.id.header_back_btn);
        TextView title = (TextView) view.findViewById(R.id.header_with_back_title);

        back.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                ItemDetail.this.finish();
            }
        });

        title.setText(mItem.getName());

    }

    private void setupUI() {
        ImageView image = (ImageView) findViewById(R.id.detail_item_image);
        Button share = (Button) findViewById(R.id.detail_item_share);
        mMap = (ImageView) findViewById(R.id.detail_item_map);

        Picasso.with(this).load(mItem.getImgURL()).into(image);

        share.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                String body = "I just tried \"" + mItem.getName().toLowerCase() + "\" via Foodin! So good! http://www.food-in.co/ #Foodin #SWwomenSF";
                if(body == null) {

                } else {
                    Intent tweetIntent = new Intent();
                    tweetIntent.setType("text/plain");
                    tweetIntent.putExtra(Intent.EXTRA_TEXT, body);
                    final PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> list = packageManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

                    for (ResolveInfo resolveInfo : list) {
                        String p = resolveInfo.activityInfo.packageName;
                        if (p != null && p.startsWith("com.twitter.android")) {//if the native twitter app is found
                            tweetIntent.setPackage(p);
                            startActivity(tweetIntent);
                        } else {
                            // When native twitter app is not found
                        }
                    }
                }
            }
        });

        loadStaticMap();
    }

    private void loadStaticMap() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        String lat = "37.774929";
        String lng = "-122.419416";

        final String endpoint = "http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=16&size=" + width/2 + "x" + dpToPixel(this, 153.33)/2 + "&scale=2&markers=color:blue%7Clabel:S%7C" + lat + "," + lng;

        AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bmp = null;
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet(endpoint);

                InputStream in = null;
                try {
                    in = httpclient.execute(request).getEntity().getContent();
                    bmp = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            protected void onPostExecute(Bitmap bmp) {
                if (bmp != null) {
                    mMap.setImageBitmap(bmp);
                }
            }
        };
        setImageFromUrl.execute();
    }

    public static int dpToPixel(Context context, double dp) {
        final double scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
