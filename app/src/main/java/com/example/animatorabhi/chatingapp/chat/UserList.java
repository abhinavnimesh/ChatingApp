package com.example.animatorabhi.chatingapp.chat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animatorabhi.chatingapp.Prefs;
import com.example.animatorabhi.chatingapp.R;

import com.example.animatorabhi.chatingapp.UserModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserList extends AppCompatActivity {
    private FirebaseDatabase database;
    private String chat_id;
    ChatConModel chatConModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button sendBtn= (Button) findViewById(R.id.sendBtn);
        final EditText messageTxt= (EditText) findViewById(R.id.messageTxt);
        ListView userList= (ListView) findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference().child("chat");
        final DatabaseReference firebase = database.getReference().child("users");


        //  String key=firebase.getKey();
        final ArrayList<UserModel> users=new ArrayList<>();


        final ArrayAdapter<UserModel> userAdapter=new ArrayAdapter<UserModel>(
                this,android.R.layout.two_line_list_item,users
        ){
            @NonNull
            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if(view ==null){view=getLayoutInflater().inflate(android.R.layout.two_line_list_item,parent,false);
                }
                UserModel user=users.get(position);
                ((TextView)view.findViewById(android.R.id.text1)).setText(user.getFirstName());

                ((TextView)view.findViewById(android.R.id.text2)).setText(user.getStatus());
                return view;
            }
        };

        userList.setAdapter(userAdapter);

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                //dataSnapshot.child("YSXSfLPWo0NAiocsDmLNhc4LzTV2");
                //  ChatMessage chat=new ChatMessage("Abhi","hello dummy");
                // UserModel userModel = new UserModel("Abhi","hello dummy");
                users.add(userModel);
                userAdapter.notifyDataSetChanged();
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
        {}

        // Firebase.setAndroidContext(this);
        //   final Firebase ref=new Firebase("https://chattingapp-3daae.firebaseio.com/");
        // ref.child("users");



        //  Query recent=ref.limitToLast(5);
     /* FirebaseListAdapter<ChatMessage> adapter=new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,android.R.layout.two_line_list_item,recent) {
            @Override
            protected void populateView(View view, ChatMessage chat, int i) {
                ((TextView)view.findViewById(android.R.id.text1)).setText(chat.getName());
                ((TextView)view.findViewById(android.R.id.text2)).setText(chat.getMessage());*/
             /*   RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) view.getLayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setBackground(getDrawable(R.drawable.ch));
                }
                else
                {view.setBackground(getDrawable(R.drawable.ch));}*/
         /*   }
        };*/
        // messageLst.setAdapter(adapter);


        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserModel u=users.get(i);
            /*  String chat_id=  */checkStatus(u.getUserId());
                Toast.makeText(UserList.this,u.getUserId(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UserList.this,ChatActivity.class);
                intent.putExtra("reciverUserName",u.getFirstName());
                intent.putExtra("reciverUid", u.getUserId());
                intent.putExtra("reciverProfilePic", u.getProfileImageUri());
                intent.putExtra("chat_id", chat_id);
Log.d("user chat id:",""+chat_id);
                UserList.this.startActivity(intent);

            }
        });

    }

    private void checkStatus(String reciverUid) {
        Log.d("user c status rec id:",reciverUid);

        DatabaseReference senderRefrence = database.getReference().child("conversation_list").child(Prefs.getUserId(UserList.this)).child(reciverUid);
        // senderRefrence.child("yo").setValue("ha");
        senderRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getValue();
                    ChatConModel chatConModel = dataSnapshot.getValue(ChatConModel.class);
                    chat_id = chatConModel.getChat_id();
                    Log.d("data check hear -> ", "" + dataSnapshot);
                    Log.d("user  status chat id:",chat_id);
                    // setUpFirebaseAdapter();
                } else{
                    Log.d("user else","no");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
