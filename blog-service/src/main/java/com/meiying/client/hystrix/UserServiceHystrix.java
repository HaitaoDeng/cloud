package com.meiying.client.hystrix;

import com.meiying.client.UserServiceClient;
import com.meiying.common.dto.RespDTO;
import com.meiying.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserServiceHystrix implements UserServiceClient {

    @Override
    public RespDTO<User> getUser(String token, String username) {
        System.out.println(token);
        System.out.println(username);
        return null;
    }
}
