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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToDoAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<ToDo> arrayList;
    FirebaseFirestore firestore;
    String username;
    FirebaseUser user;

    public ToDoAdapter(Activity activity, ArrayList<ToDo> arrayList) {
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
        Button btn_yeuthich;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.todo_item_list, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.btn_yeuthich = convertView.findViewById(R.id.btn_yeuthich);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ToDo toDo = arrayList.get(position);
        viewHolder.tv_name.setText(toDo.getName());
        viewHolder.btn_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    username = user.getEmail();
                }
                firestore = FirebaseFirestore.getInstance();
                String id = toDo.getId();
                String name = toDo.getName();
                String chitiet = toDo.getChiTiet();
                Map<String, String> map = new HashMap<>();
                map.put("chiTiet", chitiet);
                map.put("id", id);
                map.put("name", name);
                firestore.collection("User").document(username).collection("YeuThich").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(activity, "Thêm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return convertView;
    }
}
