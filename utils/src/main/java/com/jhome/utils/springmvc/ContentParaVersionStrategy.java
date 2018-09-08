package com.jhome.utils.springmvc;


import org.springframework.core.io.Resource;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.AbstractVersionStrategy;
import org.springframework.web.servlet.resource.VersionPathStrategy;

import java.io.IOException;
import java.util.regex.Pattern;


/**
 * 参数方式的版本，方便nginx 做动静分离
 */
public class ContentParaVersionStrategy extends AbstractVersionStrategy {

    public ContentParaVersionStrategy() {
        super(new FileNameVersionPathStrategy());
    }


    @Override
    public String getResourceVersion(Resource resource) {
        try {
            byte[] content = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return DigestUtils.md5DigestAsHex(content);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to calculate hash for resource [" + resource + "]", ex);
        }
    }

    /**
     * File name-based {@code VersionPathStrategy},
     * e.g. {@code "path/foo-{version}.css"}.
     */
    protected static class FileNameVersionPathStrategy implements VersionPathStrategy {

        private static final Pattern pattern = Pattern.compile("-(\\S*)\\.");

        @Override
        public String extractVersion(String requestPath) {
            return null;
        }

        @Override
        public String removeVersion(String requestPath, String version) {
            return requestPath;
        }

        @Override
        public String addVersion(String requestPath, String version) {
            String baseFilename = StringUtils.stripFilenameExtension(requestPath);
            String extension = StringUtils.getFilenameExtension(requestPath);
            return baseFilename + "." + extension+"?v_" + version ;
        }
    }
}
