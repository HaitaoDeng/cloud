package com.meiying.service;

import com.meiying.client.AuthServiceClient;
import com.meiying.common.dto.RespDTO;
import com.meiying.common.exception.CommonException;
import com.meiying.common.exception.ErrorCode;
import com.meiying.dao.UserDao;
import com.meiying.dto.LoginDTO;
import com.meiying.entity.JWT;
import com.meiying.entity.User;
import com.meiying.util.BPwdEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthServiceClient authServiceClient;


    public User createUser(User user){
        return  userDao.save(user);
    }

    public User getUserInfo(String username){
        return userDao.findByUsername(username);
    }

    public RespDTO login(String username , String password){
        User user= userDao.findByUsername(username);
        if(null==user){
            throw new CommonException(ErrorCode.USER_NOT_FOUND);
        }
        if(!BPwdEncoderUtils.matches(password,user.getPassword())){
            throw new CommonException(ErrorCode.USER_PASSWORD_ERROR);
        }

        JWT jwt = authServiceClient.getToken("Basic dWFhLXNlcnZpY2U6MTIzNDU2", "password", username, password);
        // 获得用户菜单
        if(null==jwt){
            throw new CommonException(ErrorCode.GET_TOKEN_FAIL);
        }
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setUser(user);
        loginDTO.setToken(jwt.getAccess_token());
        return RespDTO.onSuc(loginDTO);
    }

}
