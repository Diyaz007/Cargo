package com.finalproject.finalproject.repositories;

import com.finalproject.finalproject.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.stream.events.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
}
