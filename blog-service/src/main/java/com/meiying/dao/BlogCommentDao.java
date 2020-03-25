package com.meiying.dao;

import com.meiying.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCommentDao extends JpaRepository<BlogComment, Long> {


    List<BlogComment> findBlogCommentsByBlog_Id(Long blogId);
}
