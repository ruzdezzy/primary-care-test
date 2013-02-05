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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;
import org.openmrs.module.rheapocadapter.util.AttributeList;
import org.openmrs.module.rheapocadapter.util.GetPatientUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class ExtendPatientIdSearchController {

	protected final Log log = LogFactory.getLog(getClass());

	//private GetPatientUtil gpu = new GetPatientUtil();

	final static int MAX_RESULTS = 10;

	@RequestMapping("/module/rwandaprimarycare/extendPatientIdSearch.form")
	public String setupForm(
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "type", required = false) String idType,
			HttpSession session, ModelMap model) throws PrimaryCareException {
		GetPatientUtil gpu = new GetPatientUtil();
		//try {
			if (search != null) {
                log.info("searching");
				log.info("ID NUMBER >> " + search + " ID-TYPE >>> " + idType);
				
				model.addAttribute("search", search);
//				 List<Patient> extendedResults =
//				 Context.getPatientService().getAllPatients();

				//List<AttributeList> results = new ArrayList<AttributeList>();
				if(idType.equals("OMRS")){
					String impid = Context.getAdministrationService().getImplementationId().getImplementationId();
					idType=idType+impid.substring(6, impid.length());
					
				}
//				List<Patient> extendedResults = gpu.getPatientFromClientRegById(idType
//						+ "-" + search);
				
				List<AttributeList> results = gpu.getPatientWirhAttributeFromClientRegById(idType+"-"+search);
				String impid = Context.getAdministrationService().getImplementationId().getImplementationId();
				log.info("Whole string == "+impid);
				log.info(""+impid.substring(6, impid.length()));
				log.info("Result size == "+results.size());
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
//	            		
//	            		if (p.getAttribute(Context.getPersonService().getPersonAttributeTypeByName(
//	    					    PrimaryCareConstants.MOTHER_NAME_ATTRIBUTE_TYPE)) != null) {
//	    						mothersName = p.getAttribute(
//	    						    Context.getPersonService().getPersonAttributeTypeByName(PrimaryCareConstants.MOTHER_NAME_ATTRIBUTE_TYPE))
//	    						        .getValue();
//	    						
//	    					}
//	    					
//	    					if (p.getAttribute(Context.getPersonService().getPersonAttributeTypeByName(
//	    							PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE)) != null) {
//	    						fathersName = p.getAttribute(
//	    						    Context.getPersonService().getPersonAttributeTypeByName(PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE))
//	    						        .getValue();
//	    					}
//	            		
//	            		AttributeList al = new AttributeList(p, mothersName, fathersName, nid);
//	            		results.add(al);
//	            		
//	            		
//	            		
//	            		
//	            	}
//	            }
				
				
				model.addAttribute("results", results);
				//model.addAttribute("CR", "cr_patient");
				model.addAttribute("ids","ids");

			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new PrimaryCareException(e);
//		}
		return "/module/rwandaprimarycare/extendedResults";
	}
	@RequestMapping("/module/rwandaprimarycare/selectIdType.form")
	public String showSelectPage(@RequestParam(value="search", required=false) String search,HttpSession session, ModelMap model) {
		model.addAttribute("idValue", search);
		
		return "/module/rwandaprimarycare/selectIdType";

	}
}
