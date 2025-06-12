package database;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class ObjectParser {

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

  static void decode(Object obj, Map<String, String> mapSavedAttribute, String encodeStr) {
    Class<?> _class = obj.getClass();

    for (Map.Entry<String, String> entry : mapSavedAttribute.entrySet()) {
      String strAttribute = entry.getKey();
      String objAttribute = entry.getValue();
      try {
        Field field = _class.getDeclaredField(objAttribute);
        field.setAccessible(true);
        String fieldValueStr = getFieldValue(encodeStr, strAttribute);
        if (field.getType().isPrimitive()) {
          PrimitiveParser parser =
              PrimitiveParser.valueOf(field.getType().toString().toUpperCase());
          field.set(obj, parser.parse(fieldValueStr));
        } else if (field.getType() == String.class) {
          field.set(obj, fieldValueStr);
        }
        field.setAccessible(false);
      } catch (NoSuchFieldException e) {
        System.err.printf("There is no such thing as field %s in %s", objAttribute, _class);
      } catch (IllegalAccessException e) {
        System.err.printf("Unable to access field %s", objAttribute);
      }
    }
  }
}
