package com.marek.service;

import com.marek.model.WebContent;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@Stateless
public class WebContentService {

    public WebContent getWebContent(String url) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(url);
            Invocation.Builder builder = target.request();
            Response response = builder.get();
            String contentType = response.getHeaderString("content-type");
            WebContent webContent = new WebContent();
            webContent.setPageContent(response.readEntity(byte[].class));
            webContent.setContentType(contentType);
            webContent.setUrl(url);
            return webContent;
        } finally {
            client.close();
        }
    }
}
