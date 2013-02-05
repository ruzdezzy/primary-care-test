<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="resources/touchscreenHeader.jsp"%>
<%@ taglib prefix="touchscreen"
	tagdir="/WEB-INF/tags/module/rwandaprimarycare"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<openmrs:htmlInclude
	file="/moduleResources/rwandaprimarycare/addresshierarchyrwanda.js" />
	<openmrs:htmlInclude
	file="/moduleResources/rwandaprimarycare/paginate.js" />
<openmrs:htmlInclude
	file="/moduleResources/rwandaprimarycare/jquery-1.4.4.min.js" />
	
<div style="clear: both" id="res">
	<c:if test="${fn:length(results) == 0}">
		<c:if test="${title != null}">
			&nbsp;test
		</c:if>
		<c:if test="${title == null}">

			<u><span class="bigtext"> <br /> <spring:message
						code="rwandaprimarycare.touchscreen.noPatients" />
			</span></u>
		</c:if>
	</c:if>
	<c:if test="${fn:length(results) > 0}">
		<u><span class="bigtext"> <br /> ${fn:length(results)} <spring:message
					code="rwandaprimarycare.touchscreen.clientRegistry" />

		</span></u>
		<table class="paginated-table">
			<tr>
				<td></td>
				<th>Rwandan Name</th>
				<th>French/Anglo Name</th>
				<th>NID</th>
				<th>DOB (AGE)</th>
				<th>Province</th>
				<th>District</th>
				<th>Sector</th>
				<th>Cell</th>
				<th>Umudugudu</th>
			</tr>
			<c:forEach var="pat" items="${results}" varStatus="status">

				
					<tr class="${status.count%2!=0?'evenRow':'oddRow'}">
					<td><strong>${status.count}.</strong></td>
					<td>${pat.patient.personName.familyName}</td>
					<td>${pat.patient.personName.givenName}</td>
                    <td>${pat.nid}</td>

					<td><openmrs:formatDate date="${pat.patient.birthdate}"
							type="short" />(${pat.patient.age})</td>
					<fmt:formatDate value="${pat.patient.birthdate}" pattern="yyyy-MM-dd" var="db" />
				    
					<c:set var="dateParts" value="${fn:split(db, '-')}" />
					<!--<c:set var="dob_date" value="${fn:substring(dateParts[2], 0, 3)}" /> -->
					<td>${pat.patient.personAddress.stateProvince}</td>
					<td>${pat.patient.personAddress.countyDistrict}</td>
					<td>${pat.patient.personAddress.cityVillage}</td>
					<td>${pat.patient.personAddress.neighborhoodCell}</td>
					<td>${pat.patient.personAddress.address1}</td>
					<c:url var="create" value="createNewPatient.form">
						<c:param name="addIdentifier" value="${addIdentifier}" />
						<c:param name="givenName" value="${pat.patient.personName.givenName}" />
						<c:param name="familyName"
							value="${pat.patient.personName.familyName}" />
						<c:param name="gender" value="${pat.patient.gender}" />
						<c:param name="birthdateDay" value="${dateParts[2]}" />
						<c:param name="birthdateMonth" value="${dateParts[1]}" />
						<c:param name="birthdateYear" value="${dateParts[0]}" />
						<c:param name="age" value="${pat.patient.age}" />
						<c:param name="country" value="${patCountry}" />

						<c:param name="province"
							value="${pat.patient.personAddress.stateProvince}" />
						<c:param name="district"
							value="${pat.patient.personAddress.countyDistrict}" />
						<c:param name="sector"
							value="${pat.patient.personAddress.cityVillage}" />
						<c:param name="cell"
							value="${pat.patient.personAddress.neighborhoodCell}" />
						<c:param name="address1" value="${pat.patient.personAddress.address1}" />
						<c:param name="mothersName" value="${pat.mothersName}" />
						<c:param name="fathersName" value="${pat.fathersName}" />
						<<c:param name="nid" value="${pat.nid}" />

					</c:url>
					<td class="press"><touchscreen:button label="Select"
							href="${create}" /></td>

				</tr>



			</c:forEach>
		</table>
		<c:if test="${fn:length(results) > 5}">
			<span class="next" style="float: right; font-size: small;"><touchscreen:button
					label="Next &gt;&gt;" href="" cssClass="dark" /></span>
			<span class="prev" style="float: right; font-size: small;"><touchscreen:button
					label="&lt;&lt; Previous" href="" cssClass="dark" /></span>
		</c:if>
	</c:if>
	<c:url var="createnew" value="createNewPatient.form">
		<c:param name="addIdentifier" value="${addIdentifier}" />
		<c:param name="givenName" value="${givenName}" />
		<c:param name="familyName" value="${familyName}" />
		<c:param name="gender" value="${gender}" />
		<c:param name="birthdate_day" value="${param.BIRTHDATE_DAY}" />
		<c:param name="birthdate_month" value="${param.BIRTHDATE_MONTH}" />
		<c:param name="birthdate_year" value="${param.BIRTHDATE_YEAR}" />
		<c:param name="age" value="${age}" />
		<c:param name="mothersName" value="${param.MRWNAME}" />
		<c:param name="fathersName" value="${param.FATHERSRWNAME}" />
		<c:param name="country" value="${searchCOUNTRY}" />
		<c:param name="province" value="${searchPROVINCE}" />
		<c:param name="district" value="${searchDISTRICT}" />
		<c:param name="sector" value="${searchSECTOR}" />
		<c:param name="cell" value="${searchCELL}" />
		<c:param name="address1" value="${searchUMUDUGUDU}" />
		<c:param name="CR" value="${CR}" />
	</c:url>

