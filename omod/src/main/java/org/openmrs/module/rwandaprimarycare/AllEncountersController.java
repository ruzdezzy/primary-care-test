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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AllEncountersController {

    @RequestMapping("/module/rwandaprimarycare/allEncounters")
    public String listAllEncounters(@RequestParam("patientId") int patientId, ModelMap model) throws PrimaryCareException {
    	
    	//LK: Need to ensure that all primary care methods only throw a PrimaryCareException
    	//So that errors will be directed to a touch screen error page
    	try{
	        Patient patient = Context.getPatientService().getPatient(patientId);
	        SortedMap<Date, List<Encounter>> encounters = new TreeMap<Date, List<Encounter>>(Collections.reverseOrder());
	        for (Encounter e : Context.getEncounterService().getEncountersByPatient(patient)) {
	            Calendar cal = Calendar.getInstance();
	            cal.setTime(e.getEncounterDatetime());
	            cal.set(Calendar.HOUR_OF_DAY, 0);
	            cal.set(Calendar.MINUTE, 0);
	            cal.set(Calendar.SECOND, 0);
	            cal.set(Calendar.MILLISECOND, 0);
	            Date day = cal.getTime();
	            List<Encounter> holder = encounters.get(day);
	            if (holder == null) {
	                holder = new ArrayList<Encounter>();
	                encounters.put(day, holder);
	            }
	            holder.add(e);
	        }
	        model.addAttribute("patient", patient);
	        model.addAttribute("encounters", encounters);
	        model.addAttribute("vitalsEncounterType", PrimaryCareBusinessLogic.getVitalsEncounterType());
	        model.addAttribute("registrationEncounterType", PrimaryCareBusinessLogic.getRegistrationEncounterType());
    	} catch(Exception e)
    	{
    		throw new PrimaryCareException(e);
    	}
        return "/module/rwandaprimarycare/allEncounters";
    }
    
}
