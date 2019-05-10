package com.example.todoapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_ToDo extends Fragment {
    ListView listView;
    ArrayList<ToDo> arrayList;
    ToDoAdapter adapter;
    FloatingActionButton btn_add;
    FirebaseFirestore firestore;
    FirebaseUser user;
    String username, todo_name, todo_chitiet;
    EditText edt_name, edt_chitiet;
    Dialog dialog;
    String todo_name_trave, todo_id_trave, todo_chitiet_trave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        listView = view.findViewById(R.id.list);
        btn_add = view.findViewById(R.id.btn_add);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            username = user.getEmail();
        }

        firestore = FirebaseFirestore.getInstance();

        arrayList = new ArrayList<>();
        adapter = new ToDoAdapter(getActivity(), arrayList);

        LayDataFirebase();

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemToDo();
            }
        });
        return view;
    }

    private void LayDataFirebase() {
        firestore.collection("User").document(username).collection("ToDo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        todo_name_trave = document.getString("name");
                        todo_chitiet_trave = document.getString("chiTiet");
                        todo_id_trave = document.getString("id");
                        ToDo toDo = new ToDo(todo_name_trave, todo_chitiet_trave, todo_id_trave);
                        arrayList.add(toDo);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ThemToDo() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_dialog);
        dialog.show();
        edt_name = dialog.findViewById(R.id.edt_name);
        edt_chitiet = dialog.findViewById(R.id.edt_chitiet);
        Button btn_add = dialog.findViewById(R.id.btn_add);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo_name = edt_name.getText().toString();
                todo_chitiet = edt_chitiet.getText().toString();
                //Tạn todo_id ngẫu nhiên rồi lấy nó
                String todo_id = firestore.collection("User").document(username).collection("ToDo").document().getId();
                ToDo toDo = new ToDo(todo_name, todo_chitiet, todo_id);
                arrayList.add(toDo);

                //Firebase gửi dữ liệu
                firestore.collection("User").document(username).collection("ToDo").document(todo_id).set(toDo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
