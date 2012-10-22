package server.handler;

import com.m3.curly.Method;

public class TraceMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.TRACE;
    }

}
