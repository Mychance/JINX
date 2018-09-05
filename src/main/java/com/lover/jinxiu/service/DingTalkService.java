package com.lover.jinxiu.service;

import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;

public interface DingTalkService {
    @Cacheable(value="address", key="#ip", condition = "")
    String getAddressByIP(String ip) throws IOException;

}
