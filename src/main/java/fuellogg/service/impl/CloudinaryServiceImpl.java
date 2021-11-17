package fuellogg.service.impl;

import com.cloudinary.Cloudinary;
import fuellogg.service.CloudinaryImage;
import fuellogg.service.CloudinaryService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryServiceImpl implements CloudinaryService {
    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = cloudinary.
                    uploader().
                    upload(tempFile, Map.of());

            String url = uploadResult.getOrDefault(URL,
                    "https://img.lovepik.com/free-png/20210926/lovepik-car-icon-png-image_401452256_wh1200.png");
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

            return new CloudinaryImage().
                    setPublicId(publicId).
                    setUrl(url);

        } finally {
            tempFile.delete();
        }
    }

    @Override
    public boolean delete(String publicId) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
