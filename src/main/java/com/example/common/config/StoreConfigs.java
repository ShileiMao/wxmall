package com.example.common.config;

import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import javax.lang.model.type.PrimitiveType;
import java.lang.reflect.Type;

public class StoreConfigs {
    public static enum Config {
        FREE_DISPATCH_AMOUNT("FREE_DISPATCH_AMOUNT", Float.TYPE),
        DISPATCH_FEE("DISPATCH_FEE", Float.TYPE),
        DISPATCH_DISTANCE("DISPATCH_DISTANCE", Float.TYPE),
        STORE_ADDR("STORE_ADDR", String.class);

        public String name;
        public Type type;

        Config(String name, Type type) {
            this.name = name;
            this.type = type;
        }
    }

    public static <T> T convertToType(String value, Class<T> type) {
        if (type == Integer.TYPE) {
            return (T) new Integer(Integer.parseInt(value));
        } else if (type == Float.TYPE) {
            return (T) new Float(Float.parseFloat(value));
        } else if (type == Double.TYPE) {
            return (T) new Double(Double.parseDouble(value));
        } else if (type == Boolean.TYPE) {
            return (T) new Boolean(Boolean.parseBoolean(value));
        } else if (type == Character.TYPE) {
            return (T) new Character(value.charAt(0));
        } else if (type == Short.TYPE) {
            return (T) new Short(Short.parseShort(value));
        } else if (type == Byte.TYPE) {
            return (T) new Byte(Byte.parseByte(value));
        } else if (type == String.class) {
            return (T) value;
        }
        return null;
    }
}
