/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.rwandaprimarycare;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.rheapocadapter.util.AttributeList;
import org.openmrs.module.rheapocadapter.util.GetPatientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/rwandaprimarycare/extendNameSearch.form")
public class ExtendPatientNameSearchController {

	protected final Log log = LogFactory.getLog(getClass());

	final static int MAX_RESULTS = 10;
	//private GetPatientUtil getPatientUtil = new GetPatientUtil();

	@RequestMapping("/module/rwandaprimarycare/extendNameSearch")
	public String setupForm(
			@RequestParam("givenName") String givenName,
			@RequestParam("familyName") String familyName,
			@RequestParam("gender") String gender,
			@RequestParam("age") Integer age,
			@RequestParam(required = false, value = "birthdateDay") Integer birthdateDay,
			@RequestParam(required = false, value = "birthdateMonth") Integer birthdateMonth,
			@RequestParam(required = false, value = "birthdateYear") Integer birthdateYear,
			@RequestParam("country") String country,
			@RequestParam("province") String province,
			@RequestParam("district") String district,
			@RequestParam("sector") String sector,
			@RequestParam("cell") String cell,
			@RequestParam("address1") String address1, HttpSession session,
			ModelMap model) throws PrimaryCareException {
		// LK: Need to ensure that all primary care methods only throw a
		// PrimaryCareException
		// So that errors will be directed to a touch screen error page
		GetPatientUtil getPatientUtil = new GetPatientUtil();
		try {
			if (givenName != null) {
				log.info("FANAME >> " + givenName);
				log.info("RWNAME >> " + familyName);
				model.addAttribute("givenName", givenName);
				TreeMap<String, String> params = new TreeMap<String, String>();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

				Calendar c = Calendar.getInstance();
				c.add(Calendar.YEAR, -age);
				c.add(Calendar.DATE, -183);

				Date d = c.getTime();
				int year = d.getYear();
				String df = sdf.format(d);
				log.info(df + "<<  >>" + year);

				params.put("dob", df);

				if (givenName != null && givenName != "") {
					params.put("given_name", givenName);
				}
				if (familyName != null && familyName != "") {
					params.put("family_name", familyName);
				}
				if (gender != null && gender != "") {
					params.put("gender", gender);
				}
				if (province != null && province != "") {
					params.put("addr_province", province);
				}
				if (district != null && district != "") {
					params.put("addr_district", district);
				}
				if (sector != null && sector != "") {
					params.put("addr_sector", sector);
				}
				if (cell != null && cell != "") {
					params.put("addr_cell", cell);
				}
				if (address1 != null && address1 != "") {
					params.put("addr_village", address1);
				}

				model.addAttribute("givenName", givenName);
				model.addAttribute("familyName", familyName);
				model.addAttribute("gender", gender);
				model.addAttribute("age", age);
				model.addAttribute("searchUMUDUGUDU", address1);
				model.addAttribute("searchCELL", cell);
				model.addAttribute("searchDISTRICT", district);
				model.addAttribute("searchCOUNTRY", country);
				model.addAttribute("searchPROVINCE", province);
				model.addAttribute("searchSECTOR", sector);
				//model.addAttribute("CR", "Client Reg");

				//List<AttributeList> result = new ArrayList<AttributeList>();
//				List<Patient> extendedResults = getPatientUtil
//						.getPatientFromClientReg(params);
				
				
				List<AttributeList> results = getPatientUtil.getPatientWithAttributeListFromClientReg(params);
				
				if(results.size()>0){
					for(AttributeList al:results){
						String nid = "";
						if(al.getPatient().getPatientIdentifier("NID")!=null){
							nid = al.getPatient().getPatientIdentifier("NID").getIdentifier();
							al.setNid(nid);
							log.info("Moms name == "+al.getMothersName());
						}
						else{
	            			nid = "Unavailable";
	            			al.setNid(nid);
	            		}
					}
				}
//				if(extendedResults.size()>0){
//	            	for(Patient p:extendedResults){
//	            		String mothersName="";
//	            		String fathersName="";
//	            		String nid = "";
//	            		if(p.getPatientIdentifier("NID")!=null){
//	            			nid = p.getPatientIdentifier("NID").getIdentifier();
//	            		}
//	            		else{
//	            			nid = "Unavailable";
//	            		}
//	            		PersonAttributeType mName = Context.getPersonService().getPersonAttributeTypeByName(
//	    					    PrimaryCareConstants.MOTHER_NAME_ATTRIBUTE_TYPE) ;
//	            		PersonAttributeType fName = Context.getPersonService().getPersonAttributeTypeByName(
//	    					    PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE) ;
//	            		
//	            		if (p.getAttribute(mName) != null) {
//	    						mothersName = p.getAttribute(mName).getValue();
//	    						
//	    					}
//	    					
//	            		if (p.getAttribute(fName) != null) {
//    						fathersName = p.getAttribute(fName).getValue();
//    						
//    					}
//	            		
//	            		AttributeList al = new AttributeList(p, mothersName, fathersName, nid);
//	            		results.add(al);
//	            		
//	            	}
//	            }
//				

				model.addAttribute("results", results);
				
				model.addAttribute("identifierTypes", PrimaryCareBusinessLogic
						.getPatientIdentifierTypesToUse());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrimaryCareException(e);
		}
		return "/module/rwandaprimarycare/extendedResults";
	}

}
