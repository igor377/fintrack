package com.fintrack.demo.user.controller;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fintrack.demo.config.SecurityConfig;
import com.fintrack.demo.user.entity.User;
import com.fintrack.demo.user.repository.UserRepository;
import com.fintrack.demo.user.service.UserService;

/** Cadastro com a segurança e a validação ativas; só o repositório é simulado. */
@WebMvcTest(UserController.class)
@Import({ SecurityConfig.class, UserService.class })
class UserControllerTest {

    private static final String NAME = "Maria Silva";
    private static final String CPF = "529.982.247-25";
    private static final String EMAIL = "Maria@Example.com";
    private static final String PASSWORD = "Senha@123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void anonymousUserCanRegister() throws Exception {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        register(NAME, CPF, EMAIL, PASSWORD)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value("maria@example.com"))
                // aceita o CPF completo ou mascarado
                .andExpect(jsonPath("$.cpf", anyOf(is("52998224725"), is("***.982.247-**"))))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void savesNormalizedDataAndHashedPassword() throws Exception {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        register(NAME, CPF, EMAIL, PASSWORD).andExpect(status().isCreated());

        ArgumentCaptor<User> saved = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(saved.capture());
        org.junit.jupiter.api.Assertions.assertEquals("52998224725", saved.getValue().getCpf());
        org.junit.jupiter.api.Assertions.assertEquals("maria@example.com", saved.getValue().getEmail());
        org.junit.jupiter.api.Assertions.assertTrue(
                passwordEncoder.matches(PASSWORD, saved.getValue().getPassword()));
    }

    @Test
    void duplicatedCpfReturnsConflict() throws Exception {
        when(userRepository.existsByCpf("52998224725")).thenReturn(true);

        register(NAME, CPF, EMAIL, PASSWORD).andExpect(status().isConflict());

        verify(userRepository, never()).save(any());
    }

    @Test
    void duplicatedEmailReturnsConflict() throws Exception {
        when(userRepository.existsByEmail("maria@example.com")).thenReturn(true);

        register(NAME, CPF, EMAIL, PASSWORD).andExpect(status().isConflict());

        verify(userRepository, never()).save(any());
    }

    @Test
    void nameLongerThanColumnReturnsBadRequest() throws Exception {
        register("a".repeat(101), CPF, EMAIL, PASSWORD)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    void invalidCpfReturnsBadRequest() throws Exception {
        register(NAME, "111.111.111-11", EMAIL, PASSWORD)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.cpf").exists());
    }

    @Test
    void weakPasswordReturnsBadRequest() throws Exception {
        register(NAME, CPF, EMAIL, "senhafraca")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.password").exists());
    }

    @Test
    void passwordOver72BytesReturnsBadRequest() throws Exception {
        // 44 caracteres, mas 84 bytes em UTF-8
        register(NAME, CPF, EMAIL, "Aa1!" + "é".repeat(40))
                .andExpect(status().isBadRequest());
    }

    private ResultActions register(String name, String cpf, String email, String password) throws Exception {
        String body = """
                {"name": "%s", "cpf": "%s", "email": "%s", "password": "%s"}
                """.formatted(name, cpf, email, password);

        return mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(body));
    }
}
