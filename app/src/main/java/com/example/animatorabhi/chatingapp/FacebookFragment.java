package com.example.animatorabhi.chatingapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animatorabhi.chatingapp.chat.ChatActivity;
import com.example.animatorabhi.chatingapp.chat.UserList;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by ANIMATOR ABHI on 13/03/2017.
 */

public class FacebookFragment extends android.support.v4.app.Fragment {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProgressBar pb;
    private TextView textView;
   Button demoActivity;
    private ImageView img;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private boolean isUserExist = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());
        database = FirebaseDatabase.getInstance();


        // Other app specific specialization
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        callbackManager = CallbackManager.Factory.create();


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        img = (ImageView) view.findViewById(R.id.profilePicture);
        textView = (TextView) view.findViewById(R.id.textView);
        loginButton = (LoginButton) view.findViewById(R.id.loginButton);
        pb = (ProgressBar) view.findViewById(R.id.progressBar1);
        loginButton.setReadPermissions("email");
        demoActivity= (Button) view.findViewById(R.id.demoActivity);
        // If using in a fragment
        loginButton.setFragment(this);
        pb.setVisibility(View.INVISIBLE);


        demoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),UserList.class);
                startActivity(i);
            }
        });

        updateUI();
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                //  img=profile.getProfilePictureUri();

                updateUI();
                handleFacebookAccessToken(accessToken);

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
              //  checkForUserAndSignuUp(firebaseAuth.getCurrentUser());
            }
        };


        return view;
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        pb.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()&& firebaseAuth.getCurrentUser() != null) {
                    Toast.makeText(getActivity(), "log in", Toast.LENGTH_SHORT).show();
                    updateUI();
                    pb.setVisibility(View.INVISIBLE);
                    checkForUserAndSignuUp(firebaseAuth.getCurrentUser());
                }
                


            }

            private void checkForUserAndSignuUp(final FirebaseUser currentUser) {






                    final DatabaseReference firebase = database.getReference().child("users").child(currentUser.getUid());
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            // customLoaderDialog.hide();
                            // firebase.removeEventListener(this);
                            if (snapshot.getValue() != null) {
                    /*GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                    Map<String, String> hashMap = snapshot.getValue(genericTypeIndicator );

*/
                                try {
                                    Map<String, String> hashMap = snapshot.getValue(HashMap.class);
                                    Prefs.setUserId(getActivity(), currentUser.getUid());
                                    Prefs.setUSERNAME(getActivity(), getValuesWithValid(hashMap, "displayName"));
                                    Prefs.setEMAIL(getActivity(), getValuesWithValid(hashMap, "email"));
                                    Prefs.setPhotoUri(getActivity(), getValuesWithValid(hashMap, "profileImageUri"));
                                } catch (Exception e) {

                                }
                                isUserExist = true;
                                //  if (customLoaderDialog != null)
                                //    customLoaderDialog.hide();
                          //      Intent intent = new Intent(getActivity(), MainActivity.class);
                             //   startActivity(intent);
                               // finish();
                            } else {
                                // if (customLoaderDialog != null)
                                // customLoaderDialog.hide();
                                isUserExist = false;
                                UserModel userModel = new UserModel("" + currentUser.getUid(), "online", "" + currentUser.getDisplayName(), "", "Android", "" + currentUser.getPhotoUrl(), 0);
                                userModel.setEmail(currentUser.getEmail());
                                firebase.setValue(userModel);
                                Prefs.setUserId(getActivity(), currentUser.getUid());
                                Prefs.setUSERNAME(getActivity(), currentUser.getDisplayName());
                                Prefs.setEMAIL(getActivity(), currentUser.getEmail());
                                Prefs.setPhotoUri(getActivity(), currentUser.getPhotoUrl() + "");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //customLoaderDialog.hide();
                            isUserExist = false;
                            UserModel userModel = new UserModel("" + currentUser.getUid(), "offline", "" + currentUser.getDisplayName(), "", "Android", "" + currentUser.getPhotoUrl(), 0);
                            userModel.setEmail(currentUser.getEmail());
                            Prefs.setUserId(getActivity(), currentUser.getUid());
                            Prefs.setUSERNAME(getActivity(), currentUser.getDisplayName());
                            Prefs.setEMAIL(getActivity(), currentUser.getEmail());
                            Prefs.setPhotoUri(getActivity(), currentUser.getPhotoUrl() + "");
                            firebase.setValue(userModel);
                        }

                    });




            }


        });
    }



    private String getValuesWithValid(Map<String, String> hashMap, String displayName) {
        if (hashMap.containsKey("" + displayName) && hashMap.get("" + displayName).length() > 0) {
            return hashMap.get("" + displayName) + "";
        } else {
            return "";
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void displayMessage(Profile profile) {
        if (profile != null) {
            textView.setText(profile.getName());
        }
    }


    private void updateUI() {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;

        //  postStatusUpdateButton.setEnabled(enableButtons || canPresentShareDialog);
        // postPhotoButton.setEnabled(enableButtons || canPresentShareDialogWithPhotos);

        Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);
        if (enableButtons && profile != null) {

            new LoadProfileImage(img).execute(profile.getProfilePictureUri(200, 200).toString());

            // greeting.setText(getString(R.string.hello_user, profile.getFirstName()));
            //   postingEnabled = true;
            //   postPhotoButton.setVisibility(View.VISIBLE);
            //  postStatusUpdateButton.setVisibility(View.VISIBLE);
            //  getUserInterests.setVisibility(View.VISIBLE);

        } else {
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.user_default);
            img.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(), icon, 200, 200, 200, false, false, false, false));
            // greeting.setText(null);
            //postingEnabled = false;
            // postPhotoButton.setVisibility(View.GONE);
            // postStatusUpdateButton.setVisibility(View.GONE);
            //getUserInterests.setVisibility(View.GONE);
        }
    }

    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... uri) {
            String url = uri[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            //save image to internal storage
                new ImageSaver(getActivity()).
                        setFileName("myImage.png").
                        setDirectoryName("images").
                        save(mIcon11);
            } catch (Exception e) {
                //load image from internal storage
                Bitmap bitmap = new ImageSaver(getActivity()).
                        setFileName("myImage.png").
                        setDirectoryName("images").
                        load();
                mIcon11=bitmap;
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            if (result != null) {


                Bitmap resized = Bitmap.createScaledBitmap(result, 200, 200, true);
                bmImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(), resized, 250, 200, 200, false, false, false, false));

            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }
}

