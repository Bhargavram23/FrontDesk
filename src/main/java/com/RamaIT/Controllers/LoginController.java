package com.RamaIT.Controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import com.RamaIT.Models.Executive;
import com.RamaIT.Services.LoginService;
import com.RamaIT.Utils.EmailUtils;
import com.RamaIT.Utils.ExecutiveUtils;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	@Autowired
	ExecutiveUtils executiveUtils;
	@Autowired
	EmailUtils emailUtils;
	@Autowired
	LoginService loginService;
//	@Autowired
//	StudentRepository studentRepo;
//	@Autowired
//	ExecutiveRepository executiveRepo;

	Context context;

	@GetMapping(path = { "/", "/home" })
	public String Publichomepage() {
		return "home";
	}

	@GetMapping("/login")
	public ModelAndView loadLoginPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("executive", new Executive());
		return mav;
	}

	@PostMapping("/login")
	public String SignInLoginPage(@ModelAttribute(name = "executive") Executive executive, BindingResult results,
			Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// on successful login return to dash board
		executive = loginService.getExecutiveByExample(Example.of(executive));
		request.getSession().setAttribute("loggedUser", executive);
		if (executive != null) {
			if (loginService.isValidExecutive(executive)) {
				request.getSession().setAttribute("loggedUser", executive);
				if (loginService.isVerfiedExecutive(executive)) {
					response.sendRedirect("/dashboard");
				} else {
					return "confirmEmailPage";
				}
			}
		}
		else {
		model.addAttribute("message", "Please Recheck your credentials!!");
		response.sendRedirect("/login");
		}
		return "login";
		
	}

	@GetMapping("/register")
	public ModelAndView loadRegisterPage() {
		ModelAndView mav = new ModelAndView("register");
		mav.addObject("executive", new Executive());
		return mav;
	}

	@PostMapping("/register")
	public ModelAndView AddUserbyRegisterPage(@ModelAttribute(name = "executive") Executive executive)
			throws MessagingException, IOException {
		ModelAndView mav = new ModelAndView("register");
		executive = loginService.addUnVerifiedExecutive(executive);
		context = new Context();
		context.setVariable("id", executive.getId());
		boolean sendConfirmationEmail = emailUtils.sendConfirmationEmail(executive.getEmail(), "Front Home Application verification",
				 context);
		if (sendConfirmationEmail) {
			mav.setViewName("confirmEmailPage");
			return mav;
		}
		mav.setViewName("home");
		return mav;
	}

	@GetMapping(path = { "/verify/{id}" })
	public String VerifyPageLoad(@PathVariable Integer id) {
		Optional<Executive> executiveById = loginService.getExecutiveById(id);
		if(executiveById.isEmpty()) {
			return "login";
		}
		if (!executiveById.get().isVerified()) {
			loginService.verifyUser(executiveById.get());
		}
		return "home";
	}

	@GetMapping("/forgot")
	public String loadForgotPage() {
		return "forgot";
	}
}
