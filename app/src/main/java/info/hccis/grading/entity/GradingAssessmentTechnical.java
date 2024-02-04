package info.hccis.grading.entity;
import java.io.Serializable;
public class GradingAssessmentTechnical implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String studentName;

    private String instructorName;

    private String courseName;

    private String courseRoom;
    //@Max(value=100)  @Min(value=0)//if you know range of your decimal fields consider using these annotations to enforce field validation

    private Double numericGrade;

    private String letterGrade;

    private Integer academicYear;

    public GradingAssessmentTechnical() {
        numericGrade=0.0;
        academicYear=0;

    }

    public GradingAssessmentTechnical(Integer id) {
        this.id = id;
    }

    public GradingAssessmentTechnical(Integer id, String studentName, String instructorName, String courseName, String courseRoom) {
        this.id = id;
        this.studentName = studentName;
        this.instructorName = instructorName;
        this.courseName = courseName;
        this.courseRoom = courseRoom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public Double getNumericGrade() {
        return numericGrade;
    }

    public void setNumericGrade(Double numericGrade) {
        this.numericGrade = numericGrade;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GradingAssessmentTechnical)) {
            return false;
        }
        GradingAssessmentTechnical other = (GradingAssessmentTechnical) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        String output="--Assessment Details--" +
                " \nStudentName: " + getStudentName()+
                " \n CourseName: " + getCourseName() +
                " \n CourseRoom: " + getCourseRoom() +
                " \n InstructorName: " + getInstructorName() +
                " \n AcademicYear: " + getAcademicYear() +
                " \n NumericGrade: " + getNumericGrade()+
                "\n LetterGrade:"+getLetterGrade();
        return output;
    }


}

