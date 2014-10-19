package com.foodin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodin.R;
import com.foodin.activity.ItemDetail;
import com.foodin.pojo.CityPojo;
import com.foodin.pojo.ItemPojo;
import com.foodin.utility.Fonts;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by wassupnari on 10/18/14.
 */
public class ItemListAdapter extends BaseAdapter {

    private Context mContext;

    private CityPojo mCity;

    public ItemListAdapter(Context context, CityPojo city) {
        this.mContext = context;
        this.mCity = city;
    }

    @Override
    public int getCount() {
        return mCity.getList().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ItemViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_row, null);
            viewHolder = new ItemViewHolder();
            viewHolder.contentLayout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
            viewHolder.headerImage = (ImageView) convertView.findViewById(R.id.list_item_first_image);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.list_item_image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.list_item_name);
            viewHolder.name.setTypeface(Fonts.getOpenSansRegular(mContext));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }

        if(position == 0) {
            viewHolder.headerImage.setVisibility(View.VISIBLE);
            viewHolder.contentLayout.setVisibility(View.GONE);
        } else {
            viewHolder.headerImage.setVisibility(View.GONE);
            viewHolder.contentLayout.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(mCity.getList().get(position).getImgURL()).into(viewHolder.image);
            viewHolder.name.setText(mCity.getList().get(position).getName());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ItemDetail.class);
                    ItemPojo pojo = mCity.getList().get(position);
                    Log.d("SAN", "Clicked name : " + pojo.getName());
                    intent.putExtra("item_info", new Gson().toJson(pojo));
                    mContext.startActivity(intent);
                }
            });
        }



        return convertView;
    }

    static class ItemViewHolder {
        RelativeLayout contentLayout;
        ImageView headerImage;
        ImageView image;
        TextView name;
    }
}