</div>
<div style="clear: both;">
	<c:set var="nf">
		<spring:message
			code='rwandaprimarycare.touchscreen.notFoundNewPatient' />
	</c:set>
	<c:if test="${ids == null}">
	<span style="font-size: small;"> <touchscreen:button
			label="${nf} ${fn:toUpperCase(familyName)} ${givenName}"
			href="${createnew}" /></span>
	</c:if>
	<c:if test="${ids != null}">
	<c:if test="${fn:length(results) == 0}">
	<c:set var="searchByNameStr"><spring:message code="rwandaprimarycare.touchscreen.searchByName"/></c:set>
		<c:set var="searchByIdAgainStr"><spring:message code="rwandaprimarycare.touchscreen.searchByIdAgain"/></c:set>
		<touchscreen:button label="${searchByIdAgainStr}" href="findPatientById.form"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<touchscreen:button label="${searchByNameStr}" href="findPatientByName.form"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</c:if>
	</c:if>
</div>
<script type="text/javascript">
	var maxRows = 5;
	$('.paginated-table').each(
			function() {
				var cTable = $(this);
				var cRows = cTable.find('tr:gt(0)');
				var cRowCount = cRows.size();

				if (cRowCount < maxRows) {
					return;
				}

				
				cRows.filter(':gt(' + (maxRows - 1) + ')').hide();

				var cPrev = cTable.siblings('.prev');
				var cNext = cTable.siblings('.next');

				
				cPrev.addClass('disabled');

				cPrev.click(function() {
					var cFirstVisible = cRows.index(cRows.filter(':visible'));

					if (cPrev.hasClass('disabled')) {
						return false;
					}

					cRows.hide();
					if (cFirstVisible - maxRows - 1 > 0) {
						cRows.filter(
								':lt(' + cFirstVisible + '):gt('
										+ (cFirstVisible - maxRows - 1) + ')')
								.show();
					} else {
						cRows.filter(':lt(' + cFirstVisible + ')').show();
					}

					if (cFirstVisible - maxRows <= 0) {
						cPrev.addClass('disabled');
					}

					cNext.removeClass('disabled');

					return false;
				});

				cNext.click(function() {
					var cFirstVisible = cRows.index(cRows.filter(':visible'));

					if (cNext.hasClass('disabled')) {
						return false;
					}

					cRows.hide();
					cRows.filter(
							':lt(' + (cFirstVisible + 2 * maxRows) + '):gt('
									+ (cFirstVisible + maxRows - 1) + ')')
							.show();

					if (cFirstVisible + 2 * maxRows >= cRows.size()) {
						cNext.addClass('disabled');
					}

					cPrev.removeClass('disabled');

					return false;
				});

			});
</script>
<style type="text/css">
th {
	background-color: #ddd;
}

th td {
	border: 1px solid black;
}

#res {
	font-size: medium;
}
</style>


<%@ include file="resources/touchscreenFooter.jsp"%>