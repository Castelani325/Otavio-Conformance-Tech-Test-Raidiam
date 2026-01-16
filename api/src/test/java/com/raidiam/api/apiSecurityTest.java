package com.raidiam.api;

// By Otavio

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.matchesPattern;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc  //Allow to create Mocks for HTTP requests
public class apiSecurityTest {

//    @BeforeEach
//    public void setup() {}

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/now - With valid Token")
    public void testBDD_WhenCallingGetNowWithValidToken_ShouldReturnCode200() throws Exception {
        // Given + When + Then
        mockMvc.perform(
                        get("/api/now")
                                .with(opaqueToken())
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/now - No Token || Invalid Token")
    public void testBDD_WhenCallingGetNowWithNoTokenOrInvalidToken_ShouldReturnError401() throws Exception {
        //Given + When + Then
        mockMvc.perform(get("/api/now"))  //Try to acess this endpoint
                .andExpect(status().isUnauthorized());

    }

    @Test
    @DisplayName("/now - Should be time format")
    public void testBDD_WhenCallingGetNow_ShouldReturnATimeFormat() throws Exception {
        //Given + When + Then
        String response = mockMvc.perform(get("/api/now").with(opaqueToken())).
                andExpect(status().isOk()).andExpect(jsonPath("$.time").isString())
                .andReturn().getResponse().getContentAsString();

        String time = JsonPath.read(response, "$.time");
        Instant parsed = Instant.parse(time);

        assertThat(parsed).isNotNull();


    }


    @Test
    @DisplayName("/random - With valid Token + If corret format")
    public void testBDD_WhenCallingGetRandomWithValidToken_ShouldFindNumbersandCode200() throws Exception {

        mockMvc.perform(get("/api/random").with(opaqueToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.random").value(matchesPattern("^-?\\d+$")));

    }


    @Test
    @DisplayName("/random - No Token || Invalid Token")
    public void testBDD_WhenCallingGetRandomWithNoTokenOrInvalidToken_ShouldReturnError401() throws Exception {
        //Given + When + Then
        mockMvc.perform(get("/api/random"))  //Try to acess this endpoint
                .andExpect(status().isUnauthorized());


    }

}

