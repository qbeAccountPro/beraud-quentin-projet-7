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

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

import jakarta.transaction.Transactional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class TradeIntegrationTests {
  @Autowired
  TradeController tradeController;
  @Autowired
  TradeService tradeService;

  private MockMvc mvc;

  @Test
  @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS")
  @Transactional
  public void tradeIntegrationTests() throws Exception {

    this.mvc = MockMvcBuilders
        .standaloneSetup(tradeController)
        .setControllerAdvice()
        .build();

    Trade trade = new Trade("account", "type", 12.00);

    // Add trade :
    mvc.perform(MockMvcRequestBuilders.post("/trade/validate")
        .param("account", trade.getAccount())
        .param("type", trade.getType())
        .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andReturn();

    // Get saved trade :
    List<Trade> trades = tradeService.findAll();
    int lastTrade = trades.size() - 1;
    Trade tradeSaved = trades.get(lastTrade);

    // Check Values :
    assertEquals(trade.getAccount(), tradeSaved.getAccount());
    assertEquals(trade.getType(), tradeSaved.getType());
    assertEquals(trade.getBuyQuantity(), tradeSaved.getBuyQuantity());

    int tradeId = tradeSaved.getTradeId();

    // Update Trade :
    Trade tradeUpdated = new Trade("accountUpdated", "typeUpdated", 24.00);

    mvc.perform(MockMvcRequestBuilders.post("/trade/update/" + tradeId)
        .param("account", tradeUpdated.getAccount())
        .param("type", tradeUpdated.getType())
        .param("buyQuantity", String.valueOf(tradeUpdated.getBuyQuantity()))
        .param("tradeId", String.valueOf(tradeId))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andReturn();

    trades = tradeService.findAll();
    tradeSaved = trades.get(lastTrade);

    // Check Values :
    assertEquals(tradeUpdated.getAccount(), tradeSaved.getAccount());
    assertEquals(tradeUpdated.getType(), tradeSaved.getType());
    assertEquals(tradeUpdated.getBuyQuantity(), tradeSaved.getBuyQuantity());

    // Delete Trade :
    mvc.perform(MockMvcRequestBuilders.get("/trade/delete/" + tradeId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andReturn();

    Optional<Trade> optionalTrade = tradeService.findById(tradeId);
    Assert.assertFalse(optionalTrade.isPresent());
  }
}
