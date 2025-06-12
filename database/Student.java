package database;

public class Student extends BaseRecord {
  private String name;
  private double gpa;

  public static Factory<Student> factory = new Factory<Student>() {
    @Override
    public String[] getSaveAttributes() {
      return new String[] {"name", "gpa"};
    }

    @Override
    public String getFileDirname() {
      return DatabaseConfiguration.setFileDirname(this.getFilename());
    }

    @Override
    public Student newInstance(CallbackRecord callback) {
      return new Student(callback);
    }

    @Override
    public String getFilename() {
      return "student";
    }
  };

  public Student(CallbackRecord callback) {
    super(callback, Student.factory);
  }

  public String getName() {
    return this.name;
  }

  public double getGpa() {
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
