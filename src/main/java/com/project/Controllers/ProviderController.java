package com.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Provider;
import com.project.repositories.ProviderRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

@Controller
@RequestMapping("/provider/")

public class ProviderController {
	
	//System.getProperty("user.dir") : emplacement du projet sur le serveur(disc)
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads";
	
	
	@Autowired
	private final ProviderRepository providerRepository;

	// injection de dépencence + IOC(Inversion Of Control)
	public ProviderController(ProviderRepository providerRepository) {
		this.providerRepository = providerRepository;
	}

	@GetMapping("list")
	public String listProviders(Model model) {
		List<Provider> lp = (List<Provider>) providerRepository.findAll();
		if (lp.size() == 0)
			lp = null;
		// System.out.println(lp);
		// System.out.println(lp.size());
		model.addAttribute("providers", lp);
		System.out.println("yessssssssssssss!!!!");
		System.out.println(lp);

		return "provider/listProviders";
	}

	@GetMapping("add")
	public String showAddProviderForm(Model model) {
		System.out.println("form adddd");
		Provider provider = new Provider();// object dont la valeur des attributs par defaut
		model.addAttribute("provider", provider);
		return "provider/addProvider";
	}

	@PostMapping("add")
	public String addProvider(@Valid Provider provider, BindingResult result, @RequestParam("files") MultipartFile[] files) {
		System.out.println("actionnn  adddd");

		if (result.hasErrors()) {
			return "provider/addProvider";
		}
		
		/// start upload
    	StringBuilder fileName = new StringBuilder();
    	LocalDateTime ldt = LocalDateTime.now();
    	MultipartFile file = files[0];
    	String finalName = getSaltString().concat(file.getOriginalFilename()); 
    	Path fileNameAndPath = Paths.get(uploadDirectory, finalName);
    	
    	fileName.append(finalName);
		  try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		/// end upload
		provider.setPicture(fileName.toString());
		providerRepository.save(provider);
		return "redirect:list";
	}
	
	
	// rundom string to be used to the image name
	 	protected static String getSaltString() {
	 		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	 		StringBuilder salt = new StringBuilder();  
	 		Random rnd = new Random();
	 		while (salt.length() < 18) { // length of the random string.
	 			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	 			salt.append(SALTCHARS.charAt(index));
	 		}
	 		String saltStr = salt.toString();
	 		return saltStr;

	 	}

	@GetMapping("delete/{id}")
	public String deleteProvider(@PathVariable("id") long id, Model
	model) {
	//long id2 = 100L;
	Provider provider = providerRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
	System.out.println("suite du programme...");
	providerRepository.delete(provider);
	/*model.addAttribute("providers",
	providerRepository.findAll());
	return "provider/listProviders";*/
	return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showProviderFormToUpdate(@PathVariable("id") long
			id, Model model) {
			Provider provider = providerRepository.findById(id)
			.orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
			model.addAttribute("provider", provider);
			return "provider/updateProvider";
			}

	@PostMapping("update")
	public String updateProvider(@Valid Provider provider, BindingResult result, Model model) {
		providerRepository.save(provider);
		return "redirect:list";
	}
	
	@GetMapping("show/{id}")
	public String showProvider(@PathVariable("id") long id, Model model) {
		Provider provider = providerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));
				model.addAttribute("provider", provider);
		return "provider/showProvider";
	}
	
	@PostMapping("search")
	//@ResponseBody
	public String findProviderByName(@RequestParam("nom")String name,Model model) {
		//List<Provider> lp = providerRepository.ListProviderName(name);
		List<Provider> lp = providerRepository.findProviderByNameLike(name);
		
		if (lp.size() == 0)
			lp = null;
		 System.out.println(lp);
		// System.out.println(lp.size());
		model.addAttribute("providers", lp);
		return "provider/listProviders";
		//return "Vous avez taper : "+ name;
	}

}
