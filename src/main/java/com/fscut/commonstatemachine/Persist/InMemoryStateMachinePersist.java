package com.fscut.commonstatemachine.Persist;

import com.fscut.commonstatemachine.Enum.OrderEvents;
import com.fscut.commonstatemachine.Enum.OrderStates;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryStateMachinePersist implements StateMachinePersist<OrderStates, OrderEvents,String> {

    private Map<String,StateMachineContext<OrderStates,OrderEvents> > map = new HashMap<String,StateMachineContext<OrderStates,OrderEvents>>();
    @Override
    public void write(StateMachineContext<OrderStates, OrderEvents> stateMachineContext, String contextObj) throws Exception {
        map.put(contextObj,stateMachineContext);
    }

    @Override
    public StateMachineContext<OrderStates, OrderEvents> read(String contextObj) throws Exception {
        return map.get(contextObj);
    }
}
