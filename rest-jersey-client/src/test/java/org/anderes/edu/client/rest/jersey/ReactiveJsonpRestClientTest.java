package org.anderes.edu.client.rest.jersey;

import static java.lang.Boolean.TRUE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Beispiel für einen REST-Client der JSON mittels JOSN-P low level verarbeitet.
 * <p>
 * siehe https://javaee.github.io/jsonp/
 * 
 * @author René Anderes
 *
 */
public class ReactiveJsonpRestClientTest {
    
    private UriBuilder restUrl;
    private Client client;

    @Before
    public void setUp() throws Exception {
      
        restUrl = UriBuilder.fromPath("resources-api").path("recipes-repository").host("www.anderes.org").scheme("http");
        
        /** 
         * Hier wird explizit das JSON-P Feature registriert. Dies ist nicht notwendig, da Jersey
         * das entsprechende Feature im Klassenpfad hat und dadurch automatisch registriert.
         * Das Ausschalten des 'auto discovery' wird hier nur exemplarisch gemacht und ist für
         * JSON-P nicht notwendig.
         * siehe https://jersey.github.io/documentation/latest/user-guide.html#deployment.autodiscoverable 
         */
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
    public void shouldBeFindAllRecipe() {
        CompletionStage<Response> recipeList = client.target(restUrl)
                        .request(APPLICATION_JSON_TYPE)
                        .rx()
                        .get();
        
        recipeList.thenApply(response -> {
            assertThat(response.getStatus(), is(OK.getStatusCode()));
            assertThat(response.hasEntity(), is(true));
            final JsonObject content = response.readEntity(JsonObject.class);
            final JsonArray array = content.getJsonArray("content");
            assertThat(array, is(notNullValue()));
            return array;
        })
        .exceptionally(throwable -> { 
            System.out.println(throwable); 
            return Json.createArrayBuilder().build();
        })
        .thenApply((array) -> {
                        array.stream()
                            .peek(element -> System.out.println(element))
                            .forEach(element -> assertThat(((JsonObject)element).containsKey("uuid"), is(true)));
                        return array.stream()
                                      .map(element -> ((JsonObject)element).getJsonArray("links").stream()
                                                          .filter(link -> ((JsonObject)link).getString("rel").equals("self"))
                                                          .map(link -> ((JsonObject)link).getString("href"))
                                                          .findFirst())
                                      .collect(Collectors.toList());
        })
        .thenApply(urls -> handleRecipes(urls))
        .whenComplete((list, throwable) -> {
            if (throwable == null) {
                list.forEach(action -> {
                    action.thenAccept(recipes -> System.out.println(recipes));
                });
            } else {
                throwable.printStackTrace();
            }
        });
    }
    
    private List<CompletableFuture<JsonObject>> handleRecipes(List<Optional<String>> urls) {
        return urls.stream().map(url -> ((CompletableFuture<JsonObject>)handleUrl(url))).collect(Collectors.toList());
    }
    
    private CompletionStage<JsonObject> handleUrl(final Optional<String> url) {
        return ClientBuilder.newBuilder().build().target(url.get()).request(APPLICATION_JSON_TYPE)
                        .rx().get(JsonObject.class)
                        .exceptionally(throwable -> {
                            throwable.printStackTrace();
                            return Json.createObjectBuilder().build();
                        });
    }
    
}
