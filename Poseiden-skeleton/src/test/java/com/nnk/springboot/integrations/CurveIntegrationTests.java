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

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;

import jakarta.transaction.Transactional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class CurveIntegrationTests {
  
  @Autowired
  CurveController curveController;
  @Autowired
  CurveService curveService;
  private MockMvc mvc;

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
}
