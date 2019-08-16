package pl.dawid.transportapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.dawid.transportapp.dto.CarDto;
import pl.dawid.transportapp.service.CarService;
import pl.dawid.transportapp.tool.ObjectTestGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
class CarControllerTest {

    @MockBean
    private CarService service;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void init() {
        CarDto carDto = ObjectTestGenerator.getCorrectCarDto(1);
        CarDto carDto2 = ObjectTestGenerator.getCorrectCarDto(2);
        List<CarDto> fakeCars = Arrays.asList(carDto,carDto2);

        when(service.findAll()).thenReturn(fakeCars);
        when(service.findById(anyLong())).thenReturn(Optional.of(fakeCars.get(0)));
        when(service.addCar(anyObject())).thenReturn(1L);
    }

    @Test
    void shouldReturnOKWhenAllCarRequested() throws Exception {
        mvc.perform(get("/car"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnShelfHrefWhenRequested() throws Exception {
        mvc.perform(get("/car"))
                .andExpect(jsonPath("_links.self.href", is("http://localhost/car")))
                .andExpect(jsonPath("_embedded.carDtoes[0]._links.self.href", is("http://localhost/car/1")));
    }

    @Test
    void shouldReturnCarWithIdOneWhenRequested() throws Exception {
        mvc.perform(get("/car/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("brand", is("brand1")))
                .andExpect(jsonPath("_links.self.href", is("http://localhost/car/1")));
    }

    @Test
    void shouldReturn404WhenCarNotExists() throws Exception {
        when(service.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get("/car/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenCarWasRemoved() throws Exception {
        mvc.perform(delete("/car/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn302WhenCarWasSaved() throws Exception {
        CarDto carDto = ObjectTestGenerator.getCorrectCarDto(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(carDto);
        mvc.perform(post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
}