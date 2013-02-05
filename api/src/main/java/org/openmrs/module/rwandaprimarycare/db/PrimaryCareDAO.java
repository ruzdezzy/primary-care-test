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
package org.openmrs.module.rwandaprimarycare.db;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PersonAttributeType;

public interface PrimaryCareDAO {
	public List<String> getPatientFamilyNamesList(String search);
	public List<String> getPatientGivenNamesList(String search);
	public List<String> getParentsFamilyNamesList(String search,int personAttributeTypeId);
/*	public List<String> getFathersFamilyNamesList(String search);*/
	public List<String> getPatientAddress1List(String search);
	
//	public List<String> getPatientNeighborhoodCellList(String search);
//	public List<String> getPatientCountyDistrictList(String search);
//	public List<String> getPatientCountryList(String search);
//	public List<String> getPatientStateProvinceList(String search);
//	public List<String> getPatientCityVillageList(String search);
	
	public List<Patient> getPatients(
    		String givenName, 
    		String familyName,
    		String gender,
    		Float age, 
    		int ageRange,
    		String Address1,
    		PersonAttributeType healthCenterPat, 
            Location userLocation,
            boolean restrictByHealthCenter);
	
}
