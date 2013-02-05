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

import org.openmrs.Encounter;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewEncounterController {

    @RequestMapping("/module/rwandaprimarycare/encounter")
    public String viewEncounter(
            @RequestParam("encounterId") int encounterId,
            @RequestParam(required=false, value="returnUrl") String returnUrl,
            ModelMap model) throws PrimaryCareException {
        
    	//LK: Need to ensure that all primary care methods only throw a PrimaryCareException
    	//So that errors will be directed to a touch screen error page
    	try{
	        Encounter encounter = Context.getEncounterService().getEncounter(encounterId);
	        model.addAttribute(encounter);
	        model.addAttribute(encounter.getPatient());
	        model.addAttribute("returnUrl", returnUrl);
	        model.addAttribute("registrationEncounterType", PrimaryCareBusinessLogic.getRegistrationEncounterType());
    	} catch(Exception e)
    	{
    		throw new PrimaryCareException(e);
    	} 
        
        return "/module/rwandaprimarycare/encounter";
    }

    @RequestMapping("/module/rwandaprimarycare/deleteEncounter")
    public String deleteEncounter(
            @RequestParam("encounterId") int encounterId,
            @RequestParam(required = false, value = "returnUrl") String returnUrl) throws PrimaryCareException {
    	//LK: Need to ensure that all primary care methods only throw a PrimaryCareException
    	//So that errors will be directed to a touch screen error page
    	try{
	    	Encounter encounter = Context.getEncounterService().getEncounter(encounterId);
	        Integer patientId = encounter.getPatientId();
	        Context.getEncounterService().voidEncounter(encounter, "N/A");
	        //now check to see if we should void the visit as well.
//	        if(encounter.getVisit() !=  null)
//	        {
//	        	Visit visit = Context.getVisitService().getVisit(encounter.getVisit().getId());
//	        	boolean voidVisit = true;
//	        	
//	        	for(Encounter e: visit.getEncounters())
//	        	{
//	        		if(!e.isVoided())
//	        		{
//	        			voidVisit = false;
//	        		}
//	        	}
//	        	if(voidVisit)
//	        	{
//	        		Context.getVisitService().voidVisit(visit, "N/A");
//	        	}
//	        }
	        if (!StringUtils.hasText(returnUrl))
	            returnUrl = "/module/rwandaprimarycare/patient.form?patientId=" + patientId;
	        return "redirect:" + returnUrl;
    	} catch(Exception e)
    	{
    		throw new PrimaryCareException(e);
    	} 
    }
}
