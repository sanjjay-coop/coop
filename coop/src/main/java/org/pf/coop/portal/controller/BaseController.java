package org.pf.coop.portal.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.pf.coop.common.YesNo;
import org.pf.coop.portal.model.Advert;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MenuItem;
import org.pf.coop.portal.model.Parameters;
import org.pf.coop.portal.repository.AdvertRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.MenuItemRepo;
import org.pf.coop.portal.repository.ModuleRepo;
import org.pf.coop.portal.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {
	
	@Autowired
	private ParametersService paramsService;
	
	@Autowired
	private AdvertRepo advertRepo;
	
	@Autowired
	private MenuItemRepo menuItemRepo;
	
	@ModelAttribute("servletRequest")
	public HttpServletRequest servletRequest(final HttpServletRequest request) {
		
	    return request;
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "index";
	}
	
	@ModelAttribute("listAdvert")
	public List<Advert> getListAdvert(){
		return (List<Advert>) this.advertRepo.listAdvertForPublication((Calendar.getInstance()).getTime());
	}
	
	@ModelAttribute("listMenuItemTop")
	public List<MenuItem> getListMenuItemTop(){
		return (List<MenuItem>) this.menuItemRepo.listMenuItemLocation("top");
	}

	@ModelAttribute("listMenuItemLeft")
	public List<MenuItem> getListMenuItemLeft(){
		return (List<MenuItem>) this.menuItemRepo.listMenuItemLocation("left");
	}
	
	@ModelAttribute("listYesNo")
	public List<YesNo> getListYesNo(){
		
		List<YesNo> listYesNo = new ArrayList<YesNo>();
		
		listYesNo.add(new YesNo(0, "False"));
		listYesNo.add(new YesNo(1, "True"));
		
		return listYesNo;
	}
	
	@ModelAttribute("params")
	public Parameters getParameters() {
		return paramsService.getParameters();
	}
	
	@Autowired
	ModuleRepo moduleRepo;
	
	@ModelAttribute("moduleStatus")
	public HashMap<String, Boolean> getModuleStatus(){
		HashMap<String, Boolean> moduleMap = new HashMap<String, Boolean>();
		List<org.pf.coop.portal.model.Module> listModule = this.moduleRepo.findAll();
		for(org.pf.coop.portal.model.Module module : listModule) {
			moduleMap.put(module.getName(), module.getEnabled());
		}
		
		return moduleMap;
	}
	
	@Autowired
	private MemberRepo memberRepo;
	
	@ModelAttribute("colorTheme")
	public String getColorTheme(Principal principal) {
		
		if (principal == null) return "";
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		if (member == null) return "";
		else return member.getTheme();
	}
}
