package com.raidiam.api;

// By Otavio

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@SpringBootTest
@AutoConfigureMockMvc  //Allow to create Mocks for HTTP requests
public class ApiSecurityTest {

//    @BeforeEach
//    public void setup() {}

// Source - https://stackoverflow.com/questions/33779127/loggerfactory-getloggerclassname-class-vs-loggerfactory-getloggerthis-getclas
// Posted by Aurasphere, modified by community. See post 'Timeline' for change history
// Retrieved 2026-01-26, License - CC BY-SA 3.0

    private static final Logger logger =
            LoggerFactory.getLogger(ApiSecurityTest.class);



    @Autowired
    private MockMvc mockMvc;

    //private TestInfo testInfo;

    @BeforeEach
    void logTestName(TestInfo testInfo) {
        logger.info("Testing: {}", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("/now - With valid Token")
    public void testBDD_WhenCallingGetNowWithValidToken_ShouldReturnCode200() throws Exception {
        // Given + When + Then
        mockMvc.perform(
                        get("/api/now")
                                .with(opaqueToken())
                )
                .andExpect(status().isOk());

        System.out.println("Test 01 ");
    }

    @Test
    @DisplayName("/now - No Token || Invalid Token")
    public void testBDD_WhenCallingGetNowWithNoTokenOrInvalidToken_ShouldReturnError401() throws Exception {
        //Given + When + Then
        mockMvc.perform(get("/api/now"))  //Try to acess this endpoint
                .andExpect(status().isUnauthorized());

        System.out.println("Test 02");

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

        System.out.println("Test 03");
    }


    @Test
    @DisplayName("/random - With valid Token + If corret format")
    public void testBDD_WhenCallingGetRandomWithValidToken_ShouldFindNumbersandCode200() throws Exception {

        mockMvc.perform(get("/api/random").with(opaqueToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.random").value(matchesPattern("^-?\\d+$")));


        System.out.println("Test 04");
    }


    @Test
    @DisplayName("/random - No Token || Invalid Token")
    public void testBDD_WhenCallingGetRandomWithNoTokenOrInvalidToken_ShouldReturnError401() throws Exception {
        //Given + When + Then
        mockMvc.perform(get("/api/random"))  //Try to acess this endpoint
                .andExpect(status().isUnauthorized());

        System.out.println("Test 05");
    }



}

