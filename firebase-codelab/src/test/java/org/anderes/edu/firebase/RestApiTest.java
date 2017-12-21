package org.anderes.edu.firebase;

import static java.lang.Boolean.TRUE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.text.RandomStringGenerator;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

public class RestApiTest {

    private UriBuilder restUrl;
    private Client client;
    private static String accessToken;
    private final RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();

    @BeforeClass
    public static void setupOnce() {
        GoogleCredential googleCred;
        try(FileInputStream fis = new FileInputStream("codelab.service.account.json")) {
            googleCred = GoogleCredential.fromStream(fis);
            GoogleCredential scoped = googleCred.createScoped(
                Arrays.asList(
                    "https://www.googleapis.com/auth/firebase.database",  // or use firebase.database.readonly for read-only access
                    "https://www.googleapis.com/auth/userinfo.email"
                    )
                );
            scoped.refreshToken();
            accessToken = scoped.getAccessToken();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
    
    @Before
    public void setUp() throws Exception {
      
        restUrl = UriBuilder.fromPath("/").host("codelab-82e5d.firebaseio.com").scheme("https");
        client = ClientBuilder.newBuilder()
                        .register(JsonProcessingFeature.class)
                        .property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, TRUE)
                        .build();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }
    
    @Test
    public void getAllMessages() {

        restUrl.path("messages.json");
        restUrl.queryParam("access_token", accessToken);
        Response response = client.target(restUrl).request().get();

        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        JsonObject value = response.readEntity(JsonObject.class);
        navigateTree(value, "messages");
    }
    
    private void navigateTree(JsonValue tree, String key) {
        if (key != null) {
           System.out.print("Key " + key + ": ");
        }
        switch(tree.getValueType()) {
           case OBJECT:
              System.out.println("OBJECT");
              JsonObject object = (JsonObject) tree;
              for (String name : object.keySet()) {
                 navigateTree(object.get(name), name);
              }
              break;
           case ARRAY:
              System.out.println("ARRAY");
              JsonArray array = (JsonArray) tree;
              for (JsonValue val : array) {
                 navigateTree(val, null);
              }
              break;
           case STRING:
              JsonString st = (JsonString) tree;
              System.out.println("STRING " + st.getString());
              break;
           case NUMBER:
              JsonNumber num = (JsonNumber) tree;
              System.out.println("NUMBER " + num.toString());
              break;
           case TRUE:
           case FALSE:
           case NULL:
              System.out.println(tree.getValueType().toString());
              break;
        }
     }
    
    @Test
    public void insertNewMessagePOST() throws FileNotFoundException, IOException {
        
        final JsonObject message = Json.createObjectBuilder()
                        .add("name", generator.generate(15))
                        .add("text", generator.generate(55))
                        .add("photoUrl", generator.generate(55))
                        .build();
        Entity<JsonObject> entity = Entity.entity(message, APPLICATION_JSON_TYPE);
        
        restUrl.path("messages.json");
        restUrl.queryParam("access_token", accessToken);
        final Response response = client.target(restUrl).request().buildPost(entity).invoke();
        
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        
        JsonObject value = response.readEntity(JsonObject.class);
        System.out.println(value.toString());
        // z.B. { "name": "-K2ib4H77rj0LYewF7dP" }
        assertThat(value.containsKey("name"), is(true));
        assertThat(value.getString("name").length(), is(20));   // Message-ID
    }
    
    @Test
    public void insertNewMessagePUT() throws FileNotFoundException, IOException {
        
        final JsonObject message = Json.createObjectBuilder()
                        .add("name", generator.generate(15))
                        .add("text", generator.generate(55))
                        .add("photoUrl", generator.generate(55))
                        .build();
        Entity<JsonObject> entity = Entity.entity(message, APPLICATION_JSON_TYPE);
        
        restUrl.path("messages/" + UUID.randomUUID().toString() + ".json");
        restUrl.queryParam("access_token", accessToken);
        final Response response = client.target(restUrl).request().buildPut(entity).invoke();
        
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        
        JsonObject value = response.readEntity(JsonObject.class);
        
        // Der Response liefert das komplette Json zurück
        assertThat(value.containsKey("name"), is(true));
        assertThat(value.containsKey("text"), is(true));
        assertThat(value.containsKey("photoUrl"), is(true));
        assertThat(value.getString("name").length(), is(15));
        assertThat(value.getString("text").length(), is(55));
        assertThat(value.getString("photoUrl").length(), is(55));
    }
        
}
