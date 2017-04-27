package com.example.animatorabhi.chatingapp.chat;

/**
 * Created by ANIMATOR ABHI on 09/04/2017.
 */

public class ChatConModel {
    private String isDelete;
    private String latestactivity;
    private double timestamp;
    private String user_id;
    private String profilePic;
    private String displayName;
    private String creatorId;
    private String chat_id;

    public ChatConModel(String isDelete, String latestactivity, double timestamp, String user_id, String profilePic, String displayName, String creatorId, String chat_id) {
        this.isDelete = isDelete;
        this.latestactivity = latestactivity;
        this.timestamp = timestamp;
        this.user_id = user_id;
        this.profilePic = profilePic;
        this.displayName = displayName;
        this.creatorId = creatorId;
        this.chat_id = chat_id;
    }

    public ChatConModel(double timestamp, String user_id, String profilePic, String displayName, String chat_id) {
        this.timestamp = timestamp;
        this.user_id = user_id;
        this.profilePic = profilePic;
        this.displayName = displayName;
        this.chat_id = chat_id;
    }

    public ChatConModel(String latestactivity, String profilePic, String displayName) {
        this.latestactivity = latestactivity;
        this.profilePic = profilePic;
        this.displayName = displayName;
    }

    public ChatConModel(){}

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getLatestactivity() {
        return latestactivity;
    }

    public void setLatestactivity(String latestactivity) {
        this.latestactivity = latestactivity;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }


}
