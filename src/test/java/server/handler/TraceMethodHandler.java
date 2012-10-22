package server.handler;

import curly.Method;

public class TraceMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.TRACE;
    }

}
