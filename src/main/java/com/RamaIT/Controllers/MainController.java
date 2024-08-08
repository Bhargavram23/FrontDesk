package com.RamaIT.Controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.RamaIT.Enums.Course;
import com.RamaIT.Enums.Mode;
import com.RamaIT.Enums.Status;
import com.RamaIT.Models.Executive;
import com.RamaIT.Models.Student;
import com.RamaIT.Services.LoginService;
import com.RamaIT.Services.StudentService;
import com.RamaIT.Utils.ExecutiveUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {
	@Autowired
	StudentService studentService;
	@Autowired
	LoginService loginService;
	@Autowired
	ExecutiveUtils execUtils;
	Executive session_executive;

	@GetMapping("/dashboard")
	public ModelAndView loadDashboardPage(Model model, HttpServletRequest request) {

		session_executive = (Executive) request.getSession().getAttribute("loggedUser");

		ModelAndView mav = new ModelAndView("dashboard");
		mav.addObject("fail", execUtils.totalFailStudents(session_executive.getId()) + " Students failed");
		mav.addObject("success", execUtils.totalSuccesfulStudents(session_executive.getId()) + " Students successful");
		mav.addObject("total", execUtils.totalStudents(session_executive.getId()) + " Students total");
		return mav;
	}

	@GetMapping("/add")
	public ModelAndView loadAddPage() {
		ModelAndView mav = new ModelAndView("add");
		mav.addObject("courses", Course.values());
		mav.addObject("modes", Mode.values());
		mav.addObject("statuses", Status.values());
		mav.addObject("student", new Student());
		return mav;
	}

	@GetMapping("/add/{index}")
	public ModelAndView loadAddPage(@PathVariable Integer index) {
		Student student = studentService.getStudentById(index);
		ModelAndView mav = new ModelAndView("add");
		mav.addObject("courses", Course.values());
		mav.addObject("modes", Mode.values());
		mav.addObject("statuses", Status.values());
		mav.addObject("student", student);
		return mav;
	}

	@PostMapping("/add")
	public void AddPage(@ModelAttribute("student") Student student, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		session_executive = (Executive) request.getSession().getAttribute("loggedUser");

		if (student.getId() == null) {
			studentService.upsertStudentwithExecutive(student, session_executive);

		} else {
			studentService.updateStudent(student);
		}
		response.sendRedirect("/view");
	}

	@GetMapping("/view")
	public ModelAndView loadViewPage(HttpServletRequest request) {

		session_executive = (Executive) request.getSession().getAttribute("loggedUser");

		ModelAndView mav = new ModelAndView();
		Executive executive = loginService.getExecutiveById(session_executive.getId()).get();
		List<Student> students_for_Executive = studentService.getAllStudentsForExecutive(executive);
		mav.addObject("students", students_for_Executive);

		return mav;
	}

}
