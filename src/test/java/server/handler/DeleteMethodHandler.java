package server.handler;

import curly.Method;

public class DeleteMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.DELETE;
    }

}
