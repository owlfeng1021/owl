package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao NoFriendDao;

    public int addFriend(String id, String friendid) {
        //先判断 userid 到friendid 是否有数据 有就是重复添加好友   return 0
        Friend friend = friendDao.findByUseridAndFriendid(id, friendid);
        if (friend!=null){
            return 0;
        }
        // 直接添加好友 让好友表中userid 到friendid方向的type 为 0
        Friend friend1 = new Friend();
        friend1.setUserid(id);
        friend1.setFriendid(friendid);
        friend1.setIslike("0");
        friendDao.save(friend1);
        //判断从friendid到userid 是否有数据 如果有双方的状态改为1
        if (friendDao.findByUseridAndFriendid(friendid,id )!=null){
            friendDao.updateIsLike( "1" , id , friendid);
            friendDao.updateIsLike( "1" , friendid , id);
        }
        return 1;
    }
    public int addNoFriend(String id, String friendid) {
        NoFriend  noFriend= NoFriendDao.findByUseridAndFriendid(id, friendid);
        if(noFriend!=null){
            return 0;
        }
        noFriend= new NoFriend();
        noFriend.setUserid(id);
        noFriend.setFriendid(friendid);
        NoFriendDao.save(noFriend);
        return 1;
    }
    public void deleteFriend(String userid, String friendid) {
        friendDao.findByUseridAndFriendid(userid,friendid);
    }
}
