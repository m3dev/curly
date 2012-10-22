package server.handler;

import com.m3.curly.Method;

public class HeadMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.HEAD;
    }

}