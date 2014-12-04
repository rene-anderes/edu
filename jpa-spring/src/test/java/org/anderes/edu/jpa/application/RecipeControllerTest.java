package org.anderes.edu.jpa.application;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
@WebAppConfiguration
public class RecipeControllerTest {

    @Inject
    private WebApplicationContext ctx;
    
    @Inject
    private FilterChainProxy springSecurityFilterChain;
 
    private MockMvc mockMvc;
    private MockHttpSession session;
 
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).addFilters(springSecurityFilterChain).build();
        
        final List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        final Authentication authentication = new UsernamePasswordAuthenticationToken("normal_user","password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldBeAllRecipes() throws Exception {
        MvcResult result = mockMvc.perform(get("/recipes").accept(APPLICATION_JSON).param("limit", "50"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }
    
    @Test
    public void shouldBeOneRecipe() throws Exception {
        
        MvcResult result = mockMvc.perform(get("/recipes/FF00-AA").accept(APPLICATION_JSON).session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

}
