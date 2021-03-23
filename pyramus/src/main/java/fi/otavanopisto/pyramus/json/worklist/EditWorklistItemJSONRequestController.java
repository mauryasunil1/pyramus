package fi.otavanopisto.pyramus.json.worklist;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;

import fi.internetix.smvc.controllers.JSONRequestContext;
import fi.otavanopisto.pyramus.dao.DAOFactory;
import fi.otavanopisto.pyramus.dao.users.StaffMemberDAO;
import fi.otavanopisto.pyramus.dao.worklist.WorklistItemDAO;
import fi.otavanopisto.pyramus.domainmodel.users.StaffMember;
import fi.otavanopisto.pyramus.domainmodel.worklist.WorklistItem;
import fi.otavanopisto.pyramus.framework.JSONRequestController;
import fi.otavanopisto.pyramus.framework.UserRole;

public class EditWorklistItemJSONRequestController extends JSONRequestController {

  public void process(JSONRequestContext requestContext) {
    StaffMemberDAO staffMemberDAO = DAOFactory.getInstance().getStaffMemberDAO();
    WorklistItemDAO worklistItemDAO = DAOFactory.getInstance().getWorklistItemDAO();

    String itemIdStr = requestContext.getRequest().getParameter("itemId");
    Long itemId = NumberUtils.isNumber(itemIdStr) ? NumberUtils.createLong(itemIdStr) : null;
    Date entryDate = requestContext.getDate("entryDate");
    String description = requestContext.getRequest().getParameter("description");
    Double price = NumberUtils.createDouble(requestContext.getRequest().getParameter("price"));
    Double factor = NumberUtils.createDouble(requestContext.getRequest().getParameter("factor"));
    Long loggedUserId = requestContext.getLoggedUserId();
    StaffMember loggedUser = staffMemberDAO.findById(loggedUserId);
    
    WorklistItem worklistItem = worklistItemDAO.findById(itemId);
    worklistItemDAO.update(worklistItem, entryDate, description, price, factor, loggedUser);
  }

  public UserRole[] getAllowedRoles() {
    return new UserRole[] { UserRole.STUDY_PROGRAMME_LEADER, UserRole.ADMINISTRATOR };
  }

}
