package com.thepen.thepen.admin;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thepen.thepen.R;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.entity.User;

public class ChangeRoleDialog extends Dialog {
    private TextView tName,userName,subExpt;
    private Button make_admin,make_teacher;
    private User userObj;
    public ChangeRoleDialog(final Context context, User user) {
        super(context, R.style.AppTheme);
        this.setContentView(R.layout.change_role_dialog);
        tName       =findViewById(R.id.tName);
        userName    =findViewById(R.id.userName);
        subExpt     =findViewById(R.id.subExpt);
        make_admin  =findViewById(R.id.make_admin);
        make_teacher=findViewById(R.id.make_teacher);
        this.userObj = user;
        tName.setText(user.firstName+" "+user.lastName);
        userName.setText(user.userName);
        subExpt.setText(user.firstName+" "+user.exptSubject);
        make_admin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               User user = new User();
               user.userName = userObj.userName;
               user.roleId = "1";
               ApiUtil.changeRoleToAdmin(user,new GetUserList());
           }
       });
       make_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.userName = userObj.userName;
                user.roleId = "2";
                ApiUtil.changeRoleToAdmin(user,new GetUserList());
            }
        });
    }

    class GetUserList extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                //UserList ul = (UserList) msg.obj;
                Log.e("Size","GetUserList "+msg.obj);
                dismiss();
            }catch(Exception cce){
                Log.d("A",""+cce);
            }
        }
    }
}
