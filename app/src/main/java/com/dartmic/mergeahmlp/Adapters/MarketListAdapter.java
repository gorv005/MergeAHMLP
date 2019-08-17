package com.dartmic.mergeahmlp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dartmic.mergeahmlp.Constants.MarketPojo;
import com.dartmic.mergeahmlp.R;

import java.util.List;

public class MarketListAdapter extends ArrayAdapter implements Filterable {

    LayoutInflater inflater;
    Context context;
    List<MarketPojo> data;

    public MarketListAdapter(Context context, int resource, List<MarketPojo> data) {
        super(context, resource);
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }

    private class ViewHolder {
        TextView id, name;

    }

    public int getCount() {
        return data.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        MarketListAdapter.ViewHolder holder;

        View row = convertView;
        if (row == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.market_card, null);

            holder = new MarketListAdapter.ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.id);
            holder.name = (TextView) convertView.findViewById(R.id.name);

            holder.id.setText(": "+data.get(position).getMkt_id());
            holder.name.setText(": "+data.get(position).getMkt_name());

            convertView.setTag(holder);

        } else {

            holder = (MarketListAdapter.ViewHolder) convertView.getTag();
            holder.id.setText(": "+data.get(position).getMkt_id());
            holder.name.setText(": "+data.get(position).getMkt_name());
        }
        return convertView;
    }
}
