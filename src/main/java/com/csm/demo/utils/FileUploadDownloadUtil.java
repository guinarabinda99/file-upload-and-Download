package com.csm.demo.utils;



import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDownloadUtil {
	
	       //This Method is used for store the file in the given path
			public static boolean saveFile(String uploadDir,String fileName,MultipartFile multipartfile) throws IOException {
				boolean status=false;
				InputStream inputStream=null; 
				try {
					Path uploadPath = Paths.get(uploadDir);
			         
			        if (!Files.exists(uploadPath)) { //If the upload path is not present create the directory
			            Files.createDirectories(uploadPath);
			        }
			       
			        inputStream=multipartfile.getInputStream();
			        Path filePath = uploadPath.resolve(fileName);
		            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); //Store the Files into upload directory
		            status=true;
		            
					} catch (IOException  ioe) {
						// TODO: handle exception
						 throw new IOException("Could not save file: " + fileName, ioe);
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}finally {
						if(inputStream!=null) {
						   inputStream.close();
						}
						
					}
					
				
				
				return status;
				
			}
			
			
		
			
		
	}


