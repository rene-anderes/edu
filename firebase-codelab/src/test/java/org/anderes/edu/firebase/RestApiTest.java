package org.anderes.edu.firebase;

import static java.lang.Boolean.TRUE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

public class RestApiTest {

    private UriBuilder restUrl;
    private Client client;

    @Before
    public void setUp() throws Exception {
      
        restUrl = UriBuilder.fromPath("messages.json").host("codelab-82e5d.firebaseio.com").scheme("https");
        /** 
         * Hier wird das Jackson Feature registriert.
         * Eine manuelle Registrierung eines Feature verhindert, dass Jersey
         * die Feature automatisch registriert, daher wäre das "disable" 
         * des auto discovery gar nicht notwendig. Wird hier jedoch exemplarisch explizit gemacht.
         * siehe https://jersey.java.net/documentation/latest/user-guide.html#json.jackson 
         */
        client = ClientBuilder.newBuilder()
                        .register(JacksonFeature.class)
                        .property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, TRUE)
                        .property(CommonProperties.MOXY_JSON_FEATURE_DISABLE_CLIENT, TRUE)
                        .build();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }
    
    @Test
    public void token() throws FileNotFoundException, IOException {
        GoogleCredential googleCred = GoogleCredential.fromStream(new FileInputStream("codelab.service.account.json"));
        GoogleCredential scoped = googleCred.createScoped(
            Arrays.asList(
              "https://www.googleapis.com/auth/firebase.database",  // or use firebase.database.readonly for read-only access
              "https://www.googleapis.com/auth/userinfo.email"
            )
        );
        scoped.refreshToken();
        String token = scoped.getAccessToken();
        System.out.println(token);
        System.out.println(scoped.getExpiresInSeconds());
        
        restUrl.queryParam("access_token", token);
        Response response = client.target(restUrl).request().get();
        assertThat(response.getStatusInfo(), is(Status.OK));
        
        Entity<Message> entity = Entity.entity(new Message("Bill", "Microsoft"), MediaType.APPLICATION_JSON_TYPE);
        response = client.target(restUrl).request().buildPost(entity).invoke();
        
        assertThat(response.getStatusInfo(), is(Status.OK));
    }
    
    private class Message {
        private String name;
        private String text;
       
        public Message(String name, String text) {
            super();
            this.setName(name);
            this.setText(text);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
