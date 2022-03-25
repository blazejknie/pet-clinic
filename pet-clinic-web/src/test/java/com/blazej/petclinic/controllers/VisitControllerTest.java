package com.blazej.petclinic.controllers;

import com.blazej.petclinic.model.Owner;
import com.blazej.petclinic.model.Pet;
import com.blazej.petclinic.model.PetType;
import com.blazej.petclinic.services.PetService;
import com.blazej.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private static final String REDIRECT_OWNERS_1 = "redirect:/owners/{ownerId}";
    private static final String YET_ANOTHER_VISIT_DESCRIPTION = "yet another description";

    @Mock
    VisitService visitService;

    @Mock
    PetService petService;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    private final UriTemplate visitsUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
    private final Map<String, String> uriVariables = new HashMap<>();
    private URI visitUri;

    @BeforeEach
    void setUp() {
        Long petId = 1L;
        Long ownerId = 1L;
        when(petService.findById(anyLong()))
                .thenReturn(
                        Pet.builder()
                                .id(petId)
                                .birthDate(LocalDate.of(2018, 11, 11))
                                .name("Rex")
                                .visits(new HashSet<>())
                                .owner(Owner.builder()
                                        .id(ownerId)
                                        .firstName("Jon")
                                        .lastName("Doe")
                                        .build()
                                )
                                .petType(PetType.builder()
                                        .name("Dog").build()
                                )
                                .build()
                );
        uriVariables.clear();
        uriVariables.put("ownerId", ownerId.toString());
        uriVariables.put("petId", petId.toString());
        visitUri = visitsUriTemplate.expand(uriVariables);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        mockMvc.perform(get(visitUri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("visit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));

        verify(petService).findById(anyLong());
        verifyNoInteractions(visitService);
    }

    @Test
    void processNewVisitForm() throws Exception {
        mockMvc.perform(post(visitUri)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("date", "2018-11-11")
                        .param("description", YET_ANOTHER_VISIT_DESCRIPTION))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().attributeExists("visit"))
                .andExpect(view().name(REDIRECT_OWNERS_1));

        verify(visitService).save(any());
    }
}