package com.crud.dula.common.id;

/**
 * @author crud
 * @date 2023/9/13
 */
public class SnowFlake {

    /**
     * 起始的时间戳，可以修改为服务第一次启动的时间
     * 一旦服务已经开始使用，起始时间戳就不应该改变
     */
    private final static long START_STAMP = 1694534400000L;

    /**
     * 每一部分占用的位数
     */
    // 序列号占用的位数
    private final static long SEQUENCE_BIT = 12;
    // 机器标识占用的位数
    private final static long MACHINE_BIT = 5;
    // 数据中心占用的位数
    private final static long DATACENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    // 数据中心
    private final long datacenterId;
    // 机器标识
    private final long machineId;
    // 序列号
    private long sequence = 0L;
    // 上一次时间戳
    private long lastStamp = -1L;


    /**
     * 通过单例模式来获取实例
     * 分布式部署服务时，数据节点标识和机器标识作为联合键必须唯一
     * @param datacenterId 数据节点标识ID
     * @param machineId 机器标识ID
     */
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewStamp();
        if (currStmp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStmp;

        return (currStmp - START_STAMP) << TIMESTAMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }

}
