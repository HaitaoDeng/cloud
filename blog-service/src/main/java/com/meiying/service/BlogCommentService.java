package com.meiying.service;

import com.meiying.dao.BlogCommentDao;
import com.meiying.entity.BlogComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCommentService {

    @Autowired
    private BlogCommentDao blogCommentDao;


    public BlogComment add(BlogComment blogComment) {
        return blogCommentDao.save(blogComment);
    }

    public List<BlogComment> findCommentByBlogId(Long blogId) {
        return blogCommentDao.findBlogCommentsByBlog_Id(blogId);
    }

}
