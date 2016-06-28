package org.anderes.edu.jpa.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.anderes.edu.jpa.domain.Ingredient;
import org.anderes.edu.jpa.domain.Recipe;
import org.anderes.edu.jpa.rules.DbUnitRule;
import org.anderes.edu.jpa.rules.DbUnitRule.UsingDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
                "classpath:application-context.xml",
                "classpath:unittest-application-context.xml",
                "classpath:unittest-security-context.xml"
})
@WebAppConfiguration
@UsingDataSet(value = { "/prepare.xls" })
public class RecipeControllerTest {

    @Inject
    private WebApplicationContext ctx;
    @Inject
    private EntityManager manager;
    private MockMvc mockMvc;
    
    @Inject @Rule 
    public DbUnitRule dbUnitRule;
  
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                        .webAppContextSetup(ctx)
                        .apply(springSecurity())
                        .build();
    }

    @After
    public void tearDown() throws Exception {
        manager.clear();
    }

    @Test
    public void shouldBeAllRecipes() throws Exception {
        MvcResult result = mockMvc.perform(get("/recipes").accept(APPLICATION_JSON).param("limit", "50"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON.toString() + ";charset=UTF-8"))
            .andExpect(jsonPath("$.*", hasSize(9)))
            .andExpect(jsonPath("$.totalElements", is(2)))
            .andReturn();
        final String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }
    
    @Test
    public void shouldBeOneRecipe() throws Exception {
        
        MvcResult result = mockMvc.perform(get("/recipes/c0e5582e-252f-4e94-8a49-e12b4b047afb")
                       .with(httpBasic("user", "password")).accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.uuid", is("c0e5582e-252f-4e94-8a49-e12b4b047afb")))
            .andExpect(jsonPath("$.title", is("Arabische Spaghetti")))
            .andReturn();
        final String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }
    
    @Test
    public void shouldBeSaveNewRecipe() throws Exception {
        final Recipe recipeToSave = createRecipe();
        mockMvc.perform(post("/recipes")
                        .contentType(APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(recipeToSave)))
            .andExpect(status().isOk())
            .andReturn();
    }
    
    private byte[] convertObjectToJsonBytes(Recipe object) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return mapper.writeValueAsBytes(object);
    }
    
    private Recipe createRecipe() {
        final Recipe recipe = new Recipe(UUID.randomUUID().toString());
        recipe.setTitle("Neues Rezept vom Junit-Test");
        recipe.setPreamble("Da gibt es einiges zu sagen");
        recipe.setAddingDate(december(24, 2014));
        recipe.setLastUpdate(december(29, 2014));
        recipe.setNoOfPerson("2");
        recipe.setPreparation("Die Zubereitung ist einfach");
        recipe.setRating(4);
        recipe.addIngredient(new Ingredient("100g", "Mehl", "Bioqualität"));
        recipe.addIngredient(new Ingredient("2", "Tomaten", "Bioqualität"));
        return recipe;
    }

    private Date december(int day, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, Calendar.DECEMBER, day);
        return cal.getTime();
    }
    
}
