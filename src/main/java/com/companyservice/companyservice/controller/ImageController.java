package com.companyservice.companyservice.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Authorization.authorizationservice.annotation.Authorization;
import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.Images;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.ImageService;

//@Authorization
@CrossOrigin
@RestController
@Logging
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping("/image/save/{userId}")
	public ResponseEntity<?> save(@PathVariable String userId, @RequestParam("file") MultipartFile file) throws Exception{
		Images images = imageService.save(userId,file);
		if(images.getImageId() != null) {
			return new ResponseEntity<>(images, HttpStatus.CREATED);
		}else {
			throw new BadRequestException("Image data storing failed.!");
		}
	}
	
	@GetMapping("/get/image/{userId}")
	public ResponseEntity<?> get(@PathVariable String userId) throws Exception{
		Images images = imageService.get(userId);
		if(images != null) {
			return new ResponseEntity<>(images, HttpStatus.OK);
		}else {
			throw new BadRequestException("Image data not found.!");
		}
	}
	@PutMapping("/image/update/{id}")
	public ResponseEntity<?> update(@PathVariable String id,@RequestParam("file") MultipartFile file) throws Exception{
		Images images = imageService.update(id, file);
		if(images != null) {
			return new ResponseEntity<>(images, HttpStatus.OK);
		}else {
			throw new BadRequestException("Image data not found.!");
		}
	}
	@DeleteMapping("/image/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws Exception{
		String response =  imageService.remove(id);
		if(response != "") {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Image details not found");
		}
	}
	@GetMapping("/image/view/{imageName}")
	@ResponseBody
	public ResponseEntity<Resource> viewFile(@PathVariable String imageName,HttpServletRequest request) throws MalformedURLException {
				Resource resource =imageService.viewFile(imageName);
				 String contentType = null;
			        try {
			            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			        } catch (IOException ex) {
			            //logger.info("Could not determine file type.");
			        }

			        // Fallback to the default content type if type could not be determined
			        if(contentType == null) {
			            contentType = "application/octet-stream";
			        }
				return ResponseEntity.ok()
						 .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
	}
}
