package server.handler;

import curly.Method;

public class GetMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.GET;
    }

}
