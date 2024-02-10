package h12.h5;

import h12.parse.FsmBuilderImpl;

import java.lang.reflect.Field;

public class Util {
    public static Field getPrivateField(String name) throws NoSuchFieldException {
        Field f = FsmBuilderImpl.class.getDeclaredField(name);
        f.setAccessible(true);
        return f;
    }
}
