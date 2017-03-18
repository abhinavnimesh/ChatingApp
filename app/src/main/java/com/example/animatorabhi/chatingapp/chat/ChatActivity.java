package com.example.animatorabhi.chatingapp.chat;


import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
import android.widget.TextView;

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

public class ChatActivity extends AppCompatActivity {
   private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button sendBtn= (Button) findViewById(R.id.sendBtn);
        final EditText messageTxt= (EditText) findViewById(R.id.messageTxt);
        ListView messageLst= (ListView) findViewById(R.id.messageLst);
        database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference().child("chat");
        final DatabaseReference firebase = database.getReference().child("users");

      //  String key=firebase.getKey();
       // final ArrayList<UserModel> users=new ArrayList<>();
        /* this activity contains code for both user list and chat messages just use setadapter to toggle between them*/


      /*  final ArrayAdapter<UserModel> userAdapter=new ArrayAdapter<UserModel>(
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
        };*/

      //  messageLst.setAdapter(userAdapter);//for user list

  /*      firebase.addChildEventListener(new ChildEventListener() {
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
        });*/
        //{}

        // Firebase.setAndroidContext(this);
     //   final Firebase ref=new Firebase("https://chattingapp-3daae.firebaseio.com/");
        // ref.child("users");


       sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessage chat=new ChatMessage(Prefs.getUSERNAME(ChatActivity.this),messageTxt.getText().toString());
                ref.push().setValue(chat);
                messageTxt.setText("");
            }
        });
        final List<ChatMessage> messages=new LinkedList<>();
        final ArrayAdapter<ChatMessage> adapter=new ArrayAdapter<ChatMessage>(
                this,android.R.layout.two_line_list_item,messages
        ){
            @NonNull
            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if(view ==null){view=getLayoutInflater().inflate(android.R.layout.two_line_list_item,parent,false);
                }
                ChatMessage chat=messages.get(position);
                ((TextView)view.findViewById(android.R.id.text1)).setText(chat.getName());
                ((TextView)view.findViewById(android.R.id.text2)).setText(chat.getMessage());
                return view;
            }
        };
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
       messageLst.setAdapter(adapter);//for messages
     ref.addChildEventListener(new ChildEventListener() {
         @Override
         public void onChildAdded(DataSnapshot dataSnapshot, String s) {
             ChatMessage chat=dataSnapshot.getValue(ChatMessage.class);
             //  ChatMessage chat=new ChatMessage("Abhi","hello dummy");
             messages.add(chat);
             adapter.notifyDataSetChanged();
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
    }
}
