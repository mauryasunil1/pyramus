package fi.pyramus.rest;

import static com.jayway.restassured.RestAssured.given;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.jayway.restassured.response.Response;

import fi.pyramus.rest.controller.permissions.PersonPermissions;
import fi.pyramus.rest.controller.permissions.StudentPermissions;
import fi.pyramus.rest.model.Person;
import fi.pyramus.rest.model.Sex;

@RunWith(Parameterized.class)
public class PersonPermissionTestsIT extends AbstractRESTPermissionsTest {

  public PersonPermissionTestsIT(String role) {
    this.role = role;
  }
  
  /*
   * This method is called the the JUnit parameterized test runner and returns a
   * Collection of Arrays. For each Array in the Collection, each array element
   * corresponds to a parameter in the constructor.
   */
  @Parameters
  public static List<Object[]> generateData() {
    return getGeneratedRoleData();
  }
  
  private PersonPermissions personPermissions = new PersonPermissions();
  private StudentPermissions studentPermissions = new StudentPermissions();

  @Test
  public void testCreatePerson() throws NoSuchFieldException {
    Person person = new Person(null, getDate(1990, 6, 6), "1234567-0987", Sex.FEMALE, false, "to be created");
    
    Response response = given().headers(getAuthHeaders())
      .contentType("application/json")
      .body(person)
      .post("/students/persons");

    assertOk(response, personPermissions, PersonPermissions.CREATE_PERSON);

    if (response.getStatusCode() == 200) {
      int id = response.body().jsonPath().getInt("id");
      
      given().headers(getAuthHeaders())
        .delete("/students/persons/{ID}", id);
    }
  }
  
  @Test
  public void testListPersons() throws NoSuchFieldException {
    Response response = given().headers(getAuthHeaders())
      .get("/students/persons");
    
    assertOk(response, personPermissions, PersonPermissions.LIST_PERSONS);
  }
  
  @Test
  public void testFindPerson() throws NoSuchFieldException {
    Response response = given().headers(getAuthHeaders())
      .get("/students/persons/{ID}", 3);
    
    assertOk(response, personPermissions, PersonPermissions.FIND_PERSON);
  }
  
  @Test
  public void testUpdatePerson() throws NoSuchFieldException {
    Person person = new Person(null, getDate(1990, 6, 6), "1234567-0987", Sex.FEMALE, false, "not updated");
    
    Response response = given().headers(getAdminAuthHeaders())
      .contentType("application/json")
      .body(person)
      .post("/students/persons");

    Long id = new Long(response.body().jsonPath().getInt("id"));
    try {
      Person updateStudent = new Person(id, getDate(1991, 7, 7), "1234567-9876", Sex.MALE, true, "updated");

      response = given().headers(getAuthHeaders())
        .contentType("application/json")
        .body(updateStudent)
        .put("/students/persons/{ID}", id);

      assertOk(response, personPermissions, PersonPermissions.UPDATE_PERSON);
    } finally {
      given().headers(getAdminAuthHeaders())
        .delete("/students/persons/{ID}", id);
    }
  }
  
  @Test
  public void testDeletePerson() throws NoSuchFieldException {
    Person person = new Person(null, getDate(1990, 6, 6), "1234567-0987", Sex.FEMALE, false, "to be deleted");
    
    Response response = given().headers(getAdminAuthHeaders())
      .contentType("application/json")
      .body(person)
      .post("/students/persons");

    int id = response.body().jsonPath().getInt("id");

    response = given().headers(getAuthHeaders())
      .delete("/students/persons/{ID}", id);
    
    assertOk(response, personPermissions, PersonPermissions.DELETE_PERSON, 204);
    
    if (response.getStatusCode() != 204) {
      given().headers(getAdminAuthHeaders())
        .delete("/students/persons/{ID}", id);
    }
  }
  
  @Test
  public void testListStudents() throws NoSuchFieldException {
    Response response = given().headers(getAuthHeaders())   
      .get("/students/persons/{ID}/students", 3l);
    
    assertOk(response, studentPermissions, StudentPermissions.LIST_STUDENTSBYPERSON);
  }
}
