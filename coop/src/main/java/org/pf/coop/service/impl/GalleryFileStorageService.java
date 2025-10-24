package org.pf.coop.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.pf.coop.service.GalleryStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GalleryFileStorageService implements GalleryStorageService {

	//@Autowired
	//GlobalParametersService gps;
	
	//private final Path root = Paths.get(gps.getGlobalParameters().getMediaLocation());

	@Override
	public void init(Path root) {
		try {
			Files.createDirectories(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void save(MultipartFile file, Path root) {
		try {
			Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			if (e instanceof FileAlreadyExistsException) {
				throw new RuntimeException("Filename already exists.");
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void save(MultipartFile file, Path root, String fileName) {
		try {
			Files.copy(file.getInputStream(), root.resolve(fileName));
		} catch (Exception e) {
			if (e instanceof FileAlreadyExistsException) {
				throw new RuntimeException("Filename already exists.");
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Resource load(String filename, Path root) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public boolean deleteAll(Path root) {
		return FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public void delete(File file) {
		FileSystemUtils.deleteRecursively(file);
	}
	
	@Override
	public Stream<Path> loadAll(Path root) {
		try {
			return Files.walk(root, 1).filter(path -> !path.equals(root)).map(root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}
}
