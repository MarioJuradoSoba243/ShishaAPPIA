package com.shisha.appia.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shisha.appia.api.dto.BlendRequest;
import com.shisha.appia.api.dto.TobaccoRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests verifying the API for tobaccos and blends.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create and list tobaccos")
    void shouldCreateAndListTobaccos() throws Exception {
        TobaccoRequest request = new TobaccoRequest();
        request.setName("Blue Mist");
        request.setBrand("Starbuzz");

        mockMvc.perform(post("/api/tobaccos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(get("/api/tobaccos"))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).contains("Blue Mist", "Starbuzz");
    }

    @Test
    @DisplayName("Should create blends referencing tobaccos")
    void shouldCreateBlends() throws Exception {
        Long alfaId = createTobacco("Love 66", "Adalya");
        Long betaId = createTobacco("Lady Killer", "Al Fakher");

        BlendRequest blendRequest = new BlendRequest();
        blendRequest.setName("Summer Breeze");
        blendRequest.setDescription("Mezcla fresca para verano");
        blendRequest.setTobaccoIds(List.of(alfaId, betaId));

        mockMvc.perform(post("/api/blends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blendRequest)))
                .andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(get("/api/blends"))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent)
                .contains("Summer Breeze", "Mezcla fresca para verano", "Love 66", "Lady Killer");
    }

    @Test
    @DisplayName("Should reject blends when a tobacco id does not exist")
    void shouldRejectBlendWhenTobaccoMissing() throws Exception {
        BlendRequest blendRequest = new BlendRequest();
        blendRequest.setName("Invalid blend");
        blendRequest.setTobaccoIds(List.of(999L));

        mockMvc.perform(post("/api/blends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blendRequest)))
                .andExpect(status().isNotFound());
    }

    private Long createTobacco(String name, String brand) throws Exception {
        TobaccoRequest request = new TobaccoRequest();
        request.setName(name);
        request.setBrand(brand);

        MvcResult result = mockMvc.perform(post("/api/tobaccos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        return objectMapper.readTree(responseContent).get("id").asLong();
    }
}
