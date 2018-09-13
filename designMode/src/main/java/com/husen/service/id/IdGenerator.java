package com.husen.service.id;

/**
 * Long型唯一Id生成 snowflake 算法
 * Created by HuSen on 2018/7/3 15:24.
 */
public class IdGenerator {
    private final static long BEGINTS = 1483200000000L;
    private long LASTTS = 0L;
    private long processId;
    private int processIdBits = 10;
    private long sequence = 0L;
    private int sequenceBits = 12;

    public IdGenerator(long processId) {
        if(processId > ((1 << processIdBits) - 1)){
            throw new RuntimeException("进程ID超出范围，设置位数" + processIdBits + "，最大"
                    + ((1 << processIdBits) - 1));
        }
        this.processId = processId;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public synchronized long nextId(){
        long ts = timeGen();
        if(ts < LASTTS){//刚刚生成的时间戳比上次的时间戳还小，出错
            throw new RuntimeException("时间戳顺序错误");
        }else if(ts == LASTTS){//刚刚生成的时间戳和上次的时间戳一样，则需要生成一个sequence序列号
            //sequence 循环自增
            sequence = (sequence + 1) & ((1 << sequenceBits) - 1);
            // 如果sequence=0则需要重新生成时间戳
            if(sequence == 0){
                //且必须保证时间戳序列往后
                ts = nextTs(LASTTS);
            }
        }else {// 如果ts>lastTs，时间戳序列已经不同了，此时可以不必生成sequence了，直接取0
            sequence = 0L;
        }
        LASTTS = ts;// 更新lastTs时间戳
        return ((ts - BEGINTS) << (processIdBits + sequenceBits)) | (processId << sequenceBits) | sequence;
    }

    protected long nextTs(long lastTs) {
        long ts = timeGen();
        while (ts <= lastTs) {
            ts = timeGen();
        }
        return ts;
    }
}
