package br.edu.unifei.ecoi09.course.main;

public class Course {


    private String id;
    private String description;
    private int totalHours;


    private Course(CourseBuilder courseBuilder) {
        this.id = courseBuilder.id;
        this.description = courseBuilder.description;
        this.totalHours = courseBuilder.totalHours;
    }


    public Course getCourse () { return this; }

    public int getTotalHours () {
        return this.totalHours ;
    }


    @Override
    public String toString () {
        return new String ( this . id + "\t" + this . description );
    }

    public static class CourseBuilder {
        private String id;
        private String description;
        private int totalHours;

        public CourseBuilder (String id, String description) {
            this.id = id;
            this.description = description;
        }
        public CourseBuilder totalHours (int totalHours) {
            this.totalHours = totalHours;
            return this;
        }
        public Course build () {
            return new Course(this);
        }
    }
}


