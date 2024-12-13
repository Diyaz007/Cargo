package com.finalproject.finalproject.controllers;

import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.dto.CommentRequest;
import com.finalproject.finalproject.dto.ProductRequest;
import com.finalproject.finalproject.entity.Comments;
import com.finalproject.finalproject.entity.Products;
import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.Roles;
import com.finalproject.finalproject.services.CommentService;
import com.finalproject.finalproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/add")
    public ResponseEntity<Object> addComment(@RequestBody CommentRequest commentRequest) {
        try {
            Map<String,Integer> errors = new HashMap<>();
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users == null) {
                errors.put("not found",404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
            }
            if(users.getRole() != Roles.USER && users.getRole() != Roles.ADMIN) {
                errors.put("access denied",403);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
            }
            Comments comments = new Comments();
            comments.setUser(users);
            comments.setComment(commentRequest.getComment());
            return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComments(comments));
        }catch (SignUpException e){
            HashMap<String,Integer> errors = (HashMap<String, Integer>) e.getErrors();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> addComment(@RequestParam Long id) {
        try {
            Map<String,Integer> errors = new HashMap<>();
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users.getRole() == Roles.USER){
                Comments comments = commentService.getCommentById(id);
                if(comments == null){
                    errors.put("not found",404);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
                }
                if(comments.getUser() == users){
                    commentService.deleteComment(comments);
                    return new ResponseEntity<>(HttpStatus.OK);
                }else {
                    errors.put("access denied",403);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
                }
            }
            if(users.getRole() == Roles.ADMIN) {
                Comments comments = commentService.getCommentById(id);
                commentService.deleteComment(comments);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SignUpException e){
            HashMap<String,Integer> errors = (HashMap<String, Integer>) e.getErrors();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
    }
    @GetMapping("/allComments")
    public ResponseEntity<List<Comments>> getAllComments() {
        try {
            List<Comments> comments = commentService.getAllComments();
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
