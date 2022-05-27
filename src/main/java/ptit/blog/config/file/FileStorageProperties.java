package ptit.blog.config.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pat on 5/27/2022 - 1:38 PM
 *
 * @author pat
 * @project blog-version-0.2
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}