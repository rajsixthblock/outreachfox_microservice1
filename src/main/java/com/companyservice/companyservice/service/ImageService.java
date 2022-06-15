package com.companyservice.companyservice.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.companyservice.companyservice.entity.Images;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.ImageRepository;
import com.companyservice.companyservice.repository.UserRepository;
@Service
public class ImageService {
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private UserRepository userRepository;
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads/images/";
	
	public Images save(String userId,MultipartFile file) throws Exception {
		Files.createDirectories(Paths.get(uploadDirectory));
		file.transferTo( new File(uploadDirectory+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+file.getOriginalFilename()));
		Images images = new Images();
		User userDetails = userRepository.getById(userId);
		if(userDetails.getUserId() != null) {
			
			images.setCompanyId(userDetails.getCompanyId());
			images.setUserId(userDetails);
			images.setImageName(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+file.getOriginalFilename());
			images.setOriginalName(file.getOriginalFilename());
			images.setPath(uploadDirectory+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+file.getOriginalFilename());
			images.setType(file.getContentType());
			try {
				images = imageRepository.save(images);
			}catch(Exception e) {
				if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
			return images;
		}
		return images;
	}
	public Images get(String userId) throws Exception {
		Images images = new Images();
		User user = new User();
		user.setUserId(userId);
		try {
			images = imageRepository.getByUserId(user);
			if(images.getImageId() != null) {
				return images;
			}
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return images;
	}
	public Images update(String id, MultipartFile file) throws Exception {
		Files.createDirectories(Paths.get(uploadDirectory));
		file.transferTo( new File(uploadDirectory+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+file.getOriginalFilename()));
		Images images = new Images();
		images = imageRepository.getById(id);
		System.out.println(images);
		if(images.getImageId() != null) {
			images.setImageName(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+file.getOriginalFilename());
			images.setOriginalName(file.getOriginalFilename());
			images.setPath(uploadDirectory+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+file.getOriginalFilename());
			images.setType(file.getContentType());
			try {
				images = imageRepository.save(images);
				return images;
			}catch(Exception e) {
				if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}else {
			throw new DetailsNotFound("Image details not found");
		}
		return images;
	}
	public String remove(String id) throws Exception {
		Images images = new Images();
		if(imageRepository.existsById(id)) {
			try {
				images = imageRepository.getById(id);
				boolean result = Files.deleteIfExists(Paths.get(images.getPath()));
				if(result) {
					imageRepository.deleteById(id);
					return "Image info deleted successfully.!";
				}
				
			}catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}else 
			throw new DetailsNotFound("Image info does not exist.!"); 
			return null;
	}
	public Resource viewFile(String fileName) throws MalformedURLException {
		Path file = Paths.get(uploadDirectory)
                .resolve(fileName).normalize();
		Resource resource = new UrlResource(file.toUri());
		if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file!");
        }
	}
}
