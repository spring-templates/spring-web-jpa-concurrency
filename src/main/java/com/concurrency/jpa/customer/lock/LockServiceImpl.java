package com.concurrency.jpa.customer.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;
import java.util.function.Supplier;

@Service
public class LockServiceImpl implements LockService{

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private static final String EXCEPTION_MESSAGE = "LOCK 을 수행하는 중에 오류가 발생하였습니다.";

    private final LockRegistry lockRegistry;

    public LockServiceImpl(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }

    @Override
    public <T> T executeWithLock(String userLockName,
                                 int timeoutSeconds,
                                 Supplier<T> supplier) {
        var lock = lockRegistry.obtain(userLockName);
        boolean lockAcquired =  lock.tryLock();
        if(lockAcquired){
            try{
                log.info("lock taken");
                return supplier.get();
            }
            finally {
                lock.unlock();
            }
        }
        else{
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }
}
