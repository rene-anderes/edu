
package org.anderes.edu.appengine.cookbook.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class ImageDto {

    private String url;
    private String description;

    public ImageDto() {
    }

    public ImageDto(String url, String description) {
        super();
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Image: [url='%s'], [description='%s']", url, description);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(url).append(description).toHashCode();

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ImageDto rhs = (ImageDto) obj;
        return new EqualsBuilder().append(url, rhs.url).append(description, rhs.description).isEquals();
    }

}
