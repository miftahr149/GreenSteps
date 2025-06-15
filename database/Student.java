package database;

public class Student extends BaseRecord {
  private String name;
  private double gpa;
  private Reference<Course> enrolled;

  public static Factory<Student> factory = new Factory<Student>() {
    @Override
    public String[] getSaveAttributes() {
      return new String[] { "name", "gpa", "enrolled" };
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
    this.enrolled = new Reference<Course>("course", this);
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

  public void addCourse(Course course) {
    super.callback.update(this);
    this.enrolled.add(course.getID());
  }
}
