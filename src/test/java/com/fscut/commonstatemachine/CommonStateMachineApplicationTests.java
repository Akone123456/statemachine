package com.fscut.commonstatemachine;

import com.fscut.commonstatemachine.Enum.OrderEvents;
import com.fscut.commonstatemachine.Enum.OrderStates;

import com.fscut.commonstatemachine.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

import javax.annotation.Resource;

@SpringBootTest
class CommonStateMachineApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private StateMachine orderSingleMachine;



    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void test01() throws Exception {


        // 创建流程
        orderSingleMachine.start();
        // 出发PAY事件
        orderSingleMachine.sendEvent(OrderEvents.PAY);

        // 触发RECEIVE事件
        orderSingleMachine.sendEvent(OrderEvents.RECEIVE);

        // 获取最终状态
        System.out.println("最终状态：" + orderSingleMachine.getState().getId());

    }


    @Test
    public void test02(){

        Order order = new Order();
        order.setName("订单1");
        order.setId(10L);
        Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.PAY).setHeader("order",order).build();
        orderSingleMachine.start();
        orderSingleMachine.sendEvent(message);
    }

    @Test
    public void test03(){

        Order order = new Order();
        order.setName("订单1");
        order.setId(10L);
        Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.PAY).setHeader("order",order).build();
        orderSingleMachine.start();
        orderSingleMachine.sendEvent(message);
    }
}
