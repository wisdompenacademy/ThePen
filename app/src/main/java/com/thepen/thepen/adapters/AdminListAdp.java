package com.thepen.thepen.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thepen.thepen.R;
import com.thepen.thepen.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AdminListAdp  extends BaseAdapter {
    Context context;
    List<User> users;

    public AdminListAdp(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        Log.e("Size",""+users.size());
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.admin_item, viewGroup, false);
        }
        TextView name = view.findViewById(R.id.name);
        TextView exptSubject = view.findViewById(R.id.exptSubject);

        name.setText(users.get(i).firstName +" " +users.get(i).lastName);
        exptSubject.setText(users.get(i).exptSubject);
        return view;
    }
}