package com.fscut.commonstatemachine.config;

import com.fscut.commonstatemachine.Enum.OrderEvents;
import com.fscut.commonstatemachine.Enum.OrderStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author liuxinwei
 * @date 2022/8/16 9:55
 */

/**
 * 整个程序只能执行一个状态机
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初始化状态机状态 和 状态集合
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
         states.withStates()
                 // 定义初始状态
                 .initial(OrderStates.UNPAID)
                 // 定义状态机状态
                 .states(EnumSet.allOf(OrderStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions.withExternal()
                // 待支付 -> 待收货
                .source(OrderStates.UNPAID)
                .target(OrderStates.WAITING_FOR_RECEIVE)
                .event(OrderEvents.PAY)
                // 待收货 -> 结束
                .and().withExternal()
                .source(OrderStates.WAITING_FOR_RECEIVE)
                .target(OrderStates.DONE)
                .event(OrderEvents.RECEIVE);
    }
}
