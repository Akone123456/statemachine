package com.fscut.commonstatemachine.controller;

import com.fscut.commonstatemachine.Build.OrderStateMachineBuilder;
import com.fscut.commonstatemachine.Enum.OrderEvents;
import com.fscut.commonstatemachine.Enum.OrderStates;
import com.fscut.commonstatemachine.entity.Order;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.redis.RedisStateMachinePersister;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuxinwei
 * @date 2022/8/16 15:08
 */
@RestController
@RequestMapping("v1")
public class OrderController {
    @Autowired
    private StateMachine orderSingleMachine;

    @RequestMapping("test")
    public void test(){
        // 创建流程
        orderSingleMachine.start();

        // 触发PAY事件
        orderSingleMachine.sendEvent(OrderEvents.PAY);

        // 触发RECEIVE事件
        orderSingleMachine.sendEvent(OrderEvents.RECEIVE);

        // 获取最终状态
        System.out.println("最终状态：" + orderSingleMachine.getState().getId());

    }

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private OrderStateMachineBuilder orderStateMachineBuilder;

    @RequestMapping("test1")
    public void test1() throws Exception {
        System.out.println(Thread.currentThread().getName());
        StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
        System.out.println(stateMachine.getId());

        // 创建流程
        stateMachine.start();

        // 触发PAY事件
        stateMachine.sendEvent(OrderEvents.PAY);

        // 触发RECEIVE事件
        //stateMachine.sendEvent(OrderEvents.RECEIVE);

        //用message传递数据
        Order order = new Order();
        order.setId(1L);
        order.setName("张三");
        Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.RECEIVE).setHeader("order", order).setHeader("otherObj", "otherObjValue").build();
        stateMachine.sendEvent(message);

        // 获取最终状态
        System.out.println("最终状态：" + stateMachine.getState().getId());

    }



    @Resource(name="orderMemoryPersister")
    private StateMachinePersister<OrderStates,OrderEvents,String> orderMemorypersister;

    @RequestMapping("test2")
    public void test2() throws Exception {
        StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
        System.out.println(stateMachine.getId());

        // 创建流程
        stateMachine.start();

        // 触发PAY事件
        stateMachine.sendEvent(OrderEvents.PAY);

        // 触发RECEIVE事件
        //stateMachine.sendEvent(OrderEvents.RECEIVE);

        //用message传递数据
        Order order = new Order();
        order.setId(1L);
        order.setName("张三");

        // 持久化状态机 唯一id 作为key
        orderMemorypersister.persist(stateMachine,order.getId().toString());

        // 取数据
        orderMemorypersister.restore(stateMachine, order.getId().toString());


        System.out.println("恢复状态机后的状态为：" + stateMachine.getState().getId());

    }



    @Resource(name="orderRedisPersister")
    private RedisStateMachinePersister<OrderStates,OrderEvents> orderRedisPersister;
    /**
     * 状态机持久化到redis中
     * @throws Exception
     */
    @RequestMapping("persister")
    public void test3() throws Exception {
        StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
        // 创建流程
        stateMachine.start();
        Order order = new Order();
        order.setId(10L);
        Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.PAY).setHeader("order",order).build();
        stateMachine.sendEvent(message);
        // 持久化stateMachine
        orderRedisPersister.persist(stateMachine,order.getId().toString());

    }

    /**
     * 从redis中取出持久化的状态机
     * @throws Exception
     */
    @RequestMapping("restore")
    public void test4() throws Exception {
        StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
        orderRedisPersister.restore(stateMachine,"10");
        System.out.println("恢复状态机后的状态为：" + stateMachine.getState().getId());

    }

}
