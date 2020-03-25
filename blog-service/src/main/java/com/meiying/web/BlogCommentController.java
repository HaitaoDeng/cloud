package com.meiying.web;

import com.meiying.common.annotation.SysLogger;
import com.meiying.common.dto.RespDTO;
import com.meiying.entity.BlogComment;
import com.meiying.service.BlogCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog_comment")
public class BlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;

    @ApiOperation(value = "发表评论", notes = "发表评论")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    @SysLogger("putBlogComment")
    public RespDTO putComment(@RequestBody BlogComment blogComment) {
        BlogComment comment = blogCommentService.add(blogComment);
        return RespDTO.onSuc(comment);
    }

    @ApiOperation(value = "查询评论")
    @GetMapping("/{blogId}")
    @SysLogger("getCommentByBlog")
    public RespDTO getCommentByBlog(@PathVariable Long blogId) {
        List<BlogComment> blogComments = blogCommentService.findCommentByBlogId(blogId);
        return RespDTO.onSuc(blogComments);
    }

}
