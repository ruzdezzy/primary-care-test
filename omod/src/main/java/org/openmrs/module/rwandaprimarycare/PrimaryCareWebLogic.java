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

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.springframework.ui.ModelMap;

public class PrimaryCareWebLogic {

    protected static final Log log = LogFactory.getLog(PrimaryCareWebLogic.class);
    
    public static Location getCurrentLocation(HttpSession session) {
        
    	Location loc = (Location) session.getAttribute(PrimaryCareConstants.SESSION_ATTRIBUTE_WORKSTATION_LOCATION);
        if (loc == null) {
            loc = Context.getLocationService().getDefaultLocation();
        }
        if (loc == null) {
            log.warn("Cannot find a current or default location");
        }
        return loc;
    }
    
    public static void  clearSessionSearchAttributes(HttpSession session){

        session.removeAttribute("addIdentifier");
        session.removeAttribute("FANAME");
        session.removeAttribute("RWNAME");
        session.removeAttribute("GENDER");
        session.removeAttribute("AGE");
        session.removeAttribute("BIRTHDATE_DAY");
        session.removeAttribute("BIRTHDATE_MONTH");
        session.removeAttribute("BIRTHDATE_YEAR");
        session.removeAttribute("MRWNAME");
        session.removeAttribute("FATHERSRWNAME");
        session.removeAttribute("COUNTRY");
        session.removeAttribute("PROVINCE");
        session.removeAttribute("DISTRICT");
        session.removeAttribute("SECTOR");
        session.removeAttribute("CELL");
        session.removeAttribute("UMUDUGUDU");
        
    }

	public static Patient findParentsNamesAttributes( List<Person> parents, Patient patient , ModelMap map){
    	  if(parents.size() == 0 && PrimaryCareUtil.hasParentsNamesAttributes(patient)){
        	map.addAttribute("mumStr", patient.getAttribute(Context.getPersonService().getPersonAttributeTypeByName(PrimaryCareConstants.MOTHER_NAME_ATTRIBUTE_TYPE)).getValue());
            map.addAttribute("dadStr", patient.getAttribute(Context.getPersonService().getPersonAttributeTypeByName(PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE)).getValue());
        }else{
        	map.addAttribute("mumStr","");
            map.addAttribute("dadStr","");
        }
    	return patient;
    }

}
