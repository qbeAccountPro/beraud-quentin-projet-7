package com.nnk.springboot.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class LoginTests {

  @Autowired
  BidListController bidListController;

  private MockMvc mvc;

  @BeforeEach
  void setup() {

  }

  @Test
  @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS", roles = "ADMIN")
  public void addBidList() throws Exception {

        this.mvc = MockMvcBuilders
        .standaloneSetup(bidListController)
        .setControllerAdvice()
        .build();
        
    BidList bidList = new BidList("Bob", "NewTrade", 12.12);

    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/bidList/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(bidList.toString()))
        .andExpect(status().is3xxRedirection())
        .andReturn();

    String actualContent = result.getResponse().getContentAsString();
    String expectedContent = "redirect:/bidList/list";

    assertEquals(expectedContent, actualContent);
  }
}