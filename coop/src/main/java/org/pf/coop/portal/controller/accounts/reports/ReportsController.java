package org.pf.coop.portal.controller.accounts.reports;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.pf.coop.excel.ExcelFileGenerator;
import org.pf.coop.forms.ReportForm;
import org.pf.coop.portal.controller.accounts.AccountsBaseController;
import org.pf.coop.portal.model.accounts.Expenditure;
import org.pf.coop.portal.model.accounts.Income;
import org.pf.coop.portal.repository.accounts.ExpenditureRepo;
import org.pf.coop.portal.repository.accounts.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/accounts/reports")
public class ReportsController extends AccountsBaseController {
	
	@Autowired
	private IncomeRepo incomeRepo;
	
	@Autowired
	private ExpenditureRepo expenditureRepo;
		
	@GetMapping
	public String reports(Model model) {
		
		ReportForm reportForm = new ReportForm();
		
		model.addAttribute(reportForm);
		
		return "accounts/reports/form";
	}
	
	@PostMapping
	public String incomeAdd(@ModelAttribute ReportForm reportForm,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) throws IOException {
		
		if (reportForm.getStartDate()==null) reportForm.setStartDate(Calendar.getInstance().getTime());
		if (reportForm.getEndDate()==null) reportForm.setEndDate(Calendar.getInstance().getTime());
		
		switch (reportForm.getReportType()) {
			case "EXCEL":
				
				request.getSession().setAttribute("accountsReportForm", reportForm);
				return "redirect:/accounts/reports/excel";
				
			default:
				
				List<Income> listIncome = this.incomeRepo.listIncome(reportForm.getStartDate(), reportForm.getEndDate());
				List<Expenditure> listExpenditure = this.expenditureRepo.listExpenditure(reportForm.getStartDate(), reportForm.getEndDate());
				
				if (listIncome.isEmpty() && listExpenditure.isEmpty()) {
					reat.addFlashAttribute("message", "0 results found.");
					return "redirect:/accounts/reports";
				}
				
				model.addAttribute("listIncome", listIncome);
				model.addAttribute("listExpenditure", listExpenditure);
				model.addAttribute("reportForm", reportForm);
				
				return "accounts/reports/report";
		}
	}
	
	@GetMapping(value = "/excel")
	public void reports(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ReportForm reportForm = (ReportForm) request.getSession().getAttribute("accountsReportForm");
		
		if (reportForm == null) reportForm = new ReportForm();
		
		if (reportForm.getStartDate()==null) reportForm.setStartDate(Calendar.getInstance().getTime());
		if (reportForm.getEndDate()==null) reportForm.setEndDate(Calendar.getInstance().getTime());
		
		List<Income> listIncome = this.incomeRepo.listIncome(reportForm.getStartDate(), reportForm.getEndDate());
		List<Expenditure> listExpenditure = this.expenditureRepo.listExpenditure(reportForm.getStartDate(), reportForm.getEndDate());
		
		ExcelFileGenerator generator = new ExcelFileGenerator(listIncome, listExpenditure);
		
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=account-report-" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        generator.generateExcelFile(response);
	}
}
