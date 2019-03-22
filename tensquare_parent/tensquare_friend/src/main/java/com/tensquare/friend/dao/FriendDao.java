package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {
    public Friend findByUseridAndFriendid(String userid,String friendid);

    @Modifying
    @Query(value = "UPDATE tb_friend SET islike = ?1 WHERE  userid =?2 AND friendid =?3",nativeQuery = true)
    public void updateIsLike( String islike ,String userid ,String friendid);

    public void deleteByUseridAndFriendid(String userid,String friendid);
}
