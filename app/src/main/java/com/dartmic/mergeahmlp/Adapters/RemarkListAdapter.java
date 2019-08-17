package com.dartmic.mergeahmlp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dartmic.mergeahmlp.R;
import com.dartmic.mergeahmlp.RemarkPojo;

import java.util.List;

public class RemarkListAdapter extends ArrayAdapter implements Filterable {
    Context context;
    List<RemarkPojo> data;
    LayoutInflater inflater;
    TextView counter;
    TextView date;
    TextView newMech;
    TextView remark;
    TextView scans;
    TextView totalVisit;

    public RemarkListAdapter(Context context, int i, List<RemarkPojo> list) {
        super(context, i);
        this.context = context;
        this.data = list;
        this.inflater = LayoutInflater.from(context);
    }


    public int getCount() {
        return this.data.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.remark_card, null);

            date = (TextView) view.findViewById(R.id.date);
            totalVisit = (TextView) view.findViewById(R.id.MechanicVisit);
            counter = (TextView) view.findViewById(R.id.counter);
            scans = (TextView) view.findViewById(R.id.scan_pts);
            remark = (TextView) view.findViewById(R.id.remark);
            newMech = (TextView) view.findViewById(R.id.new_enrollment);

            date.setText(data.get(i).getDate());
            totalVisit.setText(data.get(i).getVisited_mechanic());
            counter.setText(data.get(i).getNo_of_counter());
            scans.setText(data.get(i).getTotal_points());
            remark.setText(data.get(i).getRemark());
            newMech.setText(data.get(i).getNew_enroll());
            return view;
        }
        date.setText(data.get(i).getDate());
        totalVisit.setText(data.get(i).getVisited_mechanic());
        counter.setText(data.get(i).getNo_of_counter());
        scans.setText(data.get(i).getTotal_points());
        remark.setText(data.get(i).getRemark());
        newMech.setText(data.get(i).getNew_enroll());
        return view;
    }
}
