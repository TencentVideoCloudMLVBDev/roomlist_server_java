package com.tencent.qcloud.roomservice.roomlist.pojo.Request;

public class CreateRoomReq {
    private String userID = "";
    private String nickName = ""; //用户昵称
    private String roomInfo = "";
    private String roomType = ""; //房间类型，可以为空
    private boolean needHeartBeat = true;
    private String roomID = "";

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(String roomInfo) {
        this.roomInfo = roomInfo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isNeedHeartBeat() {
        return needHeartBeat;
    }

    public void setNeedHeartBeat(boolean needHeartBeat) {
        this.needHeartBeat = needHeartBeat;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
