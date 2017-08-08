package org.anderes.edu.jaxrs.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Resource {
    
    private List<Link> links = new ArrayList<>();

    @JsonProperty(access=Access.WRITE_ONLY)
    public List<Link> getLinks() {
        return links;
    }
    
    @JsonProperty(access=Access.WRITE_ONLY)
    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
