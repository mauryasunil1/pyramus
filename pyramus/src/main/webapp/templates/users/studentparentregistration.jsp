<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <title><fmt:message key="users.login.pageTitle"></fmt:message></title>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
<%--     <jsp:include page="/templates/generic/head_generic.jsp"></jsp:include> --%>
    <jsp:include page="/templates/generic/jsonrequest_support.jsp"></jsp:include>
    <jsp:include page="/templates/generic/jsonform_support.jsp"></jsp:include>
    <jsp:include page="/templates/generic/scriptaculous_support.jsp"></jsp:include>
    <jsp:include page="/templates/generic/message_adapter_support.jsp"></jsp:include>
    
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/application.css"/>
    
    <script type="text/javascript">
      function onLoad(event) {
        var tabControl = new IxProtoTabs($('tabs'));
      };
      
      function toggleLogin() {
        $('createGuardianCredentialsContainer').hide();
        $('loginGuardianCredentialsContainer').show();
        $('parentRegisterCredentialType').setValue('LOGIN');
      }
    </script>
    
  </head>
  <body onload="onLoad(event);">
    <header class="application-header">
      <div class="application-header__content">
        <div class="application-header__logo">
        </div>
      </div>
    </header>
    
    <c:choose>
      <c:when test="${invalidLogin}">
        <section class="application-description application-description--credentials">
          <div class="application-description__credentials">
            <div class="application-description__credentials-header">
              <fmt:message key="studentparents.parentRegistration.invalidLoginHeader"/>
            </div>
          </div>
        </section>
        
        <main class="application-content application-content--credentials">
          <p><fmt:message key="studentparents.parentRegistration.invalidLoginMessage"/></p>
        </main>
        
      </c:when>
      <c:otherwise>
        <section class="application-description application-description--credentials">
		      <div class="application-description__credentials">
		        <div class="application-description__credentials-header">
		          <fmt:message key="studentparents.parentRegistration.pageTitle"/>
		        </div>
		      </div>
		    </section>
		    
        <main class="application-content application-content--credentials">
          <section class="application-content__form application-content__form--credentials">
		        <form class="application-form" action="parentregister.json" method="post" ix:jsonform="true">
		          <section class="form-section section-create-credentials current">
			          <input type="hidden" name="hash" value="${hash}"/>
			          <div class="form-section__field-container">
                  <label class="required" for="ssn"><fmt:message key="studentparents.parentRegistration.ssecConfirmationTitle"/></label> 
                  <input type="text" id="ssn" name="ssn-confirm" autocomplete="new-ssn-confirm" required="required" size="25">
                </div>
			    
		            <c:choose>
		              <c:when test="${loggedUserId != null}">
		                <div class="form-section__field-container">
		                  <input type="hidden" name="type" id="parentRegisterCredentialType" value="LOGGEDIN"/>
		                
		                  <fmt:message key="studentparents.parentRegistration.loggedInMessagePre"/>
		                  <span style="font-size: 1.2rem; font-weight: 400;">${loggedUserName}</span>.
		                  <fmt:message key="studentparents.parentRegistration.loggedInMessagePost"/>
		                </div>
		              </c:when>
		              <c:otherwise>
		              
		                <input type="hidden" name="type" id="parentRegisterCredentialType" value="CREATE"/>
		                <div id="createGuardianCredentialsContainer">
		                  <div>
		                    <fmt:message key="studentparents.parentRegistration.alreadyRegistered"/>
		                    <a href="#" onclick="toggleLogin();" style="font-weight: 400;"><fmt:message key="studentparents.parentRegistration.alreadyRegisteredLoginLinkLabel"/></a>.
		                  </div>
		          
						          <div class="form-section__field-container">
			                  <label class="required" for="u"><fmt:message key="studentparents.parentRegistration.userNameTitle"/></label> 
			                  <input id="u" type="text" name="new-username" autocomplete="new-username" size="30">
			                </div>
		                      
		                  <div class="form-section__field-container">
                         <label class="required" for="p1"><fmt:message key="studentparents.parentRegistration.password1Title"/></label> 
                         <input type="password" id="p1" name="new-password1" autocomplete="new-password" class="equals equals-new-password2" size="25">
                       </div>
		          
		                  <div class="form-section__field-container">
                         <label class="required" for="p2"><fmt:message key="studentparents.parentRegistration.password2Title"/></label> 
                         <input type="password" id="p2" name="new-password2" autocomplete="new-password" class="equals equals-new-password1" size="25">
                       </div>
		                </div>
		    
		                <div id="loginGuardianCredentialsContainer" style="display: none;">
		                  <div><fmt:message key="studentparents.parentRegistration.loginLabel"/></div>
		                
		                  <div class="form-section__field-container">
		                    <label for="lu"><fmt:message key="studentparents.parentRegistration.userNameTitle"/></label>
		                    <input id="lu" type="text" name="username" required="required" size="30">
		                  </div>
		
		                  <div class="form-section__field-container">
		                    <label for="lp1"><fmt:message key="studentparents.parentRegistration.password1Title"/></label>
		                    <input id="lp1" type="password" name="password" required="required" size="25">
		                  </div>                  
		                </div>
		              </c:otherwise>
		            </c:choose>
			    
			          <nav class="form-navigation">
			            <button type="submit" name="login" class="button-create-credentials">
			              <fmt:message key="studentparents.parentRegistration.submitButtonLabel"/>
			            </button>
			          </nav>
		          </section>
		        </form>
		      </section>
        </main>
      </c:otherwise>
    </c:choose>
    
    <footer class="application-footer">
      <div class="application-footer__contact">
        <div class="application-footer__contact-title">Ota yhteyttä</div>
        <div class="application-footer__contact-row"><span class="application-footer__contact-row-label">Osoite:</span> Otavantie 2 B, 50670 Otava</div>
        <div class="application-footer__contact-row"><span class="application-footer__contact-row-label">Puhelin:</span> 044 794 3552</div>
        <div class="application-footer__contact-row"><span class="application-footer__contact-row-label">Sähköposti:</span> info@otavia.fi</div>
      </div>
      <div class="application-footer__links">
        <a href="https://www.otavanopisto.fi" target="top" class="application-footer__external-link">www.otavanopisto.fi</a>
        <a href="https://www.nettilukio.fi" target="top" class="application-footer__external-link">www.nettilukio.fi</a>
        <a href="https://www.nettiperuskoulu.fi" target="top" class="application-footer__external-link">www.nettiperuskoulu.fi</a>
        <a href="https://otavanopisto.muikkuverkko.fi" target="top" class="application-footer__external-link">otavanopisto.muikkuverkko.fi</a>
        <a href="#" target="top" class="application-footer__external-link">Tietosuojaseloste</a>
      </div>
    </footer>
  </body>
</html>