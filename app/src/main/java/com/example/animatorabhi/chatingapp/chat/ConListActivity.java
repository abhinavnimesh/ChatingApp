package com.example.animatorabhi.chatingapp.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.animatorabhi.chatingapp.Prefs;
import com.example.animatorabhi.chatingapp.R;
import com.example.animatorabhi.chatingapp.UserModel;
import com.example.animatorabhi.chatingapp.adapter.MyAdapterNew;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConListActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatConModel> conList;

    //   String myDataset[]={"A0","b0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_list);
        mRecyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        conList=new ArrayList<>();
        mAdapter=new MyAdapterNew(this,conList);
        mRecyclerView.setAdapter(mAdapter);
        database = FirebaseDatabase.getInstance();
        final DatabaseReference firebase = database.getReference().child("conversation_list").child(Prefs.getUserId(this));
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               ChatConModel chatConModel = dataSnapshot.getValue(ChatConModel.class);
                //dataSnapshot.child("YSXSfLPWo0NAiocsDmLNhc4LzTV2");
                //  ChatMessage chat=new ChatMessage("Abhi","hello dummy");
                // UserModel userModel = new UserModel("Abhi","hello dummy");
               conList.add(chatConModel);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      //  prepareAlbums();

    }

    private void prepareAlbums() {
       /* int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};*/
String pic="https://scontent.xx.fbcdn.net/v/t1.0-1/p100x100/10430428_1494574417426813_2617793330457800703_n.jpg?oh=a873e7d0a03734edf5acb2af7d5ce44f&oe=5959FC22";
        ChatConModel a = new ChatConModel("True Romance",pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance",pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance", pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance",pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance", pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance", pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance",pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance", pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance", pic,"hello");
        conList.add(a);

        a = new ChatConModel("True Romance", pic,"hello");
        conList.add(a);

        mAdapter.notifyDataSetChanged();
    }
}
