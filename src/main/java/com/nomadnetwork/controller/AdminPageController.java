package com.nomadnetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nomadnetwork.entity.Place;
import com.nomadnetwork.entity.Post;
import com.nomadnetwork.entity.Report;
import com.nomadnetwork.repository.PlaceRepo;
import com.nomadnetwork.repository.ReportRepository;
import com.nomadnetwork.services.AdminService;
import com.nomadnetwork.services.DashboardService;
import com.nomadnetwork.services.PostService;
import com.nomadnetwork.services.ReportService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPageController {
	

    private final DashboardService dashboardService;

    public AdminPageController(DashboardService dashboardService,PlaceRepo placeRepository) {
        this.dashboardService = dashboardService;
        this.placeRepository = placeRepository;
    }
	
	@Autowired
    private AdminService adminService;
	@Autowired
    private ReportRepository reportRepository;
	@Autowired
    private PostService postService;
	@Autowired
	private ReportService reportService;
	private final PlaceRepo placeRepository;
	
	
	@GetMapping("/dashboard")
    public String adminDashboard(Model model) {
		
		 model.addAttribute("userCount", dashboardService.getUserCount());
	     model.addAttribute("postCount", dashboardService.getPostCount());
	     model.addAttribute("placeCount", dashboardService.getPlaceCount());
	     model.addAttribute("reportCount", dashboardService.getReportCount());
	     model.addAttribute("recentPosts", adminService.getRecentPosts());
	     
	     model.addAttribute("reportsCount", reportService.getReportCount());
	     model.addAttribute("reports", reportService.getAllReports());
	     
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
    	model.addAttribute("users", adminService.getAllUsers());
        return "admin/users";
    }
    
    @PostMapping("/users/{id}/role")
    public String changeRole(@PathVariable Long id,
                             @RequestParam String role) {
        adminService.changeUserRole(id, role);
        return "redirect:/admin/users";
    }

    @GetMapping("/posts")
    public String posts(Model model) {
    	model.addAttribute("posts", adminService.getAllPosts());
        return "admin/posts";
    }
    
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        adminService.deletePost(id);
        return "redirect:/admin/posts";
    }
    
    @GetMapping("/places")
    public String getAllPlaces(Model model) {
        model.addAttribute("places", placeRepository.findAll());
        return "admin/place";
    }

    
    @PostMapping("/places/{id}/update")
    public String updatePlace(@PathVariable Long id,
                              @RequestParam String description,
                              @RequestParam(required = false) Double latitude,
                              @RequestParam(required = false) Double longitude,
                              @RequestParam String city,
                              @RequestParam String country) {

        Place place = placeRepository.findById(id).orElseThrow();

        place.setDescription(description);
        place.setLatitude(latitude);
        place.setLongitude(longitude);
        place.setCity(city);
        place.setCountry(country);

        placeRepository.save(place);

        return "redirect:/admin/places";
    }


    @GetMapping("/scams")
    public String manageScams() {
        return "admin/scams";
    }
    
    @GetMapping("/reports")
    public String viewReports(Model model) {

        List<Object[]> reports = reportRepository.findReportedPostsWithCount();

        model.addAttribute("reports", reports);

        return "admin/reports";
    }
    
    @PostMapping("/posts/{id}/delete")
    public String deleteReportedPost(@PathVariable Long id) {

        postService.deletePostByAdmin(id);
        return "redirect:/admin/reports";
    }
    
    @PostMapping("/posts/{id}/clear-reports")
    public String clearReports(@PathVariable Long id) {

        Post post = postService.getPostEntityById(id);
        List<Report> reports = reportRepository.findByPost(post);

        reportRepository.deleteAll(reports);

        return "redirect:/admin/reports";
    }
}

