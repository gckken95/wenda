package com.nowcoder.service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.dao.MessageDAO;
import com.nowcoder.model.Comment;
import com.nowcoder.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addMessage(Message message){


        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));


        return messageDAO.addMessage(message)>0 ? message.getId():0;

    }



    public List<Message> getMessageList(int userId,int offset,int limit){
        return messageDAO.getConversationList(userId,offset,limit);
    }


    public List<Message> getMessageDetail(String  conversationId,int offset,int limit){
        return messageDAO.getConversationDetail(conversationId,offset,limit);
    }

     public int getConversationUnreadCount( int userId, String conversationId){
        return messageDAO.getConversationUnreadCount(userId,conversationId);
     }

}
