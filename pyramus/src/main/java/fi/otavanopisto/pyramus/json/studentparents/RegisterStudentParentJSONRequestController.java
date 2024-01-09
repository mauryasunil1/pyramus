package fi.otavanopisto.pyramus.json.studentparents;

import org.apache.commons.lang3.StringUtils;

import fi.internetix.smvc.controllers.JSONRequestContext;
import fi.otavanopisto.pyramus.I18N.Messages;
import fi.otavanopisto.pyramus.dao.DAOFactory;
import fi.otavanopisto.pyramus.dao.base.ContactTypeDAO;
import fi.otavanopisto.pyramus.dao.base.EmailDAO;
import fi.otavanopisto.pyramus.dao.users.StudentParentChildDAO;
import fi.otavanopisto.pyramus.dao.users.StudentParentDAO;
import fi.otavanopisto.pyramus.dao.users.StudentParentRegistrationDAO;
import fi.otavanopisto.pyramus.dao.users.UserIdentificationDAO;
import fi.otavanopisto.pyramus.domainmodel.base.ContactType;
import fi.otavanopisto.pyramus.domainmodel.base.Organization;
import fi.otavanopisto.pyramus.domainmodel.users.StudentParent;
import fi.otavanopisto.pyramus.domainmodel.users.StudentParentRegistration;
import fi.otavanopisto.pyramus.domainmodel.users.User;
import fi.otavanopisto.pyramus.framework.JSONRequestController;
import fi.otavanopisto.pyramus.framework.UserRole;
import fi.otavanopisto.pyramus.framework.UserUtils;
import fi.otavanopisto.pyramus.plugin.auth.AuthenticationException;
import fi.otavanopisto.pyramus.plugin.auth.AuthenticationProviderVault;
import fi.otavanopisto.pyramus.plugin.auth.InternalAuthenticationProvider;

/**
 * The controller responsible of logging in the user with the credentials he has provided. 
 * 
 * @see fi.otavanopisto.pyramus.views.users.LoginViewController
 */
public class RegisterStudentParentJSONRequestController extends JSONRequestController {
  
  private static final String FORMTYPE_CREATE = "CREATE";
  private static final String FORMTYPE_LOGIN = "LOGIN";
  private static final String FORMTYPE_LOGGEDIN = "LOGGEDIN";
  
  public void process(JSONRequestContext requestContext) {
    InternalAuthenticationProvider internalAuthenticationProvider = AuthenticationProviderVault.getInstance().getInternalAuthenticationProvider("internal");
    if (internalAuthenticationProvider == null) {
      fail(requestContext, "Operation not available.");
      return;
    }

    ContactTypeDAO contactTypeDAO = DAOFactory.getInstance().getContactTypeDAO();
    EmailDAO emailDAO = DAOFactory.getInstance().getEmailDAO();
    StudentParentDAO studentParentDAO = DAOFactory.getInstance().getStudentParentDAO();
    StudentParentChildDAO studentParentChildDAO = DAOFactory.getInstance().getStudentParentChildDAO();
    StudentParentRegistrationDAO studentParentRegistrationDAO = DAOFactory.getInstance().getStudentParentRegistrationDAO();
    UserIdentificationDAO userIdentificationDAO = DAOFactory.getInstance().getUserIdentificationDAO();

    String hash = requestContext.getString("hash");
    String formType = StringUtils.trim(requestContext.getString("type"));
    String ssnConfirm = StringUtils.trim(requestContext.getString("ssn-confirm"));
    
    StudentParentRegistration studentParentRegistration = studentParentRegistrationDAO.findByHash(hash);
    
    // Validate
    if (studentParentRegistration == null || StringUtils.isBlank(ssnConfirm) || !StringUtils.equals(ssnConfirm, studentParentRegistration.getStudent().getPerson().getSocialSecurityNumber())) {
      fail(requestContext, Messages.getInstance().getText(requestContext.getRequest().getLocale(), "studentparents.parentRegistration.formValidationError"));
      return;
    }
    
    StudentParent studentParent = null;
    
    if (requestContext.isLoggedIn()) {
      if (FORMTYPE_LOGGEDIN.equals(formType)) {
        studentParent = studentParentDAO.findById(requestContext.getLoggedUserId());
      }
      else {
        fail(requestContext, "Internal error.");
        return;
      }
    }
    else {
      if (FORMTYPE_CREATE.equals(formType)) {
        String username = StringUtils.trim(requestContext.getString("new-username"));
        String password1 = StringUtils.trim(requestContext.getString("new-password1"));
        String password2 = StringUtils.trim(requestContext.getString("new-password2"));
        
        if (!StringUtils.equals(password1, password2)) {
          fail(requestContext, Messages.getInstance().getText(requestContext.getRequest().getLocale(), "studentparents.parentRegistration.passwordConfirmError"));
          return;
        }
        
        if (StringUtils.isBlank(password1)) {
          fail(requestContext, Messages.getInstance().getText(requestContext.getRequest().getLocale(), "generic.errors.nopassword"));
          return;
        }
        
        // Require email to be unique
        boolean unique = true;
        if (!UserUtils.isAllowedEmail(studentParentRegistration.getEmail(), unique)) {
          fail(requestContext, Messages.getInstance().getText(requestContext.getRequest().getLocale(), "generic.errors.emailInUse"));
          return;
        }

        // TOOD Transaction
        Organization organization = studentParentRegistration.getStudent().getOrganization();
        studentParent = studentParentDAO.create(studentParentRegistration.getFirstName(), studentParentRegistration.getLastName(), organization);
  
        ContactType contactType = contactTypeDAO.findById(1L);
        emailDAO.create(studentParent.getContactInfo(), contactType, true, studentParentRegistration.getEmail()).getId(); 
  
        try {
          String externalId = internalAuthenticationProvider.createCredentials(username, password1);
          userIdentificationDAO.create(studentParent.getPerson(), internalAuthenticationProvider.getName(), externalId);
        } catch (Exception ex) {
          fail(requestContext, Messages.getInstance().getText(requestContext.getRequest().getLocale(), "generic.errors.usernameInUse"));
          return;
        }
        
      } else if (FORMTYPE_LOGIN.equals(formType)) {
        String username = StringUtils.trim(requestContext.getString("username"));
        String password = StringUtils.trim(requestContext.getString("password"));
  
        User user = null;
        try {
          user = internalAuthenticationProvider.getUser(username, password);
        } catch (AuthenticationException e) {
          fail(requestContext, Messages.getInstance().getText(requestContext.getRequest().getLocale(), "generic.errors.usernameInUse"));
          return;
        }
        
        if (user != null && user instanceof StudentParent) {
          studentParent = (StudentParent) user;
        } else {
          fail(requestContext, Messages.getInstance().getText(requestContext.getRequest().getLocale(), "users.login.loginFailed"));
          return;
        }
      }
    }

    if (studentParent != null) {
      studentParentChildDAO.create(studentParent, studentParentRegistration.getStudent());
      
      studentParentRegistrationDAO.delete(studentParentRegistration);
      
      requestContext.addResponseParameter("status", "OK");
    } else {
      fail(requestContext, Messages.getInstance().getText(requestContext.getRequest().getLocale(), "studentparents.parentRegistration.userNotFoundError"));
      return;
    }
  }

  public UserRole[] getAllowedRoles() {
    return new UserRole[] { UserRole.EVERYONE };
  }

  private void fail(JSONRequestContext requestContext, String reason) {
    requestContext.addResponseParameter("status", "FAIL");
    requestContext.addResponseParameter("reason", reason);
  }
  
}
