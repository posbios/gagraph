package com.bitexception.rest.filter;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @param <T>
 */
public class Filter<T> {

    protected final String[] filters;

    public Filter(String... filters) {
        this.filters = filters;
    }

    private static void setNullOrDefaultValue(Field field, Object object) throws Exception {
        if (!field.getType().isPrimitive()) {
            field.set(object, null);
        } else {
            Class objClass = field.getType();
            if (int.class.equals(objClass) || long.class.equals(objClass)
                    || short.class.equals(objClass) || byte.class.equals(objClass)) {
                field.set(object, 0);
            } else if (float.class.equals(objClass) || double.class.equals(objClass)) {
                field.set(object, 0d);
            } else if (boolean.class.equals(objClass)) {
                field.set(object, false);
            } else if (char.class.equals(objClass)) {
                field.set(object, '\u0000');
            }
        }
    }

    public final void filter(T object) {
        filter(object, filters);
    }

    /**
     *
     * @param object
     * @param cleans
     */
    public final void filter(T object, String... cleans) {
        Field declaredField;
        boolean isAccessible;

        declaredField = null;
        for (String clean : cleans) {
            try {
                declaredField = object.getClass().getField(clean);
                isAccessible = declaredField.isAccessible();
                declaredField.setAccessible(true);
                Filter.setNullOrDefaultValue(declaredField, object);
                declaredField.setAccessible(isAccessible);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            } catch (Exception ex) {
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
