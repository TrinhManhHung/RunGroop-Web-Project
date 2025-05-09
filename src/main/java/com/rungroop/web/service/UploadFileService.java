package com.rungroop.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//

@Service
public class UploadFileService {

    // Dùng user.dir để xác định thư mục gốc ứng dụng khi chạy
    private static final String UPLOAD_ROOT = System.getProperty("user.dir") + File.separator + "uploads";

    public String handleSaveUploadedFile(MultipartFile file, String targetFolder) {
        if (file.isEmpty()) {
            return "";
        }

        try {
            // Tạo thư mục: {user.dir}/uploads/{targetFolder}
            File dir = new File(UPLOAD_ROOT + File.separator + targetFolder);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destination = new File(dir, filename);
            file.transferTo(destination);

            // In ra console để debug path lưu file
            System.out.println("Saved file to: " + destination.getAbsolutePath());

            // Trả về URL tương đối để lưu CSDL và show
            return "/uploads/" + targetFolder + "/" + filename;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

