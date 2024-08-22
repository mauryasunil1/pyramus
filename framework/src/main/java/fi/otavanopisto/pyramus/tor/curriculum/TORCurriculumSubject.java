package fi.otavanopisto.pyramus.tor.curriculum;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TORCurriculumSubject {

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<TORCurriculumModule> getModules() {
    return modules;
  }

  public void setModules(List<TORCurriculumModule> modules) {
    this.modules = modules;
  }

  public List<String> getIncludedSubjects() {
    return includedSubjects;
  }

  public void setIncludedSubjects(List<String> includedSubjects) {
    this.includedSubjects = includedSubjects;
  }

  /**
   * Returns sum of module lengths for modules that are marked as mandatory.
   * 
   * @return sum of module lengths for modules that are marked as mandatory
   */
  public int getMandatoryModuleLengthSum() {
    if (this.modules == null) {
      return 0;
    }
    
    // TODO Curriculum has no indication what unit type the length is and also it is in integer format
    //      while it probably should be a decimal number, as that's what the course credit points are.
    return this.modules.stream()
        .filter(TORCurriculumModule::isMandatory)
        .mapToInt(TORCurriculumModule::getLength)
        .sum();
  }
  
  private String name;
  private String code;
  private List<TORCurriculumModule> modules;
  @JsonProperty(value = "included-subjects", required = false)
  private List<String> includedSubjects;
}
