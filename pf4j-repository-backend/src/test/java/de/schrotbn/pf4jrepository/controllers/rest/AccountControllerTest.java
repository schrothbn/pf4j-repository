package de.schrotbn.pf4jrepository.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.schrotbn.pf4jrepository.domain.model.User;
import de.schrotbn.pf4jrepository.domain.repositories.UserRepository;
import de.schrotbn.pf4jrepository.domain.services.RepositoryUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AccountController should")
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @MockBean
    UserRepository mockUserRepository;


    @MockBean
    RepositoryUserService userServicel;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @WithMockUser(roles = {"ADMIN"})
    @Test
    void whenUserIsAdmin_thenIndexReturns200() throws Exception{
        mockMvc.perform(get("/accounts").contentType("application/json"))
                .andExpect(status().isOk());
    }


    @WithMockUser(roles = {"ADMIN"})
    @Test
    void whenUserIsAdmin_thenFindOneReturns200() throws Exception {
        mockMvc.perform(get("/accounts/1234").contentType("application/json"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    void whenUserIsAdmin_thenCreateReturns200() throws Exception{
        var user = new User("User", "user");

        mockMvc.perform(post("/accounts").contentType("application/json")
                        .content(mapper.writeValueAsString(user)) )
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    void whenNullValue_thenCreateReturns400() throws Exception{
        mockMvc.perform(post("/accounts").contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    void whenUserIsAdmin_thenChangePasswordReturns200() throws Exception {
        when(mockUserRepository.findById(eq("123"))).thenReturn(Optional.of(new User("User", "user")));
        mockMvc.perform(put("/accounts/123/password").contentType("application/json").content("1234"))
                .andExpect(status().isOk());
        var userCaptor = ArgumentCaptor.forClass(User.class);
        verify(mockUserRepository, times(1)).save(userCaptor.capture());
        assertThat(passwordEncoder.matches("1234", userCaptor.getValue().getPassword())).isTrue();
    }

    @Test
    @WithMockUser
    void whenUserIsNotAdmin_rejectIndex() throws Exception{
        mockMvc.perform(get("/accounts")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void whenUserIsNotAdmin_rejectFindOne() throws Exception{
        mockMvc.perform(get("/accounts/123")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void whenUserIsNotAdmin_rejectCreate() throws Exception{
        var user = new User("User", "user");
        mockMvc.perform(post("/accounts").contentType("application/json").content(mapper.writeValueAsString(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void whenUserIsNotAdmin_rejectUpdate() throws Exception{
        var user = new User("User", "user");
        mockMvc.perform(put("/accounts/123").contentType("application/json").content(mapper.writeValueAsString(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void whenUserIsNotAdmin_rejectPasswordChange() throws Exception {
        mockMvc.perform(put("/accounts/123/password").content("12234")).andExpect(status().isForbidden());
    }
}