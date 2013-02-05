<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="resources/touchscreenHeader.jsp"%>

<c:if test="${results == null}">
	<form method="get">
		<c:set var="enterId"><spring:message code="rwandaprimarycare.touchscreen.enterIdNumber"/></c:set>
		<c:set var="searchStr"><spring:message code="rwandaprimarycare.touchscreen.search"/></c:set>
		<touchscreen:textInput label="${enterId}" field_name="search" required="true" value="" fieldType="id"/>
		 <!-- <c:set var="selectID">
						<spring:message code='rwandaprimarycare.touchscreen.sel' /></c:set>
					<c:set var="nid">
						<spring:message code='rwandaprimarycare.touchscreen.nidval' />
					</c:set> <c:set var="rama">
						<spring:message code='rwandaprimarycare.touchscreen.rama' />
					</c:set> <c:set var="mutuelle">
						<spring:message code='rwandaprimarycare.touchscreen.mutuelle' />
					</c:set> <c:set var="omrs">
						<spring:message code='rwandaprimarycare.touchscreen.omrsid' />
					</c:set> <select optional="false" name="ID-Type" label="${selectID}  "
					helpText="${selectID}">
						<option value="NID">${nid}</option>
						<option value="RAM">${rama}</option>
						<option value="MUT">${mutuelle}</option>
						<option value="OMRS">${omrs}</option>
				</select> -->
		<input type="submit" value="${searchStr} }"/>
	</form>
</c:if>

<c:if test="${results != null}">
    
	<touchscreen:patientList patients="${results}" maxResults="5" separator="" showAllIds="true" />
	
	
	<c:if test="${fn:length(results) == 0}">
		<br/>
		<br/>
		<c:set var="searchByNameStr"><spring:message code="rwandaprimarycare.touchscreen.searchByName"/></c:set>
		<c:set var="searchByIdAgainStr"><spring:message code="rwandaprimarycare.touchscreen.searchByIdAgain"/></c:set>
		<touchscreen:button label="${searchByIdAgainStr}" href="findPatientById.form"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<touchscreen:button label="${searchByNameStr}" href="findPatientByName.form"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${CR == null}">
	<c:url var="ext" value="selectIdType.form">
		<c:param name="search" value="${search}"/>
		
	</c:url>
		<touchscreen:button label="Extend Search To Client Registry" href="${ext}"/>
		</c:if>
	</c:if>
</c:if>

<%@ include file="resources/touchscreenFooter.jsp"%>