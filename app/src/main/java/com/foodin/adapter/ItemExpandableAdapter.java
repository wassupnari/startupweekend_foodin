package com.foodin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wassupnari on 10/18/14.
 */
public class ItemExpandableAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private List<String> mGroup = new ArrayList<String>();

    public ItemExpandableAdapter(Context context, List<String> group) {
        this.mContext = context;
        this.mGroup = group;
    }

    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i2) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i2) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        MenuGroupViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_expandable_row_group, null);
            viewHolder = new MenuGroupViewHolder();
            viewHolder.groupIcon = (ImageView) convertView.findViewById(R.id.expandable_row_group_icon);
            viewHolder.groupText = (TextView) convertView.findViewById(R.id.expandable_row_group_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MenuGroupViewHolder) convertView.getTag();
        }

        viewHolder.groupText.setText(mGroup.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    static class MenuGroupViewHolder {
        private ImageView groupIcon;
        private TextView groupText;
    }
}
