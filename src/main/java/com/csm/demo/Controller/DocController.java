package com.csm.demo.Controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csm.demo.Model.Doc;
import com.csm.demo.Repository.docRepository;
import com.csm.demo.service.docService;
import com.csm.demo.utils.FileUploadDownloadUtil;

@Controller
public class DocController {

	@Autowired
	private docService service;

	@Autowired
	private docRepository docRepo;

	@Value("${student.upload.path}")
	private String docuploadpath;

	@GetMapping("/")
	public String get(Model model) {
		List<Doc> doc = service.getFiles();
		model.addAttribute("docs", doc);
		return "doc";

	}

	@PostMapping("/uploadFiles")
	public String upload(@RequestParam("files") MultipartFile file, @ModelAttribute("Doc") Doc doc) {
		String documentName = "";
		boolean docUploadStatus = false;
		try {

			if (file.getOriginalFilename().trim().isEmpty()) {
				doc.setName("NA");
			} else {
				if (file.getSize() <= 200000) {
					// name
					documentName = StringUtils.cleanPath(file.getOriginalFilename()).trim();
					doc.setName(documentName);
					doc.setData(file.getBytes());
					doc.setDocType(file.getContentType());
					docUploadStatus = FileUploadDownloadUtil.saveFile(docuploadpath, documentName, file);

				} else {
					docUploadStatus = false;
				}

			}
			if (docUploadStatus) {
				docRepo.save(doc);
				System.out.println("data saved successfully");
			} else {
				System.out.println("docfile upload failed");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/";

	}

	@GetMapping("/downloadFile/{fileId}")
	public void downloadFile(@PathVariable Integer fileId, HttpServletResponse response) throws IOException {

		Doc doc = service.getFile(fileId).get();
		String path = docuploadpath + "/" + doc.getName();
		File file = new File(path);
		if(!file.exists()){
			String errorMessage = "Sorry. The file you are looking for does not exist";
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		FileCopyUtils.copy(inputStream, response.getOutputStream());

	}

}
