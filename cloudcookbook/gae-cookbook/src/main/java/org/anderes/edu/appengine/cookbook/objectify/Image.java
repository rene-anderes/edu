
package org.anderes.edu.appengine.cookbook.objectify;

import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Image {

	private @Id String uuid;
    private String url;
    private String description;

    private Image() {
    	super();
    }

    public Image(final String id, String url, String description) {
        this();
        this.uuid = id;
        this.url = url;
        this.description = description;
    }

    public Image(String url, String description) {
        this(UUID.randomUUID().toString(), url, description);
    }

    public String getUuid() {
		return uuid;
	}

	public Image setUuid(String uuid) {
		this.uuid = uuid;
		return this;
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
        Image rhs = (Image) obj;
        return new EqualsBuilder().append(url, rhs.url).append(description, rhs.description).isEquals();
    }

}
