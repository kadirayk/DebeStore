package com.kadirayk.debestore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kadirayk.debestore.R;
import com.kadirayk.debestore.models.DebeListItem;

import java.util.ArrayList;

/**
 * Created by Kadiray on 11.04.2015.
 */
public class DebeListAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DebeListItem> mDebeList;
    private View mView;

    public DebeListAdapter(Context context, ArrayList<DebeListItem> DebeList){
        mContext = context;
        mDebeList = DebeList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDebeList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return mDebeList.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = new ViewHolder();
        mView = null;

        if(position == 0 ){
            mView = mInflater.inflate(R.layout.debe_list_header, null);
            return mView;
        }else if(mView == null){
            mView = mInflater.inflate(R.layout.debe_list_item, null);
        }

        mHolder.debeListItemTitleTV = (TextView) mView.findViewById(R.id.debeListItemTitleTV);
        mHolder.debelistItemAuthorTV = (TextView) mView.findViewById(R.id.debeListItemAuthorTV);
        mHolder.debeListItemPlaceTV = (TextView) mView.findViewById(R.id.debeListItemPlaceTV);

        DebeListItem item = mDebeList.get(position-1);

        mHolder.debeListItemTitleTV.setText(item.getTitle());
        mHolder.debelistItemAuthorTV.setText(item.getAuthor());

        int mplace = item.getPlace();

        mHolder.debeListItemPlaceTV.setText(String.valueOf(mplace));

        return mView;
    }

    private class ViewHolder {
        TextView debeListItemTitleTV, debelistItemAuthorTV, debeListItemPlaceTV;
    }
}
