package com.thepen.thepen.admin;

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

public class UserListAdapter   extends BaseAdapter {
    Context context;
    List<User> users;

    public UserListAdapter(Context context, ArrayList<User> users) {
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
            view = LayoutInflater.from(context).inflate(R.layout.users_item, viewGroup, false);
        }
        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);
        TextView exptSubject = view.findViewById(R.id.exptSubject);
        TextView dob = view.findViewById(R.id.dob);
        TextView phoneNo = view.findViewById(R.id.phoneNo);
        TextView userName = view.findViewById(R.id.userName);

        name.setText(users.get(i).firstName);
        address.setText(users.get(i).address);
        if(users.get(i).exptSubject!=null){
            exptSubject.setText(users.get(i).exptSubject);
        }else{
            exptSubject.setText(users.get(i).student_std);
        }
        if(users.get(i).dob!=null){
            dob.setText(users.get(i).dob.substring(0,10));
        }
        phoneNo.setText(users.get(i).phoneNo);
        userName.setText(users.get(i).userName);
        return view;
    }
}