package com.meiying.web;

import com.meiying.common.annotation.SysLogger;
import com.meiying.common.dto.RespDTO;
import com.meiying.common.exception.CommonException;
import com.meiying.common.exception.ErrorCode;
import com.meiying.common.entity.User;
import com.meiying.service.UserService;
import com.meiying.util.BPwdEncoderUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "注册", notes = "username和password为必选项")
    @PostMapping("/registry")
    @SysLogger("registry")
    public User createUser(@RequestBody User user) {
        User userDB = userService.getUserInfo(user.getUsername());
        if(userDB != null) {
            throw new CommonException(ErrorCode.USER_HAS_EXIST);
        }
        String entryPassword = BPwdEncoderUtils.BCryptPassword(user.getPassword());
        user.setPassword(entryPassword);
        return userService.createUser(user);
    }

    @ApiOperation(value = "根据用户名获取用户", notes = "根据用户名获取用户")
    @PostMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    @SysLogger("getUserInfo")
    public RespDTO getUserInfo(@PathVariable("username") String username) {
        //参数判读省略
        User user = userService.getUserInfo(username);
        return RespDTO.onSuc(user);
    }

    @ApiOperation(value = "删除当前用户", notes = "删除当前用户")
    @PostMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    @SysLogger("deleteUserInfo")
    public RespDTO deleteUserInfo(@RequestBody User user) {
        String entryPassword = BPwdEncoderUtils.BCryptPassword(user.getPassword());
        user.setPassword(entryPassword);
        User userDB = userService.getUserInfo(user.getUsername());
        if(userDB.getPassword().equals(user.getPassword()) && userDB.getUsername().equals(user.getUsername())) {
            userService.deleteUserInfo(user.getId());
        } else {
            throw new CommonException(ErrorCode.USER_PASSWORD_ERROR);
        }
        return RespDTO.onSuc(null);
    }

    //    @Autowired
    //    private AmqpTemplate rabbitTemplate;
    //    @GetMapping("/test")
    //    public void test(){
    //        rabbitTemplate.convertAndSend(RabbitConfig.queueName, "Hello from RabbitMQ!");
    //    }
}
