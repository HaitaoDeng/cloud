package com.meiying.service;


import com.meiying.client.UserServiceClient;
import com.meiying.common.entity.User;
import com.meiying.dao.BlogDao;
import com.meiying.dto.BlogDetailDTO;
import com.meiying.common.dto.RespDTO;
import com.meiying.entity.Blog;
import com.meiying.common.exception.CommonException;
import com.meiying.common.exception.ErrorCode;
import com.meiying.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {

    @Autowired
    BlogDao blogDao;

    @Autowired
    UserServiceClient userServiceClient;

    public Blog postBlog(Blog blog) {
        return blogDao.save(blog);
    }

    public List<Blog> findBlogs(String username) {
        return blogDao.findByUsername(username);
    }


    public BlogDetailDTO findBlogDetail(Long id) {
        Blog blog = blogDao.findOne(id);
        if (null == blog) {
            throw new CommonException(ErrorCode.BLOG_IS_NOT_EXIST);
        }
        RespDTO<User> respDTO = userServiceClient.getUser(UserUtils.getCurrentToken(), blog.getUsername());
        if (respDTO==null) {
            throw new CommonException(ErrorCode.RPC_ERROR);
        }
        BlogDetailDTO blogDetailDTO = new BlogDetailDTO();
        blogDetailDTO.setBlog(blog);
        blogDetailDTO.setUser(respDTO.data);
        return blogDetailDTO;
    }

    public void deleteBlogDetail(Long id) {
        blogDao.delete(id);
    }

}
