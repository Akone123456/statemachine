package com.fscut.commonstatemachine.controller;

import com.fscut.commonstatemachine.Enum.OrderEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
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
}
