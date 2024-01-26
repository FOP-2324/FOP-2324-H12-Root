package h12.h4;

import h12.parse.FsmParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Util {
    public static Method getPrivateParserMethod(String name) throws NoSuchMethodException {
        Method method = FsmParser.class.getDeclaredMethod(name);
        method.setAccessible(true);
        return method;
    }

    public static void callAndUnpackException(Method method, FsmParser parser) throws Throwable {
        try {
            method.invoke(parser);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
