<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<section class="application-section application-meta">      
  
  <div class="user-exists-container" style="display:none;">
    <div class="user-exists-description-title">Hakija löytyy jo Pyramuksesta.</div> 
    <div class="user-exists-description">
      <div class="user-exists-description-piggy"></div>
      <div class="user-exists-description-actions">
        <span>Hakijan Pyramus-profiili:</span> 
      </div>
    </div>
  </div>
  
  <div class="additional-info-wrapper">
    <div class="additional-info-container">
      <div class="meta-container">
        <span class="meta-name">Hakemuksen tila</span>
        <span id="info-application-state-value" data-state="${infoState}" class="meta-value">${infoStateUi}</span>
      </div>
      <div class="meta-container">
        <span class="meta-name">Käsittelijä</span>
        <span id="info-application-handler-value" data-handler-id="${infoHandlerId}" class="meta-value">
          <c:choose>
            <c:when test="${empty infoHandler}">-</c:when>
            <c:otherwise>${infoHandler}</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="meta-container">
        <span class="meta-name">Jätetty</span>
        <span id="info-application-created-value" class="meta-value">
          <fmt:formatDate pattern="d.M.yyyy H:mm" value="${infoCreated}"/>
        </span>
      </div>
      <div class="meta-container">
        <span class="meta-name">Muokattu viimeksi</span>
        <span id="info-application-last-modified-value" class="meta-value">
          <fmt:formatDate pattern="d.M.yyyy H:mm" value="${infoLastModified}"/>
        </span>
      </div>
    </div>
  </div>
  
  <c:if test="${infoState eq 'WAITING_STAFF_SIGNATURE'}">
    <div class="signatures-container" data-document-id="${infoSignatures.staffDocumentId}" data-document-state="${infoSignatures.staffDocumentState}" data-ssn="${infoSsn}">
      <div class="signatures-document-container">
        <div id="signatures-generate-document-button" style="display:none;">Luo hyväksymisasiakirja</div>
        <div class="signatures-document-link" style="display:none;"></div>
      </div>
      <div class="signatures-auth-container" style="display:none;">
        <div class="signatures-auth-sources"></div>
      </div>
    </div>
  </c:if>
  
</section>