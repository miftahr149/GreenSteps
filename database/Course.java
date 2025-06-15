package database;

import java.util.ArrayList;

public class Course extends BaseRecord {

  public static Factory<Course> factory = new Factory<Course>() {
    @Override
    public String[] getSaveAttributes() {
      return new String[] { "code", "name", "credits" };
    }

    @Override
    public String getFilename() {
      return "course";
    }

    @Override
    public String getFileDirname() {
      return DatabaseConfiguration.setFileDirname(this.getFilename());
    }

    @Override
    public Course newInstance(CallbackRecord callback) {
      return new Course(callback);
    }
  };

  private String code;
  private String name;
  private int credits;

  public Course(CallbackRecord callback) {
    super(callback, factory);
  }

  public void setCode(String code) {
    super.callback.update(this);
    this.code = code;
  }

  public void setName(String name) {
    super.callback.update(this);
    this.name = name;
  }

  public void setCredits(int credits) {
    super.callback.update(this);
    this.credits = credits;
  }

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public int getCredits() {
    return this.credits;
  }

  public ArrayList<Student> getEnrolledStudent() {
    ArrayList<Student> returnValue = super.referenceSubscriber.get(Student.class);
    return returnValue;
  }
}
