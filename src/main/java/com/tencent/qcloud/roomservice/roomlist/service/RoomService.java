package com.tencent.qcloud.roomservice.roomlist.service;

import com.tencent.qcloud.roomservice.roomlist.pojo.Request.*;
import com.tencent.qcloud.roomservice.roomlist.pojo.Response.*;

public interface RoomService {
    GetLoginInfoRsp getLoginInfo(String userID);
    CreateRoomRsp createRoom(CreateRoomReq req);
    EnterRoomRsp enterRoom(EnterRoomReq req);
    BaseRsp quitRoom(QuitRoomReq req);
    BaseRsp heartbeat(HeartBeatReq req);
    GetRoomListRsp getRoomList(GetRoomListReq req);
    GetRoomMembersRsp getRoomMembers(GetRoomMembersReq roomID);

    BaseRsp deleteRoom(DeleteRoomReq req);
}