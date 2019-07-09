package org.zongzi.platform.operation.socket.service;

import java.util.LinkedList;
import java.util.List;

public class CompositeExecuteService implements ExecuteService {

    private List<ExecuteService> executeServices = new LinkedList<>();

    public CompositeExecuteService() {
        add(new ClockExecuteService());
    }

    private void add(ExecuteService executeService) {
        executeServices.add(executeService);
    }

    @Override
    public Object apply(Byte flag, String body) {
        for (ExecuteService executeService : executeServices) {
            Object result;
            if ((result = executeService.apply(flag, body)) != null) {
                return result;
            }
        }
        return null;
    }
}
