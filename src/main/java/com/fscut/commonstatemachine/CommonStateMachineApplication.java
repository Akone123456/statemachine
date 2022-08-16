package com.fscut.commonstatemachine;

import com.fscut.commonstatemachine.Enum.OrderEvents;
import com.fscut.commonstatemachine.Enum.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class CommonStateMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonStateMachineApplication.class, args);
    }





}
