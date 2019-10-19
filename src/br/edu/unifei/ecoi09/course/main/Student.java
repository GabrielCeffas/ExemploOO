package br.edu.unifei.ecoi09.course.main;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    private double totalIndex;
    private List<Course> courseList = new ArrayList<>();

    private Student(StudentBuilder studentBuilder) {
        super(studentBuilder.getCPF(),
                studentBuilder.getName());

        this.birthDate = studentBuilder.getBirthDate();
        this.totalIndex = studentBuilder.totalIndex;
    }

    public static class StudentBuilder extends Person {

        private double totalIndex;

        public StudentBuilder(String CPF, String name) {
            super(CPF, name);
            totalIndex = 0;
        }

        public StudentBuilder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public StudentBuilder totalIndex(double totalIndex) {
            this.totalIndex = totalIndex;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    public List<Course> getCourseList() {

        return courseList;
    }


    public void addCourse(Course course){
        courseList.add(course);

    }
    public void removeCourse(Course course){
        courseList.remove(course);
    }
    public boolean hasCourse(Course course){
        boolean bool = false;
        for(int i=0;i<=courseList.size()-1;i++){
            if(courseList.get(i)==course){
                bool = true;
            }
        }
        return bool;
    }

    public double getTotalIndex() {
        return this.totalIndex;
    }
}

