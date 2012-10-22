package server.handler;

import com.m3.curly.Method;

public class DeleteMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.DELETE;
    }

}
