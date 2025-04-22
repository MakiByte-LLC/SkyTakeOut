package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("setStatus:{}",status==1?"open":"closed");
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success();
    }
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("getStatus:{}",status==1?"open":"closed");
        return Result.success(status);
    }
}
