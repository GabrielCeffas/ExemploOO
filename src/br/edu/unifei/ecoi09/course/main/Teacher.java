package br.edu.unifei.ecoi09.course.main;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    private List <Course> courseList = new ArrayList <>();
    private int totalHoursCourses;
    private double salary;

    private Teacher ( TeacherBuilder teacherBuilder ) {
        super (teacherBuilder.getCPF (), teacherBuilder.getName());
        this.salary = teacherBuilder.salary;
    }

    public void addCourse ( Course course ) {
        courseList.add(course);
    }
    public void removeCourse(Course course){
        courseList.remove(course);
    }
    public List <Course> getCourseList () {
        return courseList;
    }
    public int getTotalHoursCourses () {
        int totalHours = 0;
        for (Course c : courseList)
            totalHours += c.getCourse().getTotalHours();
        return totalHours;
    }

    public static class TeacherBuilder extends Person {

        private double salary;

        public TeacherBuilder (String CPF, String name) {
            super(CPF, name);
        }
        public TeacherBuilder salary (double salary) {
            this.salary = salary;
            return this;
        }
        public Teacher build () {
            return new Teacher( this);
        }

    }
}