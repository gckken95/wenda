package com.nowcoder.service;

import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean follow(int userId,int entityType,int entityId){
        //粉丝
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        //关注的人
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        Date date=new Date();
        Jedis jedis=jedisAdapter.getJedis();
        Transaction tx=jedisAdapter.multi(jedis);
        tx.zadd(followerKey,date.getTime(),String.valueOf(userId));
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));
        List<Object> ret=jedisAdapter.exec(tx,jedis);
        return ret.size()==2 && (Long)ret.get(0)>0 && (Long)ret.get(1)>0;
    }

    public boolean unfollow(int userId,int entityType,int entityId){
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        Jedis jedis=jedisAdapter.getJedis();
        Transaction tx=jedisAdapter.multi(jedis);
        tx.zrem(followerKey,String.valueOf(userId));
        tx.zrem(followeeKey,String.valueOf(entityId));
        List<Object> ret=jedisAdapter.exec(tx,jedis);
        return ret.size()==2 && (Long)ret.get(0)>0 && (Long)ret.get(1)>0;
    }

    private List<Integer> getIdsFromSet(Set<String> idset){
        List<Integer>list=new ArrayList<>();
        for(String id:idset){
            list.add(Integer.parseInt(id));
        }
        return list;
    }

    public List<Integer> getFollowers(int entityType,int entityId,int count){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey,0,count));
    }

    public List<Integer> getFollowers(int entityType,int entityId,int offset,int count){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey,offset,count));
    }

    public List<Integer> getFollowees(int userId,int entityType,int count){
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey,0,count));
    }

    public List<Integer> getFollowees(int userId,int entityType,int offset,int count){
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey,offset,count));
    }

    public long getFollowerCount(int entityType,int entityId){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapter.zcard(followerKey);
    }

    public long getFolloweeCount(int userId,int entityType){
        String followeeKey=RedisKeyUtil.getFollowerKey(userId,entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    public boolean isFollower(int userId,int entityType,int entityId){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapter.zscore(followerKey,String.valueOf(userId)) != null;
    }



}
