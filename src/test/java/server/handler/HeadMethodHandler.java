package server.handler;

import curly.Method;

public class HeadMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.HEAD;
    }

}