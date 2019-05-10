package com.example.todoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_Yeuthich extends Fragment {
    ListView listView;
    FirebaseFirestore firestore;
    FirebaseUser user;
    String username;
    String todo_name_trave, todo_chitiet_trave, todo_id_trave;
    ArrayList<YeuThich> arrayList;
    YeuThichAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeuthich, container, false);
        listView = view.findViewById(R.id.list_yeuthich);

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            username = user.getEmail();
        }
        arrayList = new ArrayList<>();
        adapter = new YeuThichAdapter(getActivity(), arrayList);

        LayDataFirebase();

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

    private void LayDataFirebase() {
        firestore.collection("User").document(username).collection("YeuThich").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        todo_name_trave = document.getString("name");
                        todo_chitiet_trave = document.getString("chiTiet");
                        todo_id_trave = document.getString("id");
                        YeuThich yeuThich = new YeuThich(todo_name_trave, todo_chitiet_trave, todo_id_trave);
                        arrayList.add(yeuThich);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
