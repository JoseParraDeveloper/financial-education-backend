package com.financialeducation.virtualwallet.restcontrollers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.financialeducation.virtualwallet.services.IStorageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/media")
@AllArgsConstructor
@CrossOrigin
public class MediaController {

	private final IStorageService storageService;

	private final HttpServletRequest request;

	@PostMapping("upload")
	public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) {
		String path = storageService.store(file);
		String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
		String url = ServletUriComponentsBuilder.fromHttpUrl(host).path("/media/").path(path).toUriString();
		return Map.of("url", url);
	}

	@GetMapping("{fileName}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws IOException {
		Resource file = storageService.loadResource(fileName);
		String contentType = Files.probeContentType(file.getFile().toPath());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType).body(file);
	}

}
