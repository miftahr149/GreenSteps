package database;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
interface ObjectParserFunction<T> {
  T apply(String strRecord, Object obj);
}


class ObjectParser {

  private enum Parser {
    REFERENCE(Reference.class, Reference::parseReference), DATE(CustomDate.class,
        DateParser::parse);

    private ObjectParserFunction<?> func;
    private Class<?> classType;

    private Parser(Class<?> classType, ObjectParserFunction<?> func) {
      this.func = func;
      this.classType = classType;
    }

    public Object parse(String str, Object obj) {
      return this.func.apply(str, obj);
    }

    @Override
    public String toString() {
      return this.classType.toString();
    }

    public static Parser classOf(String className) {
      Parser returnValue = null;
      for (Parser constant : Parser.values()) {
        if (constant.classType.toString().equals(className)) {
          returnValue = constant;
          break;
        }
      }
      return returnValue;
    }
  }

  static String encode(Object obj, String[] attributes) {
    StringBuilder result = new StringBuilder();
    Class<?> _class = obj.getClass();

    for (String attributeName : attributes) {
      try {
        Field field = _class.getDeclaredField(attributeName);
        field.setAccessible(true);
        Object fieldValue = field.get(obj);

        result.append(String.format("{name:%s,value:", attributeName) + fieldValue + "}");
        field.setAccessible(false);
      } catch (NoSuchFieldException e) {
        System.out.printf("Can't find field %s \n", attributeName);
      } catch (IllegalAccessException e) {
        System.err.printf("Unable to access field %s \n", attributeName);
      }
    }
    return result.toString();
  }

  static private String getFieldValue(String encodeStr, String fieldName) {
    String result = "";
    String nameLabel = String.format("{name:%s,", fieldName);
    String valueLabel = "value:";

    int indexName = encodeStr.indexOf(nameLabel);
    if (indexName == -1) {
      System.out.printf("unable to find field %s\n", fieldName);
      return result;
    }
    int indexStart = encodeStr.indexOf(valueLabel, indexName + nameLabel.length());
    indexStart += valueLabel.length();
    int indexEnd = encodeStr.indexOf('}', indexName + nameLabel.length());
    result = encodeStr.substring(indexStart, indexEnd);
    return result;
  }

  static void decode(Object obj, String[] attributes, String encodeStr) {
    Map<String, String> mapSavedAttribute = new HashMap<String, String>();
    for (String attribute : attributes) {
      mapSavedAttribute.put(attribute, attribute);
    }
    ObjectParser.decode(obj, mapSavedAttribute, encodeStr);
  }

  private static boolean isParserAvailable(String findClassName) {
    boolean returnValue = false;
    for (Parser parser : Parser.values()) {
      if (parser.toString().equals(findClassName)) {
        returnValue = true;
        break;
      }
    }
    return returnValue;
  }

  static void decode(Object obj, Map<String, String> mapSavedAttribute, String encodeStr) {
    Class<?> _class = obj.getClass();

    for (Map.Entry<String, String> entry : mapSavedAttribute.entrySet()) {
      String strAttribute = entry.getKey();
      String objAttribute = entry.getValue();
      try {
        Field field = _class.getDeclaredField(objAttribute);
        field.setAccessible(true);
        String fieldValueStr = getFieldValue(encodeStr, strAttribute);

        String className = field.getType().toString();

        if (field.getType().isPrimitive()) {
          PrimitiveParser parser = PrimitiveParser.valueOf(className.toUpperCase());
          field.set(obj, parser.parse(fieldValueStr));
        } else if (field.getType() == String.class) {
          field.set(obj, fieldValueStr);
        } else if (isParserAvailable(className)) {
          Parser parser = Parser.classOf(className);
          field.set(obj, parser.parse(fieldValueStr, obj));
        } else {
          System.err.printf("Unable to decode attribute %s with type %s\n", objAttribute,
              field.getType().toString());
        }
        field.setAccessible(false);
      } catch (NoSuchFieldException e) {
        System.err.printf("There is no such thing as field %s in %s\n", objAttribute, _class);
      } catch (IllegalAccessException e) {
        System.err.printf("Unable to access field %s\n", objAttribute);
      }
    }
  }
}
