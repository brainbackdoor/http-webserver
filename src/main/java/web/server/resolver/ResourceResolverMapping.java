package web.server.resolver;

import web.protocol.http.message.request.RequestMessage;
import web.util.FileUtils;

import java.util.Set;

public class ResourceResolverMapping {
    private String path;
    private Set<String> extensions;

    public ResourceResolverMapping(String path, Set<String> extensions) {
        this.path = path;
        this.extensions = extensions;
    }

    public String resolve(RequestMessage request) {
        if(!isTarget(request.getPath())) {
            throw new IllegalArgumentException("Not supported extension");
        }
        return path + request.getPath();
    }

    public boolean isTarget(String path) {
        return extensions.contains(FileUtils.getExtension(path));
    }
}
