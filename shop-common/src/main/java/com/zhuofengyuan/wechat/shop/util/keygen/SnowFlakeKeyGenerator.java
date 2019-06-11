package com.zhuofengyuan.wechat.shop.util.keygen;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 雪花算法
 */
@Slf4j
@Service("snowFlakeKeyGenerator")
public class SnowFlakeKeyGenerator implements KeyGenerator{

    @Autowired
    private WorkerIDSenquence workerIDSenquence;

    public static final long EPOCH;
    private static long workerId;
    private long sequence;
    private long lastTime;

    /**
     * 初始化workerID 从ZK获取序列
     */
    @PostConstruct
    public  void initWorkerId() throws Exception {
      long workerID = workerIDSenquence.getSequence(null);
      Preconditions.checkArgument(workerID >= 0L && workerID < 1024L);
      workerId = workerID;
    }

    public synchronized Number generateKey() {
        long currentMillis = System.currentTimeMillis();
        Preconditions.checkState(this.lastTime <= currentMillis, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", new Object[]{Long.valueOf(this.lastTime), Long.valueOf(currentMillis)});
        if(this.lastTime == currentMillis) {
            if(0L == (this.sequence = ++this.sequence & 4095L)) {
                currentMillis = this.waitUntilNextTime(currentMillis);
            }
        } else {
            this.sequence = 0L;
        }

        this.lastTime = currentMillis;
        if(log.isDebugEnabled()) {
            log.debug("{}-{}-{}", new Object[]{(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date(this.lastTime)), Long.valueOf(workerId), Long.valueOf(this.sequence)});
        }

        return Long.valueOf(currentMillis - EPOCH << 22 | workerId << 12 | this.sequence);
    }

    private long waitUntilNextTime(long lastTime) {
        long time;
        for(time = System.currentTimeMillis(); time <= lastTime; time = System.currentTimeMillis()) {
        }

        return time;
    }

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 10, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        EPOCH = calendar.getTimeInMillis();
    }
}
