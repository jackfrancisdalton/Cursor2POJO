package jackfrancisdalton.com.cursor2pojo;

import android.database.Cursor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class C2PMapper {

    public static Object execute(final Cursor cursor, final ResultDescriber erd) {
        Object returnObject = null;

        if(ResultDescriber.StructureType.list == erd.getStructureType())
            returnObject = cursorToList(cursor, erd.getDesiredClass());
        else if(ResultDescriber.StructureType.object == erd.getStructureType())
            returnObject = cursorToObject(cursor, erd.getDesiredClass());

        return returnObject;
    }

    private static <T> List<T> cursorToList(final Cursor cursor, final Class<T> expectedClass) {
        List<T> allSections = new ArrayList<>();
        Class klass = expectedClass;

        //TODO: change so fetching class details is done once, instead of on each item
        while (cursor.moveToNext()) {
            try {
                T objectInstance = (T) klass.newInstance();
                mapCursorToObjFields(cursor, klass, objectInstance);
                allSections.add(objectInstance);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return allSections;
    }

    private static <T> T cursorToObject(final Cursor cursor, final Class<T> expectedClass) {
        cursor.moveToNext();
        T objectInstance = null;

        try {
            objectInstance = (T) expectedClass.newInstance();
            objectInstance = mapCursorToObjFields(cursor, expectedClass, objectInstance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return objectInstance;
    }

    private static <T> T mapCursorToObjFields(Cursor cursor, Class klass, T objectInstance) throws IllegalAccessException {
        while (klass != Object.class) {
            for (Field field : klass.getDeclaredFields()) {
                if (field.isAnnotationPresent(C2PColumnInfo.class)) {
                    String columnName = field.getAnnotation(C2PColumnInfo.class).name();
                    Class type = field.getType();
                    field.setAccessible(true);

                    if (String.class == type) {
                        String val = cursor.getString(cursor.getColumnIndex(columnName));
                        field.set(objectInstance, val);
                    } else if (int.class == type) {
                        int val = cursor.getInt(cursor.getColumnIndex(columnName));
                        field.set(objectInstance, val);
                    } else if (boolean.class == type) {
                        boolean val = cursor.getInt(cursor.getColumnIndex(columnName)) > 0;
                        field.set(objectInstance, val);
                    } else {
                        try {
                            throw new Exception("unsupported property type, could not map from cursor");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            klass = klass.getSuperclass();
        }
        return  objectInstance;
    }

    public static String propertyNameToColumnName(String propertyName) {
        String columnName = "";
        String[] s = propertyName.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
        for (int i = 0; i < s.length; i++) {
            if(i == s.length - 1)
                columnName += s[i].toLowerCase();
            else
                columnName += s[i].toLowerCase() + "_";
        }
        return columnName;
    }
}