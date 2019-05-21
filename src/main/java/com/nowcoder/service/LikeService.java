package com.nowcoder.service;

import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long getLikeCount(int entytyType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entytyType,entityId);
        return jedisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId,int entytyType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entytyType,entityId);
        if(jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey=RedisKeyUtil.getDisLikeKey(entytyType,entityId);
        return jedisAdapter.sismember(disLikeKey,String.valueOf(userId))?-1:0;
    }

    public long like(int userId,int entytyType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entytyType,entityId);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));

        String disLikeKey=RedisKeyUtil.getDisLikeKey(entytyType,entityId);
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId,int entytyType,int entityId){
        String disLikeKey=RedisKeyUtil.getDisLikeKey(entytyType,entityId);
        jedisAdapter.sadd(disLikeKey,String.valueOf(userId));

        String likeKey= RedisKeyUtil.getLikeKey(entytyType,entityId);
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }



}
