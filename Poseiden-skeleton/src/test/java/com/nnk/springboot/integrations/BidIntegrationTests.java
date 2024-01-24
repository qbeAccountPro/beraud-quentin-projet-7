package com.nnk.springboot.integrations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class BidIntegrationTests {

  @Autowired
  BidListController bidListController;
  @Autowired
  BidListService bidListService;

  private MockMvc mvc;

  @Test
  @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS")
  @Transactional
  public void bidIntegrationTests() throws Exception {

    this.mvc = MockMvcBuilders
        .standaloneSetup(bidListController)
        .setControllerAdvice()
        .build();

    BidList bid = new BidList("Bob", "NewTrade", 12.12);

    // Add bid :
    mvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .param("account", bid.getAccount())
        .param("type", bid.getType())
        .param("bidQuantity", bid.getBidQuantity().toString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andReturn();

    // Get saved Bids :
    List<BidList> bidList = bidListService.findAll();
    int lastBid = bidList.size() - 1;
    BidList bidSaved = bidList.get(lastBid);

    // Check Values :
    assertEquals(bid.getAccount(), bidSaved.getAccount());
    assertEquals(bid.getType(), bidSaved.getType());
    assertEquals(bid.getBidQuantity(), bidSaved.getBidQuantity());

    int bidId = bidSaved.getBidListId();

    // Update bid :
    BidList bidToBeUpdated = new BidList("Jack", "Euro", 13.00);

    mvc.perform(MockMvcRequestBuilders.post("/bidList/update/" + bidId)
        .param("account", bidToBeUpdated.getAccount())
        .param("type", bidToBeUpdated.getType())
        .param("bidQuantity", bidToBeUpdated.getBidQuantity().toString())
        .param("bidListId", String.valueOf(bidId))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andReturn();

    bidList = bidListService.findAll();
    bidSaved = bidList.get(lastBid);

    // Check Values :
    assertEquals(bidToBeUpdated.getAccount(), bidSaved.getAccount());
    assertEquals(bidToBeUpdated.getType(), bidSaved.getType());
    assertEquals(bidToBeUpdated.getBidQuantity(), bidSaved.getBidQuantity());

    // Delete bid :
    mvc.perform(MockMvcRequestBuilders.get("/bidList/delete/" + bidId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andReturn();

    Optional<BidList> optionalBidList = bidListService.findById(bidId);
    Assert.assertFalse(optionalBidList.isPresent());
  }
}
