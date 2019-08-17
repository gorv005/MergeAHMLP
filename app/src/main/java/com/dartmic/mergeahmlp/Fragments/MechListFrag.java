package com.dartmic.mergeahmlp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Adapters.MechanicListAdapt;
import com.dartmic.mergeahmlp.AddMechanic;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.MechListBean;
import com.dartmic.mergeahmlp.MLPDashboard;
import com.dartmic.mergeahmlp.R;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MechListFrag extends Fragment implements View.OnClickListener {

    ImageView img_menu;
    Context context;
    RecyclerView recycler;
    ArrayList<MechListBean> arrayList, data;
    MLPDashboard dashboard;
    RecyclerView.LayoutManager manager;
    MechanicListAdapt adapter;
    EditText showlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mech_list_frag, container, false);
        dashboard = new MLPDashboard();
        arrayList = new ArrayList<>();
        data = new ArrayList<>();
        context = getActivity();
        mechanicClick(v);
        if (FixedData.mData.size() > 0) {
            filterBc(FixedData.mData);
        } else
            fetchAllList();

        return v;
    }

    public void mechanicClick(View v) {

        v.findViewById(R.id.btn_Add_Mechanic).setOnClickListener(this);
        img_menu = (ImageView) v.findViewById(R.id.img_menu);
        img_menu.setOnClickListener(this);
        recycler = (RecyclerView) v.findViewById(R.id.recycler);
        showlist = (EditText) v.findViewById(R.id.showlist);
        manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        showlist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.clear();
                for (int p = 0; p < arrayList.size(); p++) {
                    if (arrayList.get(p).getPassbook_no().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || arrayList.get(p).getMech_Name().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        data.add(arrayList.get(p));
                    }
                }
                filterBc(data);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void fetchAllList() {
        ((MLPDashboard) getActivity()).showProgressDialog();
        Log.e("Lme Id", MyPref.storePrefs(context).getLmeId());
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/mechlist.php")
                .setTag("List_Mechanic")
                .setPriority(Priority.MEDIUM)
                .addBodyParameter("lme_id", MyPref.storePrefs(context).getLmeId())
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ((MLPDashboard) getActivity()).hideProgressDialog();
                try {
                    Log.e("RESPONSE AJA::::::", response.toString());
                    if (response.getInt("status") == 2) {
                        JSONArray array = response.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String shop_Name = object.getString("WorkshopeName");
                            String city = object.getString("Distt");
                            String points = object.getString("total_pts");
                            String name = object.getString("Name");
                            String passbook = object.getString("PassbookNo");
                            String mech_ID = object.getString("M_id");
                            String mobile = object.getString("Phone");

                            MechListBean mechListBean = new MechListBean();
                            mechListBean.setShopName(shop_Name);
                            mechListBean.setCity(points);
                            mechListBean.setMobile(mobile);
                            mechListBean.setMech_Name(name);
                            MyPref.storePrefs(context).setMechName(name);
                            MyPref.storePrefs(context).setMOBILE(object.getString("Phone"));
                            mechListBean.setMech_Id(mech_ID);
                            mechListBean.setPassbook_no(passbook);
                            arrayList.add(mechListBean);
                        }
                    }

                    Log.e("array list007", arrayList.size() + "");

                } catch (JSONException e) {
                    Log.e("Log", e + "");
                }
                filterBc(arrayList);
            }

            @Override
            public void onError(ANError anError) {
                Log.e("Error", anError.toString());
                ((MLPDashboard) getActivity()).hideProgressDialog();

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_Add_Mechanic) {
            Intent intent = new Intent(getActivity(), AddMechanic.class);
            startActivity(intent);
            getActivity().finish();
        } else if (view.getId() == R.id.img_menu) {
            MLPDashboard.drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    void filterBc(ArrayList<MechListBean> bc) {
        MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        adapter = new MechanicListAdapt(context, bc);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }
}

