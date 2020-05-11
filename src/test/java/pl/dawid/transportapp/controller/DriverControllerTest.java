//package pl.dawid.transportapp.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import pl.dawid.transportapp.dto.DriverDto;
//import pl.dawid.transportapp.security.config.WebSecurity;
//import pl.dawid.transportapp.security.repository.UserRepository;
//import pl.dawid.transportapp.security.service.UserDetailsServiceImpl;
//import pl.dawid.transportapp.service.DriverService;
//import pl.dawid.transportapp.tool.ObjectTestGenerator;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.anyObject;
//import static org.mockito.Mockito.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = DriverController.class, excludeAutoConfiguration = {WebSecurity.class,UserDetailsServiceImpl.class})
////@Disabled
//class DriverControllerTest {
//
//    @MockBean
//    private DriverService service;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @BeforeEach
//    void init() {
//        DriverDto driver = ObjectTestGenerator.getCorrectDriverDto(1);
//        driver.setId(1L);
//        DriverDto driver2 = ObjectTestGenerator.getCorrectDriverDto(2);
//        driver2.setId(2L);
//        List<DriverDto> fakeDrivers = Arrays.asList(driver, driver2);
//        when(service.findAll()).thenReturn(fakeDrivers);
//        when(service.findDtoById(anyLong())).thenReturn(Optional.of(fakeDrivers.get(0)));
//        when(service.addDriver(anyObject())).thenReturn(1L);
//    }
//
//    @Test
//    void shouldReturnOKWhenAllDriverRequestedRequested() throws Exception {
//        mvc.perform(get("/resource/driver"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void shouldReturnShelfHrefWhenRequested() throws Exception {
//        mvc.perform(get("/resource/driver"))
//                .andExpect(jsonPath("_links.self.href", is("http://localhost/resource/driver")))
//                .andExpect(jsonPath("_embedded.driverDtoes[0]._links.self.href", is("http://localhost/resource/driver/1")));
//    }
//
//    @Test
//    void shouldReturnDriverWithIdOneWhenRequested() throws Exception {
//        mvc.perform(get("/resource/driver/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("firstName", is("firstName1")))
//                .andExpect(jsonPath("_links.self.href", is("http://localhost/resource/driver/1")));
//    }
//
//    @Test
//    void shouldReturn404WhenDriverNotExists() throws Exception {
//        when(service.findDtoById(anyLong())).thenReturn(Optional.empty());
//        mvc.perform(get("/resource/driver/4"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void shouldReturn201WhenDriverWasSaved() throws Exception {
//        DriverDto driverDto = ObjectTestGenerator.getCorrectDriverDto(1);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(driverDto);
//        mvc.perform(post("/driver")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"));
//    }
//}