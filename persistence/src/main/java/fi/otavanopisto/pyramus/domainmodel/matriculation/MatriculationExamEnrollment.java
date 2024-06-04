package fi.otavanopisto.pyramus.domainmodel.matriculation;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.DocumentId;

import fi.otavanopisto.pyramus.domainmodel.students.Student;
import fi.otavanopisto.pyramus.matriculation.MatriculationExamEnrollmentState;

@Entity // TODO constraint student+exam
public class MatriculationExamEnrollment {

  /**
   * Returns the unique identifier of this object.
   * 
   * @return The unique identifier of this object
   */
  public Long getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Long getNationalStudentNumber() {
    return nationalStudentNumber;
  }

  public void setNationalStudentNumber(Long nationalStudentNumber) {
    this.nationalStudentNumber = nationalStudentNumber;
  }

  public String getGuider() {
    return guider;
  }

  public void setGuider(String guider) {
    this.guider = guider;
  }

  public int getNumMandatoryCourses() {
    return numMandatoryCourses;
  }

  public void setNumMandatoryCourses(int numMandatoryCourses) {
    this.numMandatoryCourses = numMandatoryCourses;
  }

  public boolean isRestartExam() {
    return restartExam;
  }

  public void setRestartExam(boolean restartExam) {
    this.restartExam = restartExam;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SchoolType getEnrollAs() {
    return enrollAs;
  }

  public void setEnrollAs(SchoolType enrollAs) {
    this.enrollAs = enrollAs;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isCanPublishName() {
    return canPublishName;
  }

  public void setCanPublishName(boolean canPublishName) {
    this.canPublishName = canPublishName;
  }

  public MatriculationExamEnrollmentState getState() {
    return state;
  }

  public void setState(MatriculationExamEnrollmentState state) {
    this.state = state;
  }
  
  public Date getEnrollmentDate() {
    return enrollmentDate;
  }

  public void setEnrollmentDate(Date enrollmentDate) {
    this.enrollmentDate = enrollmentDate;
  }

  public Integer getCandidateNumber() {
    return candidateNumber;
  }

  public void setCandidateNumber(Integer candidateNumber) {
    this.candidateNumber = candidateNumber;
  }

  public DegreeType getDegreeType() {
    return degreeType;
  }

  public void setDegreeType(DegreeType degreeType) {
    this.degreeType = degreeType;
  }

  public boolean isApprovedByGuider() {
    return approvedByGuider;
  }

  public void setApprovedByGuider(boolean approvedByGuider) {
    this.approvedByGuider = approvedByGuider;
  }

  public MatriculationExam getExam() {
    return exam;
  }

  public void setExam(MatriculationExam exam) {
    this.exam = exam;
  }

  public MatriculationExamEnrollmentDegreeStructure getDegreeStructure() {
    return degreeStructure;
  }

  public void setDegreeStructure(MatriculationExamEnrollmentDegreeStructure degreeStructure) {
    this.degreeStructure = degreeStructure;
  }

  @Id
  @GeneratedValue(strategy=GenerationType.TABLE, generator="MatriculationExamEnrollment")  
  @TableGenerator(name="MatriculationExamEnrollment", allocationSize=1, table = "hibernate_sequences", pkColumnName = "sequence_name", valueColumnName = "sequence_next_hi_value")
  @DocumentId 
  private Long id;
 
  @ManyToOne
  private MatriculationExam exam;
  
  @Column
  private String name;
  
  @Column
  private String ssn;
  
  @Column
  private String email;
  
  @Column
  private String phone;
  
  @Column
  private String address;
  
  @Column
  private String postalCode;
  
  @Column
  private String city;
  
  @Column
  private Long nationalStudentNumber;
  
  @Column
  private String guider;
  
  @Column
  @Enumerated(EnumType.STRING)
  private SchoolType enrollAs;
  
  @Column
  @Enumerated(EnumType.STRING)
  private DegreeType degreeType;
  
  @Column
  private int numMandatoryCourses;
  
  @Column(nullable = false)
  private boolean restartExam;
  
  @Column
  private String location;
  
  @Lob
  @Basic (fetch = FetchType.LAZY)
  @Column
  private String message;
  
  @Column(nullable = false)
  private boolean canPublishName;

  @Column(nullable = false)
  private boolean approvedByGuider;
  
  @ManyToOne
  private Student student;
  
  @Column
  private Integer candidateNumber;
  
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MatriculationExamEnrollmentState state;
  
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MatriculationExamEnrollmentDegreeStructure degreeStructure;
  
  @Column(nullable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date enrollmentDate;

}