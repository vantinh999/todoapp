package com.example.todoapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YeuThichAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<YeuThich> arrayList;
    FirebaseFirestore firestore;
    String username;
    FirebaseUser user;

    public YeuThichAdapter(Activity activity, ArrayList<YeuThich> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tv_name;
        Button btn_xoa;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.yeuthich_item_list, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.btn_xoa = convertView.findViewById(R.id.btn_xoa);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final YeuThich yeuThich = arrayList.get(position);
        viewHolder.tv_name.setText(yeuThich.getName());
        viewHolder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    username = user.getEmail();
                }
                firestore = FirebaseFirestore.getInstance();
                String id = yeuThich.getId();
                firestore.collection("User").document(username).collection("YeuThich").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return convertView;
    }
}
