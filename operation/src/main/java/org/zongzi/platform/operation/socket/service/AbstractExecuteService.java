package org.zongzi.platform.operation.socket.service;

abstract class AbstractExecuteService implements ExecuteService {

    private final byte flag;

    AbstractExecuteService(byte flag) {
        this.flag = flag;
    }

    @Override
    public Object apply(Byte flag, String body) {
        if (this.flag == flag) {
            return execute(body);
        }
        return null;
    }

    abstract Object execute(String body);
}
