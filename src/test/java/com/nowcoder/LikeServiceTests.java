package com.nowcoder;

import com.nowcoder.service.LikeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class LikeServiceTests {

    @Autowired
    LikeService likeService;


    @Test
    public void testLike(){
        likeService.like(123,1,1);
        Assert.assertEquals(1,likeService.getLikeStatus(123,1,1));
    }

}
