package com.fscut.commonstatemachine.entity;

import com.fscut.commonstatemachine.Enum.OrderStates;
import lombok.Data;

import javax.persistence.Entity;

/**
 * @author liuxinwei
 * @date 2022/8/16 14:52
 */
@Data
@Entity
public class Order {
    private Long id;
    private OrderStates states;
    private String name;
}
