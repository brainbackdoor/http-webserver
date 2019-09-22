package web.server.resolver;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.response.ResponseMessage;
import web.util.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResourceResolver implements Resolver {
    private static final Logger log = LoggerFactory.getLogger(ResourceResolver.class);
    private static final String STATIC_PATH = "./static";
    private static final Set<String> STATIC_EXTENSION = Sets.newHashSet(".css", ".js");
    private static final String TEMPLATE_PATH = "./templates";
    private static final Set<String> TEMPLATE_EXTENSION = Sets.newHashSet(".html");

    private List<ResourceResolverMapping> resourceResolverMappings = new ArrayList<>();

    public ResourceResolver() {
        ResourceResolverMapping staticMapping = new ResourceResolverMapping(STATIC_PATH, STATIC_EXTENSION);
        ResourceResolverMapping templateMapping = new ResourceResolverMapping(TEMPLATE_PATH, TEMPLATE_EXTENSION);

        resourceResolverMappings.add(staticMapping);
        resourceResolverMappings.add(templateMapping);
    }

    @Override
    public void resolve(RequestMessage request, ResponseMessage response) {
        ResourceResolverMapping resourceResolverMapping = getResourceResolverMapping(request.getPath());
        try {
            byte[] body = FileUtils.loadFileFrom(resourceResolverMapping.resolve(request));
            response.ok(body);
        } catch (IOException e) {
            log.error(e.getMessage());
            response.notFound(e);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }

    }

    private ResourceResolverMapping getResourceResolverMapping(String path) {
        String fileExtension = FileUtils.getExtension(path);
        return resourceResolverMappings.stream()
                .filter(v -> v.isTarget(fileExtension))
                .findFirst().orElseThrow(() -> new IllegalArgumentException());
    }
}
