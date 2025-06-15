package database;

@FunctionalInterface
interface PrimitiveParserFunction<T> {
  public T apply(String strValue);
}

class Char {
  static char parseChar(String strValue) {
    return strValue.charAt(0);
  }
}

enum PrimitiveParser {
  INT(Integer::parseInt), FLOAT(Float::parseFloat), DOUBLE(Double::parseDouble), BOOLEAN(Boolean::parseBoolean),
  BYTE(Byte::parseByte), SHORT(Short::parseShort), CHAR(Char::parseChar);

  private PrimitiveParserFunction<?> func;

  PrimitiveParser(PrimitiveParserFunction<?> func) {
    this.func = func;
  };

  public Object parse(String strValue) {
    return this.func.apply(strValue);
  }
}
