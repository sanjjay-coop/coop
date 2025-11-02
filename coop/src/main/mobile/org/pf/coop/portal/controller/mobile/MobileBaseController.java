package org.pf.coop.portal.controller.mobile;

import java.util.Calendar;
import java.util.List;

import org.pf.coop.portal.model.Advert;
import org.pf.coop.portal.model.MenuItem;
import org.pf.coop.portal.model.Parameters;
import org.pf.coop.portal.repository.AdvertRepo;
import org.pf.coop.portal.repository.MenuItemRepo;
import org.pf.coop.portal.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

public class MobileBaseController {


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
	
	@ModelAttribute("params")
	public Parameters getParameters() {
		return paramsService.getParameters();
	}
	
}
