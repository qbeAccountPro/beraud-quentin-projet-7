package com.nnk.springboot.integrations;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.CurveService;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.TradeService;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class EndpointTests {

    @Autowired
    BidListController bidListController;
    @Autowired
    BidListService bidListService;

    @Autowired
    CurveController curveController;
    @Autowired
    CurveService curveService;

    @Autowired
    RatingController ratingController;
    @Autowired
    RatingService ratingService;

    @Autowired
    RuleNameController ruleNameController;
    @Autowired
    RuleNameService ruleNameService;

    @Autowired
    TradeController tradeController;
    @Autowired
    TradeService tradeService;

    @Autowired
    UserController userController;
    @Autowired
    UserRepository userRepository;

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

    @Test
    @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS")
    @Transactional
    public void curvepointIntegrationTests() throws Exception {

        this.mvc = MockMvcBuilders
                .standaloneSetup(curveController)
                .setControllerAdvice()
                .build();

        CurvePoint curvePoint = new CurvePoint(143, 12.00, 51.0);

        // Add curvePoint :
        mvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                .param("curveId", String.valueOf(curvePoint.getCurveId()))
                .param("term", String.valueOf(curvePoint.getTerm()))
                .param("value", String.valueOf(curvePoint.getValue()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Get saved CurvePoint :
        List<CurvePoint> curvePoints = curveService.findAll();
        int lastCurvePoint = curvePoints.size() - 1;
        CurvePoint curvePointSaved = curvePoints.get(lastCurvePoint);

        // Check Values :
        assertEquals(curvePoint.getCurveId(), curvePointSaved.getCurveId());
        assertEquals(curvePoint.getTerm(), curvePointSaved.getTerm());
        assertEquals(curvePoint.getValue(), curvePointSaved.getValue());

        int curveId = curvePointSaved.getId();

        // Update Curve :
        CurvePoint curvePointUpdated = new CurvePoint(32, 25.00, 32.0);

        mvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/" + curveId)
                .param("curveId", String.valueOf(curvePointUpdated.getCurveId()))
                .param("term", String.valueOf(curvePointUpdated.getTerm()))
                .param("value", String.valueOf(curvePointUpdated.getValue()))
                .param("id", String.valueOf(curveId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        curvePoints = curveService.findAll();
        curvePointSaved = curvePoints.get(lastCurvePoint);

        // Check Values :
        assertEquals(curvePointUpdated.getCurveId(), curvePointSaved.getCurveId());
        assertEquals(curvePointUpdated.getTerm(), curvePointSaved.getTerm());
        assertEquals(curvePointUpdated.getValue(), curvePointSaved.getValue());

        // Delete Curve :
        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/" + curveId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Optional<CurvePoint> optionalCurvePoint = curveService.findById(curveId);
        Assert.assertFalse(optionalCurvePoint.isPresent());
    }

    @Test
    @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS")
    @Transactional
    public void ratingIntegrationTests() throws Exception {

        this.mvc = MockMvcBuilders
                .standaloneSetup(ratingController)
                .setControllerAdvice()
                .build();

        Rating rating = new Rating("moodysRating", "sandRating", "fitchRating", 13);

        // Add Rating :
        mvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                .param("moodysRating", rating.getMoodysRating())
                .param("sandPRating", rating.getSandPRating())
                .param("fitchRating", rating.getFitchRating())
                .param("orderNumber", String.valueOf(rating.getOrderNumber()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Get saved Rating :
        List<Rating> ratings = ratingService.findAll();
        int lastRating = ratings.size() - 1;
        Rating ratingSaved = ratings.get(lastRating);

        // Check Values :
        assertEquals(rating.getMoodysRating(), ratingSaved.getMoodysRating());
        assertEquals(rating.getSandPRating(), ratingSaved.getSandPRating());
        assertEquals(rating.getFitchRating(), ratingSaved.getFitchRating());
        assertEquals(rating.getOrderNumber(), ratingSaved.getOrderNumber());

        int ratingId = ratingSaved.getId();

        // Update Rating :
        Rating ratingtUpdated = new Rating("moodysRatingUpdated", "sandRatingUpdated", "fitchRatingUpdated", 51);

        mvc.perform(MockMvcRequestBuilders.post("/rating/update/" + ratingId)
                .param("moodysRating", ratingtUpdated.getMoodysRating())
                .param("sandPRating", ratingtUpdated.getSandPRating())
                .param("fitchRating", ratingtUpdated.getFitchRating())
                .param("orderNumber", String.valueOf(ratingtUpdated.getOrderNumber()))
                .param("id", String.valueOf(ratingId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ratings = ratingService.findAll();
        ratingSaved = ratings.get(lastRating);

        // Check Values :
        assertEquals(ratingtUpdated.getMoodysRating(), ratingSaved.getMoodysRating());
        assertEquals(ratingtUpdated.getSandPRating(), ratingSaved.getSandPRating());
        assertEquals(ratingtUpdated.getFitchRating(), ratingSaved.getFitchRating());
        assertEquals(ratingtUpdated.getOrderNumber(), ratingSaved.getOrderNumber());

        // Delete Rating :
        mvc.perform(MockMvcRequestBuilders.get("/rating/delete/" + ratingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Optional<Rating> optionalRating = ratingService.findById(ratingId);
        Assert.assertFalse(optionalRating.isPresent());
    }

    @Test
    @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS")
    @Transactional
    public void rulenameIntegrationTests() throws Exception {

        this.mvc = MockMvcBuilders
                .standaloneSetup(ruleNameController)
                .setControllerAdvice()
                .build();

        RuleName ruleName = new RuleName("Name", "description", "json", "tamplate", "sqlStr", "sqlPart");

        // Add RuleName :
        mvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                .param("name", ruleName.getName())
                .param("description", ruleName.getDescription())
                .param("json", ruleName.getJson())
                .param("template", ruleName.getTemplate())
                .param("sqlStr", ruleName.getSqlStr())
                .param("sqlPart", ruleName.getSqlPart())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Get saved RuleName :
        List<RuleName> ruleNames = ruleNameService.findAll();
        int lastRuleName = ruleNames.size() - 1;
        RuleName ruleNameSaved = ruleNames.get(lastRuleName);

        // Check Values :
        assertEquals(ruleName.getName(), ruleNameSaved.getName());
        assertEquals(ruleName.getDescription(), ruleNameSaved.getDescription());
        assertEquals(ruleName.getJson(), ruleNameSaved.getJson());
        assertEquals(ruleName.getTemplate(), ruleNameSaved.getTemplate());
        assertEquals(ruleName.getSqlStr(), ruleNameSaved.getSqlStr());
        assertEquals(ruleName.getSqlPart(), ruleNameSaved.getSqlPart());

        int ruleNameId = ruleNameSaved.getId();

        // Update RuleName :
        RuleName ruleNametUpdated = new RuleName("NameUpdated", "descriptionUpdated", "jsonUpdated", "tamplateUpdated",
                "sqlStrUpdated", "sqlPartUpdated");

        mvc.perform(MockMvcRequestBuilders.post("/ruleName/update/" + ruleNameId)
                .param("name", ruleNametUpdated.getName())
                .param("description", ruleNametUpdated.getDescription())
                .param("json", ruleNametUpdated.getJson())
                .param("template", ruleNametUpdated.getTemplate())
                .param("sqlStr", ruleNametUpdated.getSqlStr())
                .param("sqlPart", ruleNametUpdated.getSqlPart())
                .param("id", String.valueOf(ruleNameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ruleNames = ruleNameService.findAll();
        ruleNameSaved = ruleNames.get(lastRuleName);

        // Check Values :
        assertEquals(ruleNametUpdated.getName(), ruleNameSaved.getName());
        assertEquals(ruleNametUpdated.getDescription(), ruleNameSaved.getDescription());
        assertEquals(ruleNametUpdated.getJson(), ruleNameSaved.getJson());
        assertEquals(ruleNametUpdated.getTemplate(), ruleNameSaved.getTemplate());
        assertEquals(ruleNametUpdated.getSqlStr(), ruleNameSaved.getSqlStr());
        assertEquals(ruleNametUpdated.getSqlPart(), ruleNameSaved.getSqlPart());

        // Delete RuleName :
        mvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/" + ruleNameId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Optional<RuleName> optionalRuleName = ruleNameService.findById(ruleNameId);
        Assert.assertFalse(optionalRuleName.isPresent());
    }

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

    @Test
    @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS")
    @Transactional
    public void userIntegrationTests() throws Exception {

        this.mvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice()
                .build();

        User user = new User();
        user.setUsername("Jackie");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("paulette*9chips"));
        user.setFullname("Paulettor");
        user.setRole("ADMIN");

        // Add User :
        mvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("fullname", user.getFullname())
                .param("role", user.getRole())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Get saved User :
        List<User> users = userRepository.findAll();
        int lastUser = users.size() - 1;
        User userSaved = users.get(lastUser);

        // Check Values :
        assertEquals(user.getUsername(), userSaved.getUsername());
        // TODO : assertEquals(user.getPassword(), userSaved.getPassword());
        assertEquals(user.getFullname(), userSaved.getFullname());
        assertEquals(user.getRole(), userSaved.getRole());

        int userId = userSaved.getId();

        // Update User :
        User userUpdated = new User(userSaved.getId(), "PaulUpdated", passwordEncoder.encode("pauletteUpdated*89"),
                "PaulettorUpdated", "USER");

        mvc.perform(MockMvcRequestBuilders.post("/user/update/" + userId)
                .param("username", userUpdated.getUsername())
                .param("password", userUpdated.getPassword())
                .param("fullname", userUpdated.getFullname())
                .param("role", userUpdated.getRole())
                .param("id", String.valueOf(userId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        users = userRepository.findAll();
        userSaved = users.get(lastUser);

        // Check Values :
        assertEquals(userUpdated.getUsername(), userSaved.getUsername());
        //assertEquals(userUpdated.getPassword(), userSaved.getPassword());
        assertEquals(userUpdated.getFullname(), userSaved.getFullname());
        assertEquals(userUpdated.getRole(), userSaved.getRole());

        // Delete Curve :
        mvc.perform(MockMvcRequestBuilders.get("/user/delete/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Optional<User> optionalUser = userRepository.findById(userId);
        Assert.assertFalse(optionalUser.isPresent());
    }
}