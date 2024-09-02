package  org.example.project2.storage ;

import org.example.project2.storage.FileStorage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SystemFileStorage implements FileStorage {

    // Define the storage directory in the user's home directory
    private final String storageDirectory = System.getProperty("user.home") + "/product-images";

    public SystemFileStorage() {
        createDirectoryIfNotExists();
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        // Generate a unique file name to avoid conflicts
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(storageDirectory, fileName);
        Files.write(filePath, file.getBytes());
        // Return the relative URL to the server root
        return fileName;
    }

    public byte[] getFile(String fileName) throws IOException {
        Path filePath = Paths.get(storageDirectory, fileName);
        return Files.readAllBytes(filePath);
    }

    // Create the directory in the home directory if it doesn't exist
    private void createDirectoryIfNotExists() {
        File directory = new File(storageDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
