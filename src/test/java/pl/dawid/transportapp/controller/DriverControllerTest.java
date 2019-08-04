package pl.dawid.transportapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.repository.DriverRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DriverController.class)
class DriverControllerTest {

    @MockBean
    private DriverRepository driverRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void init() {
        List<Driver> fakeDrivers = Arrays.asList(new Driver(1L, "name1"), new Driver(2L, "name2"));
        when(driverRepository.findAll()).thenReturn(fakeDrivers);
        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(fakeDrivers.get(0)));
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
                .andExpect(jsonPath("_embedded.drivers[0]._links.self.href", is("http://localhost/driver/1")));
    }

    @Test
    void shouldReturnDriverWithIdOneWhenRequested() throws Exception {
        mvc.perform(get("/driver/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("name1")))
                .andExpect(jsonPath("_links.self.href", is("http://localhost/driver/1")));
    }
    @Test
    void shouldReturn404WhenDriverNotExists() throws Exception {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get("/driver/4"))
                .andExpect(status().isNotFound());
    }
}