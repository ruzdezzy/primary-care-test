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

import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyPropertiesController {

	protected static final Log log = LogFactory
			.getLog(MyPropertiesController.class);

	@RequestMapping("/module/rwandaprimarycare/myProperties")
	public String showMyProperties(ModelMap model, HttpSession session)
			throws PrimaryCareException {

		return "/module/rwandaprimarycare/myProperties";
	}

	@RequestMapping("/module/rwandaprimarycare/keyboardType")
	public String showSetKeyboardType(ModelMap model, HttpSession session,
			HttpServletRequest request) throws PrimaryCareException {
		return "/module/rwandaprimarycare/keyboardType";

	}

	@RequestMapping("/module/rwandaprimarycare/chooseLanguage")
	public String showChooseLanguage(ModelMap model, HttpSession session,
			HttpServletRequest request) throws PrimaryCareException {

		model.addAttribute("locales", Context.getAdministrationService()
				.getAllowedLocales());

		return "/module/rwandaprimarycare/chooseLanguage";

	}

	@RequestMapping("/module/rwandaprimarycare/keyboardSelected")
	public String changeUserKeyboard(ModelMap model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response)
			throws PrimaryCareException {
		try {
			String keyboardType = request.getParameter("keyboardSelect");
			if (keyboardType != null) {
				User user = Context.getAuthenticatedUser();
				user.getUserProperties().put("keyboardType", keyboardType);
				session.setAttribute("keyboardType", keyboardType);
				Context.getUserService().saveUser(user, null);
			}
		} catch (Exception e) {
			throw new PrimaryCareException(e);
		}
		return "/module/rwandaprimarycare/homepage";
	}

	@RequestMapping("/module/rwandaprimarycare/languageChanged.form")
	public String changeUserLanguage(ModelMap model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response)
			throws PrimaryCareException {
		try {
			String locale = request.getParameter("locales");
			User user = Context.getAuthenticatedUser();
			Map<String, String> properties = user.getUserProperties();
			properties.put(OpenmrsConstants.USER_PROPERTY_DEFAULT_LOCALE,
					locale);
			Context.getUserService().saveUser(user, null);
			Context.setLocale(new Locale(locale));

		} catch (Exception e) {
			throw new PrimaryCareException(e);
		}
		return "/module/rwandaprimarycare/homepage";
	}
}
