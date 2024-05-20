package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.CommentDao;
import com.lvucko.cookshare.dto.CommentCreationDto;
import com.lvucko.cookshare.dto.CommentDetailsDto;
import com.lvucko.cookshare.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    public List<CommentDetailsDto> getAllRecipeComments(long recipeId){
        return commentDao.getAllRecipeComments(recipeId);
    }
    public List<Comment> getAllUserComment(long userId){
        return commentDao.getAllUserComments(userId);
    }
    public long addNewComment(CommentCreationDto comment){
        return commentDao.addNewComment(comment);
    }
    public void deleteComment(long commentId){
        commentDao.deleteComment(commentId);
    }
    public void deleteAllCommentsFromUser(long userId){
        commentDao.deleteAllCommentsFromUser(userId);
    }
    public void deleteAllCommentsFromRecipe(long recipeId){
        commentDao.deleteAllCommentsFromRecipe(recipeId);
    }
}
