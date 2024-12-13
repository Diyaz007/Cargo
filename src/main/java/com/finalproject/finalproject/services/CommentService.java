package com.finalproject.finalproject.services;

import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.entity.Comments;
import com.finalproject.finalproject.entity.Flights;
import com.finalproject.finalproject.entity.Products;
import com.finalproject.finalproject.enums.FlightStatus;
import com.finalproject.finalproject.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comments addComments(Comments comments) {
        return commentRepository.save(comments);
    }
    public List<Comments> getAllComments() {
        return commentRepository.findAll();
    }
    public Comments getCommentById(Long id) {
        return commentRepository.getReferenceById(id);
    }
    public void deleteComment(Comments comments) {
        commentRepository.delete(comments);
    }
}
