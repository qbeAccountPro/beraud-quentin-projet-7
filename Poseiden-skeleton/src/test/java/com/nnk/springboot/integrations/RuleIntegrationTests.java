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

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class RuleIntegrationTests {

  @Autowired
  RuleNameController ruleNameController;
  @Autowired
  RuleNameService ruleNameService;

  private MockMvc mvc;

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
    RuleName ruleNametUpdated = new RuleName("NameUpdated", "descriptionUpdated", "jsonUpdated",
        "tamplateUpdated",
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
}
