package com.fscut.commonstatemachine.Build;

/**
 * @author liuxinwei
 * @date 2022/8/16 18:07
 */

import com.fscut.commonstatemachine.Enum.OrderEvents;
import com.fscut.commonstatemachine.Enum.OrderStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * 整个程序构建多个状态机
 */
@Component
public class OrderStateMachineBuilder {
    private final static String MACHINEID = "orderMachine";

    public StateMachine<OrderStates, OrderEvents> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<OrderStates,OrderEvents> builder = StateMachineBuilder.builder();
        System.out.println("构建订单状态机");

        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINEID)
                .beanFactory(beanFactory);
        builder.configureStates()
                .withStates()
                .initial(OrderStates.UNPAID)
                .states(EnumSet.allOf(OrderStates.class));
        builder.configureTransitions()
                .withExternal()
                .source(OrderStates.UNPAID)
                .target(OrderStates.WAITING_FOR_RECEIVE)
                .event(OrderEvents.PAY)

                .and()
                .withExternal()
                .source(OrderStates.WAITING_FOR_RECEIVE)
                .target(OrderStates.DONE)
                .event(OrderEvents.RECEIVE);
        return builder.build();
    }
}
