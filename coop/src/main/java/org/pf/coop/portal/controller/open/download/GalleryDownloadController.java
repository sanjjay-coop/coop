package org.pf.coop.portal.controller.open.download;

import org.pf.coop.portal.controller.BaseController;
import org.pf.coop.portal.model.Gallery;
import org.pf.coop.portal.service.GalleryService;
import org.pf.coop.service.impl.GalleryFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/open/gallery/download")
public class GalleryDownloadController  extends BaseController {

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	GalleryFileStorageService storageService;
	
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
	    
		Gallery gallery = (Gallery) galleryService.getById(id);
		
		return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + gallery.getFileName() + "\"")
		        .body(gallery.getFileData());
	}
}
