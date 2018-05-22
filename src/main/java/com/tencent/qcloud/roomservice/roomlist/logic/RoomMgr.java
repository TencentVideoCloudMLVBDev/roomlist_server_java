package com.tencent.qcloud.roomservice.roomlist.logic;

import com.tencent.qcloud.roomservice.roomlist.common.Config;
import com.tencent.qcloud.roomservice.roomlist.pojo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomMgr implements InitializingBean {
    private ConcurrentHashMap<String, com.tencent.qcloud.roomservice.roomlist.pojo.Room> roomMap = new ConcurrentHashMap<>();
    private static Logger log = LoggerFactory.getLogger(RoomMgr.class);

    private HeartTimer heartTimer = new HeartTimer();
    private Timer timer = null;

    @Override
    public void afterPropertiesSet() throws Exception {
// 开启心跳检查定时器
        if (timer == null) {
            timer = new Timer();
            timer.schedule(heartTimer, 5 * 1000, 5 * 1000);
        }
    }

    public class HeartTimer extends TimerTask {
        @Override
        public void run() {
            onTimer();
        }
    }

    private void onTimer() {
        // 遍历房间每个成员，检查pusher的时间戳是否超过timeout
        long currentTS = System.currentTimeMillis()/1000;
        int timeout = Config.Room.heartBeatTimeout;

        for (com.tencent.qcloud.roomservice.roomlist.pojo.Room room : roomMap.values()) {
            if (!room.isNeedHeartBeat())
                continue;
            Iterator<Map.Entry<String, Member>> entries = room.getMembersMap().entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Member> entry = entries.next();
                if (currentTS - entry.getValue().getTimeStamp() > timeout) {
                    entries.remove();
                }
            }
        }
    }

    /**
     * 获取房间列表
     */
    public ArrayList<com.tencent.qcloud.roomservice.roomlist.pojo.Room> getList(int cnt, int index, String roomType) {
        ArrayList<com.tencent.qcloud.roomservice.roomlist.pojo.Room> resultList = new ArrayList<>();
        int cursor = 0;
        int roomCnt = 0;

        //遍历
        for (com.tencent.qcloud.roomservice.roomlist.pojo.Room value : roomMap.values()) {
            if (roomCnt >= cnt)
                break;

            if (!value.getRoomType().equals(roomType)) {
                continue;
            }

            log.info("getRoomList roomID: " + value.getRoomID() + ", members count: " + value.getMembersCnt() + ", members list : " + value.getMembers() + ", roomType: " + roomType);

            if (value.getMembersCnt() != 0) {
                if (cursor >= index) {
                    resultList.add(value);
                    ++roomCnt;
                } else {
                    ++cursor;
                    continue;
                }
            } else {
                roomMap.remove(value.getRoomID());
            }
        }

        return resultList;
    }


    /**
     * 创建房间
     */
    public void creatRoom(String roomID, String userID, String nickName, String roomInfo, String roomType, boolean needHeartBeat) {
        com.tencent.qcloud.roomservice.roomlist.pojo.Room room = new com.tencent.qcloud.roomservice.roomlist.pojo.Room();
        room.setRoomID(roomID);
        room.setRoomInfo(roomInfo);
        room.addMember(userID, nickName);
        room.setRoomCreator(userID);
        room.setRoomType(roomType);
        room.setNeedHeartBeat(needHeartBeat);
        log.info("creatRoom roomID: " + roomID + ", userID: " + userID + ", nickName: " + nickName + ", roomType: " + roomType);
        roomMap.put(roomID, room);
    }

    public void deleteRoom(String roomID) {
        com.tencent.qcloud.roomservice.roomlist.pojo.Room room = roomMap.get(roomID);
        log.info("deleteRoom roomID: " + roomID);
        if (room != null) {
            roomMap.remove(roomID);
        }
    }

    /**
     * 房间是否存在
     */
    public boolean isRoomExist(String roomID) {
        return roomMap.containsKey(roomID);
    }

    /**
     * 用户是否在房间中
     */
    public boolean isMemberExist(String roomID, String userID) {
        com.tencent.qcloud.roomservice.roomlist.pojo.Room room = roomMap.get(roomID);
        if (room != null) {
            return room.isMember(userID);
        }
        return false;
    }


    /**
     * 心跳更新
     */
    public void updateTimeStamp(String roomID, String userID) {
        com.tencent.qcloud.roomservice.roomlist.pojo.Room room = roomMap.get(roomID);
        if (room != null && room.isMember(userID)) {
            room.updateMember(userID);
        }
    }

    /**
     * 获取房间推流者人数
     */
    public int getMemberCnt(String roomID) {
        int count = 0;
        com.tencent.qcloud.roomservice.roomlist.pojo.Room room = roomMap.get(roomID);
        if (room != null) {
            count = room.getMembersCnt();
        }
        return count;
    }

    /**
     * 删除成员
     */
    public void delMember(String roomID, String userID) {
        com.tencent.qcloud.roomservice.roomlist.pojo.Room room = roomMap.get(roomID);
        if (room != null) {
            room.delMember(userID);
            log.info("delMember roomID: " + roomID + ", userID: " + userID);

            if (room.getMembersCnt() == 0) {
                roomMap.remove(roomID);
            }
        }
    }

    public void addMember(String roomID, String userID, String nickName, int status) {
        com.tencent.qcloud.roomservice.roomlist.pojo.Room room = roomMap.get(roomID);
        if (room != null) {
            room.addMember(userID, nickName);
            room.setStatus(status);
            log.info("addMember roomID: " + roomID + ", userID: " + userID);
        }
    }

    public ArrayList<Member> getMembers(String roomID) {
        com.tencent.qcloud.roomservice.roomlist.pojo.Room room = roomMap.get(roomID);
        if (room != null) {
            return room.getMembers();
        }
        return null;
    }
}
