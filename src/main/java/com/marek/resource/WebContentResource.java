package com.marek.resources;

import com.marek.repository.WebContentRepository;
import com.marek.model.WebContent;
import com.marek.service.QueueService;
import com.marek.service.WebContentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/webcontent")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WebContentResource {
    @Inject
    WebContentRepository webContentRepository;

    @Inject
    QueueService queueService;

    @Inject
    WebContentService webContentService;

    @GET
    public WebContent getWebContentByURL(@QueryParam("url") String url) {
        WebContent webContent = webContentRepository.getByURL(url);
        return webContent;
    }

    @GET
    @Path("/all")
    public List<WebContent> getAllWebContent() {
        return webContentRepository.getAllWebContents();
    }

    @GET
    @Path("/search")
    public List<WebContent> getAllWebContentWithSearchParameter(@QueryParam("searchParameter") String searchParameter) {
        return webContentRepository.searchWebContentsWithGivenString(searchParameter);
    }

    @POST
    public Response saveWebContent(@QueryParam("url") String url) {
        queueService.addToQueue(url);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}





