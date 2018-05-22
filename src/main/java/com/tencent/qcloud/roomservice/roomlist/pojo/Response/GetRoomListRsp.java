package com.tencent.qcloud.roomservice.roomlist.pojo.Response;

import com.tencent.qcloud.roomservice.roomlist.pojo.Room;

import java.util.ArrayList;

public class GetRoomListRsp extends BaseRsp {
    private ArrayList<Room> rooms;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
