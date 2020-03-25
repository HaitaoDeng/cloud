package com.meiying.web;

import com.meiying.common.annotation.SysLogger;
import com.meiying.common.dto.RespDTO;
import com.meiying.entity.Blog;
import com.meiying.common.exception.CommonException;
import com.meiying.common.exception.ErrorCode;
import com.meiying.service.BlogService;
import com.meiying.util.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @ApiOperation(value = "发布博客", notes = "发布博客")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    @SysLogger("postBlog")
    public RespDTO postBlog(@RequestBody Blog blog){
        //字段判读省略
       Blog blog1= blogService.postBlog(blog);
       return RespDTO.onSuc(blog1);
    }

    @ApiOperation(value = "根据用户id获取所有的blog", notes = "根据用户id获取所有的blog")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{username}")
    @SysLogger("getBlogs")
    public RespDTO getBlogs(@PathVariable String  username){
        //字段判读省略
        if(UserUtils.isMyself(username)) {
            List<Blog> blogs = blogService.findBlogs(username);
            return RespDTO.onSuc(blogs);
        }else {
            throw new CommonException(ErrorCode.TOKEN_IS_NOT_MATCH_USER);
        }
    }

    @ApiOperation(value = "获取博文的详细信息", notes = "获取博文的详细信息")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}/detail")
    @SysLogger("getBlogDetail")
    public RespDTO getBlogDetail(@PathVariable Long id){
        return RespDTO.onSuc(blogService.findBlogDetail(id));
    }

    @ApiOperation(value = "删除当前用户的博文", notes = "删除当前用户的博文")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{username}/{id}")
    @SysLogger("deleteBlogDetail")
    public RespDTO deleteBlogDetail(@PathVariable String username, @PathVariable Long id) {
        if(UserUtils.isMyself(username)) {
            blogService.deleteBlogDetail(id);
            return RespDTO.onSuc(null);
        }else {
            throw new CommonException(ErrorCode.TOKEN_IS_NOT_MATCH_USER);
        }
    }
}
