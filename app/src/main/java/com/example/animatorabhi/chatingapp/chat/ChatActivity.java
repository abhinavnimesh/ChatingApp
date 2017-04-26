package com.example.animatorabhi.chatingapp.chat;


import android.content.Intent;
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
   private String reciverUserName, reciverUid, reciverProfilePic;
    private String senderId;
    private String chat_id;

    Intent intent;
    private boolean chatIdExist=false;
    private boolean isAlready = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        intent=getIntent();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference refChat = database.getReference().child("chat");
        final DatabaseReference firebaseUser = database.getReference().child("users");
       // checkStatus();
        getDataFromBundle();
        //checkStatus();
        Button sendBtn= (Button) findViewById(R.id.sendBtn);
        final EditText messageTxt= (EditText) findViewById(R.id.messageTxt);
        ListView messageLst= (ListView) findViewById(R.id.messageLst);


        senderId=Prefs.getUserId(this);
        if (chat_id == null) {
           // checkStatus();
        } else {
            chatIdExist = true;
           //checkStatus();
            setUpFirebaseAdapter();
        }
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
                DatabaseReference chatMessageRefrence;

                if(chat_id!=null)
                {  chatMessageRefrence= database.getReference().child("chat").child(chat_id).push();}
                else{  DatabaseReference chatIdRefrence=refChat.push();
                    String chatId="";
                     chatId = chatIdRefrence.getKey();
                    Log.d("else ref chat id:",""+chat_id);

                    chat_id = chatId;

                     chatMessageRefrence = chatIdRefrence.push();
                }



                ChatMessage chat = new ChatMessage(Prefs.getUSERNAME(ChatActivity.this), messageTxt.getText().toString());


                    chatMessageRefrence.setValue(chat);
                    messageTxt.setText("");


                    //Sender Refrence
                    ChatConModel chatConModel = new ChatConModel();
                    chatConModel.setDisplayName("" + reciverUserName);
                   // chatConModel.setBadge(0);
                Log.d("send mess chat id:",""+chat_id);

                chatConModel.setChat_id(chat_id);
                  //  chatConModel.setIsDelete("NO");
                  /*  if (type == 2) {
                        chatConModel.setLatestactivity("Image");
                    } else {
                        chatConModel.setLatestactivity(message);
                    }*/
                    chatConModel.setProfilePic(reciverProfilePic);
                   // chatConModel.setTimestamp(Utils.getCurrentTimeStamp());
                    chatConModel.setUser_id(reciverUid);
                  //  chatConModel.setGroup(false);
                    DatabaseReference senderRefrence = database.getReference().child("conversation_list").child(senderId).child(reciverUid);
                    senderRefrence.setValue(chatConModel);
                    DatabaseReference reciverRefrence = database.getReference().child("conversation_list").child(reciverUid).child(senderId);
                    chatConModel.setUser_id(senderId);
                  //  chatConModel.setBadge(getBadgerCount(reciverRefrence));
                    chatConModel.setDisplayName("" + Prefs.getUSERNAME(ChatActivity.this));
                    chatConModel.setProfilePic("" + Prefs.getPhotoUri(ChatActivity.this));
                    reciverRefrence.setValue(chatConModel);

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
        Log.d("before read chat_id",""+chat_id);
     refChat.child("-Ki_-SO1JxUxIDIpguT0").addChildEventListener(new ChildEventListener() {
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

    private void sendMessage(){

    }

    private void setUpFirebaseAdapter() {
    }

    private void checkStatus() {
        DatabaseReference senderRefrence = database.getReference().child("conversation_list").child("jwiJxUQDmBfkvhtvgBmVAUKFdDe2").child("tRzyV5405uQ1qAJ4hnfHmQVlLbb2");
       // senderRefrence.child("yo").setValue("ha");
        senderRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    dataSnapshot.getValue();
                    ChatConModel chatConModel = dataSnapshot.getValue(ChatConModel.class);
                    chat_id = chatConModel.getChat_id();
                    Log.d("chatdata hear -> ", "" + dataSnapshot);
                    Log.d("c check status chat id:",chat_id);
                    setUpFirebaseAdapter();
                } else
                    isAlready = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            chat_id = bundle.getString("chat_id");
            reciverUserName = "" + bundle.getString("reciverUserName");
            reciverUid = "" + bundle.getString("reciverUid");
            reciverProfilePic = "" + bundle.getString("reciverProfilePic");
            getSupportActionBar().setTitle("" + reciverUserName);
            Log.d("get bund chat_id: ",""+bundle);

        }

    }
}
