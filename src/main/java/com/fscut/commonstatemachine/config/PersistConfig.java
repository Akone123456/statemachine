package com.fscut.commonstatemachine.config;

import com.fscut.commonstatemachine.Enum.OrderEvents;
import com.fscut.commonstatemachine.Enum.OrderStates;
import com.fscut.commonstatemachine.Persist.InMemoryStateMachinePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

@Configuration
public class PersistConfig {
    @Autowired
    private InMemoryStateMachinePersist inMemoryStateMachinePersist;


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 注入stateMachinePersister对象
     */
    @Bean(name = "orderMemoryPersister")
    public StateMachinePersister<OrderStates, OrderEvents,String> getPersister(){
        return new DefaultStateMachinePersister<>(inMemoryStateMachinePersist);
    }


    /**
     * 持久化到Redis中
     */
    @Bean(name = "orderRedisPersister")
    public RedisStateMachinePersister<OrderStates,OrderEvents> redisPersister(){
        return new RedisStateMachinePersister<>(redisPersist());
    }



    /**
     * 通过redisConnectionFactory创建StateMachinePersist
     *
     * @return
     */
    public StateMachinePersist<OrderStates, OrderEvents,String> redisPersist() {
        RedisStateMachineContextRepository<OrderStates, OrderEvents> repository =
                new RedisStateMachineContextRepository<>(redisConnectionFactory);
        return new RepositoryStateMachinePersist<>(repository);
    }

}
