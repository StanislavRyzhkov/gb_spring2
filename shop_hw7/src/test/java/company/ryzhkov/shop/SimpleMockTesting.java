package company.ryzhkov.shop;

import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SimpleMockTesting {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void tryToStart() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("Каталог товаров")));
    }

    @Test
    public void page404Test() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/abc"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void correctLogin() throws Exception {
        mockMvc
                .perform(SecurityMockMvcRequestBuilders
                        .formLogin("/authenticate")
                        .user("admin")
                        .password("123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

    }

    @Test
    public void wrongLoginOrPassword() throws Exception {
        mockMvc
                .perform(SecurityMockMvcRequestBuilders
                        .formLogin("/authenticate")
                        .user("this")
                        .password("this"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void checkCartSize() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/cartSize")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(0));

    }

    @Test
    public void checkAddToCart() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/addToCart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemPrice\":1,\"quantity\":2,\"productId\":3}".getBytes())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));

    }
 }
