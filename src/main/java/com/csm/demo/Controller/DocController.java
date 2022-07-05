package com.csm.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csm.demo.Model.Doc;
import com.csm.demo.service.docService;

@Controller
public class DocController {
	
	@Autowired
	private docService service;
	
	@GetMapping("/")
	public String get(Model model) {
		List<Doc> doc=service.getFiles();
		model.addAttribute("docs", doc);
		return "doc";
		
	}
	@PostMapping("/uploadFiles")
	public String upload(@RequestParam("files") MultipartFile[] files)
	{
		for(MultipartFile file:files) {
			service.saveFile(file);
		}
		return "redirect:/";
		
	}
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
		
		Doc doc=service.getFile(fileId).get();
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(doc.getDocType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+doc.getName()+"\"")
				.body(new ByteArrayResource(doc.getData()));
		
	}
	

}
