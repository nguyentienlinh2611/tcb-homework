package com.example.tcbhomework;


import com.example.tcbhomework.controller.rest.PoolController;
import com.example.tcbhomework.exception.PoolNotFoundException;
import com.example.tcbhomework.service.PoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PoolController.class)
@AutoConfigureMockMvc
public class PoolControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PoolService service;

    @Test
    public void whenQueryNonexistentPoolId_thenReturnNotFoundStatus() throws Exception {
        given(service.queryPool(Mockito.anyInt(), Mockito.anyDouble())).willThrow(PoolNotFoundException.class);

        mvc.perform(post("/api/pool/query").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"poolId\": 123546,\n" +
                        "    \"percentile\": 50\n" +
                        "}")).andExpect(status().is(404));
    }

    @Test
    public void whenPostRequestToAddPoolAndInvalidRequest_thenReturnBadRequestResponse() throws Exception {
        mvc.perform(post("/api/pool/add").contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().is(400));
    }

    @Test
    public void whenPostRequestToQueryPoolAndInvalidRequest_thenReturnBadRequestResponse() throws Exception {
        mvc.perform(post("/api/pool/query").contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().is(400));
    }


}
