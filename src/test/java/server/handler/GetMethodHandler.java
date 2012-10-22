package server.handler;

import com.m3.curly.Method;

public class GetMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.GET;
    }

}
