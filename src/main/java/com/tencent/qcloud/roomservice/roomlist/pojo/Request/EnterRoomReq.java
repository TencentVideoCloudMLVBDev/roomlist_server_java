package com.tencent.qcloud.roomservice.roomlist.pojo.Request;

public class EnterRoomReq {
    private String userID = "";
    private String nickName = ""; //用户昵称
    private String roomID = "";
    private int status = 0;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
