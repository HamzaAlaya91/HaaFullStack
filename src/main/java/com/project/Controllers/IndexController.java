package com.project.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entities.Provider;
import com.project.repositories.ProviderRepository;
import com.project.repositories.RoleRepository;
import com.project.repositories.UserRepository;

@Controller
@RequestMapping("/")
public class IndexController {

	
	private final ProviderRepository providerRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	
	@Autowired
	public IndexController(ProviderRepository providerRepository, RoleRepository roleRepository,
			UserRepository userRepository) {
		super();
		this.providerRepository = providerRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}


	@RequestMapping("index")
	public String listProviders(Model model) {
		System.out.println("index controller");
		long nbrProviders = providerRepository.count();
		long nbrUsers = userRepository.count();
		long nbrRoles = roleRepository.count();

		model.addAttribute("nbrPro", nbrProviders);
		model.addAttribute("nbrUse", nbrUsers);
		model.addAttribute("nbrRol", nbrRoles);

		return "/index";
	}

}
