package pl.dawid.transportapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.service.DriverService;
import pl.dawid.transportapp.tool.ObjectTestGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DriverController.class)
class DriverControllerTest {

    @MockBean
    private DriverService service;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void init() {
        List<DriverDto> fakeDrivers = Arrays.asList(new DriverDto(1L, "pesel", "name1", "lastName1"),
                new DriverDto(2L, "pesel", "name2", "lastName2"));
        when(service.findAll()).thenReturn(fakeDrivers);
        when(service.findById(anyLong())).thenReturn(Optional.of(fakeDrivers.get(0)));
        when(service.addDriver(anyObject())).thenReturn(1L);
    }

    @Test
    void shouldReturnOKWhenAllDriverRequestedRequested() throws Exception {
        mvc.perform(get("/driver"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnShelfHrefWhenRequested() throws Exception {
        mvc.perform(get("/driver"))
                .andExpect(jsonPath("_links.self.href", is("http://localhost/driver")))
                .andExpect(jsonPath("_embedded.driverDtoes[0]._links.self.href", is("http://localhost/driver/1")));
    }

    @Test
    void shouldReturnDriverWithIdOneWhenRequested() throws Exception {
        mvc.perform(get("/driver/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("name1")))
                .andExpect(jsonPath("_links.self.href", is("http://localhost/driver/1")));
    }

    @Test
    void shouldReturn404WhenDriverNotExists() throws Exception {
        when(service.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get("/driver/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenDriverWasRemoved() throws Exception {
        mvc.perform(delete("/driver/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn302WhenDriverWasSaved() throws Exception {
        DriverDto driverDto = ObjectTestGenerator.getCorrectDriverDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(driverDto);
        mvc.perform(post("/driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
}