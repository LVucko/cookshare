package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.CommentCreationDto;
import com.lvucko.cookshare.dto.CommentDetailsDto;
import com.lvucko.cookshare.models.Comment;
import com.lvucko.cookshare.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/r/{id}")
    public ResponseEntity<List<CommentDetailsDto>> getRecipeComments(@PathVariable("id") Long recipeId){

        return ResponseEntity.ok(commentService.getAllRecipeComments(recipeId));
    }
    @GetMapping("/u/{id}")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable("id") Long userId){
        return ResponseEntity.ok(commentService.getAllUserComment(userId));
    }
    @PostMapping("")
    public ResponseEntity<Long> addComment(@RequestBody CommentCreationDto comment){
        return ResponseEntity.ok(commentService.addNewComment(comment));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
    @DeleteMapping("/r/{id}")
    public ResponseEntity<String> deleteAllCommentsFromRecipe(@PathVariable("id") long recipeId){
        commentService.deleteAllCommentsFromRecipe(recipeId);
        return ResponseEntity.ok("Successfully deleted all comments from recipe");
    }
    @DeleteMapping("/u/{id}")
    public ResponseEntity<String> deleteAllCommentsFromUser(@PathVariable("id") long userId){
        commentService.deleteAllCommentsFromUser(userId);
        return ResponseEntity.ok("Successfully deleted all comments from user");
    }
}
