package database;

@FunctionalInterface
interface ParserFunction<T> {
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

  private ParserFunction<?> func;

  PrimitiveParser(ParserFunction<?> func) {
    this.func = func;
  };

  public Object parse(String strValue) {
    return this.func.apply(strValue);
  }
}
