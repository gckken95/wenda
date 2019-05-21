package com.nowcoder.controller;

import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventProducer;
import com.nowcoder.async.EventType;
import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.model.Question;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "/addComment",method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId")int questionId,
                              @RequestParam("content")String content){
        try {

            Comment comment=new Comment();
            comment.setContent(content);
            if(hostHolder.getUser()!=null){
                comment.setUserId(hostHolder.getUser().getId());
            }else{
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            commentService.addComment(comment);

            int count =commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(),count);

            eventProducer.fireEvent(new EventModel(EventType.COMMENT)
                                    .setActorId(comment.getUserId())
                                    .setEntityId(questionId));

        }catch (Exception e){
            logger.error("增加评论失败"+e.getMessage());
        }

        return "redirect:/question/"+questionId;
    }



}
