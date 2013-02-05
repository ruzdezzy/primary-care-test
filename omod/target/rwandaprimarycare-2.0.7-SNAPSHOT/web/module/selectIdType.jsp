<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="resources/touchscreenHeader.jsp"%>

	<form method="get" action="extendPatientIdSearch.form">
		<c:set var="enterId"><spring:message code="rwandaprimarycare.touchscreen.enterIdNumber"/></c:set>
		<c:set var="searchStr"><spring:message code="rwandaprimarycare.touchscreen.search"/></c:set>
		 <c:set var="selectID">
						<spring:message code='rwandaprimarycare.touchscreen.sel' /></c:set>
					<c:set var="nid">
						<spring:message code='rwandaprimarycare.touchscreen.nidval' />
					</c:set> <c:set var="rama">
						<spring:message code='rwandaprimarycare.touchscreen.rama' />
					</c:set> <c:set var="mutuelle">
						<spring:message code='rwandaprimarycare.touchscreen.mutuelle' />
					</c:set> <c:set var="omrs">
						<spring:message code='rwandaprimarycare.touchscreen.omrsid' />
					</c:set> <select optional="false" name="type" label="${selectID} "
					helpText="${selectID} ${idValue}">
						<option value="NID">${nid}</option>
						<option value="RAM">${rama}</option>
						<option value="MUT">${mutuelle}</option>
						<option value="OMRS">${omrs}</option>
				</select>
				<input type="hidden" name="search" value="${idValue}"/>
		<input type="submit" value="${searchStr} }"/>
	</form>

<%@ include file="resources/touchscreenFooter.jsp"%>