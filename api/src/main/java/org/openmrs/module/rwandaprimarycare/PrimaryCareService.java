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

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

public interface PrimaryCareService extends OpenmrsService {

    /**
     * FANAME is mapped to given_name, RWNAME is mapped to family_name
     */
	public enum PatientSearchType {FANAME,RWNAME,GENDER, AGE, BIRTHDATE_DAY, BIRTHDATE_MONTH, BIRTHDATE_YEAR, MRWNAME, FATHERSRWNAME, COUNTRY, PROVINCE, SECTOR, UMUDUGUDU, CELL, DISTRICT};
	
	/**
	 * Returns a list of all possible values for the given search type. This does NOT return patients.
	 * 
	 * @param search search term, searchType ths enum for type of object we're looking for, see the impl, 
	 * and previousId is for the addressheirarchy module, because we need to get the children by passing in the parent.
	 * @param searchType what we are searching for 
	 */
	@Transactional(readOnly=true)
    public List<String>  getPatientSearchList(String search, PatientSearchType searchType, Integer previousId);

	/**
	 * Sorts the return list so that patients who match userLocation show up first
	 * 
	 * @param frenchEnglishName
	 * @param rwandanName
	 * @param gender
	 * @param age
	 * @param mothersRwandanName
	 * @param Umudugudu
	 * @param userLocation optional
	 * @return
	 * 
	 * @should find the right patients with few arguments
	 * @should find the right patients with all arguments
	 */
	@Transactional(readOnly=true)
    public List<Patient> getPatients(
    		String frenchEnglishName, 
    		String rwandanName,
    		String gender,
    		Float age, 
    		String mothersRwandanName,
    		String fathersRwandanName,
    		String country,
    		String province,
    		String district,
    		String sector,
    		String cell,
    		String umudugudu,
    		PersonAttributeType healthCenterPat,
            Location userLocation);
	
}