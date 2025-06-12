package database;

public class Student extends BaseRecord {
  private String name;
  private double gpa;

  public static class StudentFactory implements Factory<Student> {
    @Override
    public String[] getSaveAttributes() {
      return new String[] {
          "name",
          "gpa"
      };
    }

    @Override
    public String getFileDirname() {
      return "Storage/Student.txt";
    }

    @Override
    public Student newInstance(CallbackRecord callback) {
      return new Student(callback);
    }
  }

  public Student(CallbackRecord callback) {
    super(callback, new Student.StudentFactory());
  }

  public String getName() {
    return this.name;
  }

  public double getGpa() {
    this.callback.update(this);
    return this.gpa;
  }

  public void setName(String name) {
    super.callback.update(this);
    this.name = name;
  }

  public void setGpa(double gpa) {
    super.callback.update(this);
    this.gpa = gpa;
  }
}
