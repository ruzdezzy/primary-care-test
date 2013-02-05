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
package org.openmrs.module.rwandaprimarycare.dwr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService;

public class PrimaryCareDWRService {

    
    public Integer getAddressHierarchyId(Integer parentId, String searchString){
        Integer ret = null;
        if (Context.getAuthenticatedUser() != null && parentId != null && searchString != null && !searchString.equals("")){
            AddressHierarchyService ahs = Context.getService(AddressHierarchyService.class);
            if (parentId.intValue() == 0){
                List<AddressHierarchy> topList = ahs.getTopOfHierarchyList();
                for (AddressHierarchy ah : topList){
                    if (ah != null && ah.getLocationName().equals(searchString))
                        return ah.getAddressHierarchyId();
                }
            } else {
                
                    List<AddressHierarchy> childList = ahs.getNextComponent(parentId);
                    for (AddressHierarchy ahTmp : childList){
                        if (ahTmp != null && ahTmp.getLocationName().equals(searchString))
                            return ahTmp.getAddressHierarchyId();
                    }
                    
                
            }
        }
        return ret;
    }
    
    public String getMessage(String message){
    	return Context.getMessageSourceService().getMessage(message);
    }
    
    public Map<String,String> getMessages(List<String> messageCodes, String prefix){
    	
    	Map<String,String> messages = new HashMap<String,String>();
    	
    	MessageSourceService messageSourceService = Context.getMessageSourceService();
    	
    	for(String message:messageCodes){
    		if(prefix != null && StringUtils.isNotEmpty(prefix)){
    			messages.put(message, messageSourceService.getMessage(prefix + "." + message));
    		}
    		else{
    			messages.put(message, messageSourceService.getMessage(message));
    		}
    	}
    		
    	return messages;
    }
}
