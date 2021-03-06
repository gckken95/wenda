package com.nowcoder.service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.model.Comment;
import com.nowcoder.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addComment(Comment comment){


        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));


        return commentDAO.addComment(comment)>0 ? comment.getId():0;

    }

    public Comment getCommentById(int id){
        return commentDAO.getCommentById(id);
    }

    public int getCommentCount(int entityId, int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }


    public List<Comment> getCommentsByEntity( int entityId, int entityType){
        return commentDAO.selectCommentByEntity(entityId,entityType);
    }

    public int getUserCommentCount(int userId){
        return commentDAO.getUserCommentCount(userId);
    }

    public boolean deleteComment(int commentId){
        return commentDAO.updateStatus(commentId,1)>0;
    }

}
