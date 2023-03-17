package com.financialeducation.virtualwallet.services;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

	public void init() throws IOException;

	public String store(MultipartFile multipartFile);

	public Resource loadResource(String fileName);

}
