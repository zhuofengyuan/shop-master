package com.zhuofengyuan.wechat.shop.util.keygen;

import com.zhuofengyuan.wechat.shop.util.UUID;
import org.springframework.stereotype.Service;

@Service("uuidKeyGenerator")
public class UUIDKeyGenerator implements KeyGenerator<String>{

    @Override
    public synchronized String generateKey() {
        return UUID.UU32();
    }
}
