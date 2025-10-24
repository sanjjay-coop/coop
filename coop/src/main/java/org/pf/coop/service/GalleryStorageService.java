package org.pf.coop.service;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface GalleryStorageService {

	public void init(Path root);

	public void save(MultipartFile file, Path root);

	public void save(MultipartFile file, Path root, String fileName);

	public Resource load(String filename, Path root);

	public boolean deleteAll(Path root);
	
	public void delete(File file);

	public Stream<Path> loadAll(Path root);

}
