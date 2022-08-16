package com.fscut.commonstatemachine.config;

/**
 * @author liuxinwei
 * @date 2022/8/16 10:02
 */

import com.fscut.commonstatemachine.Enum.OrderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.config.EnableStateMachine;

/**
 * 状态机事件配置
 */
@WithStateMachine(id = "orderMachine")
public class OrderSingleEventConfig {


    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 当前状态UNPAID
     */
    @OnTransition(target = "UNPAID")
    public void create() {
        logger.info("---订单创建，待支付---");
    }

    /**
     * UNPAID->WAITING_FOR_RECEIVE 执行的动作
     */
    @OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void pay(Message<OrderEvents> message) {

        logger.info("---用户完成支付，待收货---" );
    }

    /**
     * WAITING_FOR_RECEIVE->DONE 执行的动作
     */
    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive() {
        logger.info("---用户已收货，订单完成---");
    }


}
