package com.example.banking.services.Impl;


import com.example.banking.DTO.UserRrgistrationRecord;
import com.example.banking.entities.ClientData;
import com.example.banking.entities.FineractClient;
import com.example.banking.entities.LocalClient;
import com.example.banking.exceptions.UserNotFoundException;
import com.example.banking.repository.LocalClientRepository;
import jakarta.ws.rs.core.Response;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl {

    @Value("${keycloak.realm}")
    private String realm;
    private final Keycloak keycloak;

    private static final Logger LOGGER = Logger.getLogger(ClientServiceImpl.class.getName());
    private final RestTemplate restTemplate;
    private final String url = "https://localhost:8443/fineract-provider/api/v1/clients";
    private final String username = "mifos";
    private final String password = "password";
    private final String auth = username + ":" + password;

    private final LocalClientRepository clientRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public String getClients() {

        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                LOGGER.log(Level.SEVERE, "Error response from Fineract API: " + response.getBody());
                return "Error response from Fineract API: " + response.getBody();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error connecting to Fineract API", e);
            return "Error connecting to Fineract API: " + e.getMessage();
        }
    }

    public String addClient(ClientData clientData) {

        // Check if email or mobile number already exists
        if (clientRepository.findByEmailAddress(clientData.getEmailAddress()).isPresent()) {
            return "Error: Email address already exists.";
        }

        if (clientRepository.findByMobileNo(clientData.getMobileNo()).isPresent()) {
            return "Error: Mobile number already exists.";
        }
        // Enregistrer le client dans la base de données locale

        LocalClient localClient = new LocalClient();
        localClient.setActive(clientData.isActive());
        localClient.setFirstname(clientData.getFirstname());
        localClient.setLastname(clientData.getLastname());
        localClient.setExternalId(clientData.getExternalId());
        localClient.setEmailAddress(clientData.getEmailAddress());
        localClient.setMobileNo(clientData.getMobileNo());
        localClient.setActivationDate(clientData.getActivationDate());
        localClient.setSubmittedOnDate(clientData.getSubmittedOnDate());
        String hashedPassword = passwordEncoder.encode(clientData.getPassword());
        localClient.setPassword(hashedPassword);
        //localClient.setConfirmPassword(clientData.getConfirmPassword());
        localClient.setScore(clientData.getScore());
        clientRepository.save(localClient);

        //Create Client in keycloak
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(clientData.getFirstname()); // Utiliser l'ID externe comme nom d'utilisateur
        user.setEmail(clientData.getEmailAddress());
        user.setFirstName(clientData.getFirstname());
        user.setLastName(clientData.getLastname());
        user.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(clientData.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> credentialsList = new ArrayList<>();
        credentialsList.add(credentialRepresentation);
        user.setCredentials(credentialsList);

        UsersResource usersResource = getUsersResource();
        Response response1 = usersResource.create(user);

        if (response1.getStatus() != 201) {
            // Gérer l'erreur de création de l'utilisateur dans Keycloak
            return "Error creating user in Keycloak: " + response1.getStatusInfo();
        }
        // Enregistrer vers fineract
        FineractClient clientDataForFineract = FineractClient.builder()
                .officeId(clientData.getOfficeId())
                .dateFormat(clientData.getDateFormat())
                .locale(clientData.getLocale())
                .active(clientData.isActive())
                .legalFormId(clientData.getLegalFormId())
                .firstname(clientData.getFirstname())
                .lastname(clientData.getLastname())
                .externalId(clientData.getExternalId())
                .emailAddress(clientData.getEmailAddress())
                .mobileNo(clientData.getMobileNo())
                .dateOfBirth(clientData.getDateOfBirth())
                .activationDate(clientData.getActivationDate())
                .submittedOnDate(clientData.getSubmittedOnDate())
                .datatables(clientData.getDatatables())
                .build();
        HttpHeaders headers = createHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<FineractClient> entity = new HttpEntity<>(clientDataForFineract, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JSONObject responseBody = new JSONObject(response.getBody());
                Long fineractClientId = responseBody.getLong("clientId");

                // Mettre à jour le client local avec l'ID de Fineract
                localClient.setFineractClientId(fineractClientId);
                clientRepository.save(localClient);

                return response.getBody();
            } else {
                LOGGER.log(Level.SEVERE, "Error response from Fineract API: " + response.getBody());
                return "Error response from Fineract API: " + response.getBody();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error connecting to Fineract API", e);
            return "Error connecting to Fineract API: " + e.getMessage();
        }
    }



    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    private HttpHeaders createHeaders() {
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String authHeader = "Basic " + encodedAuth;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.set("Accept", "application/json");
        headers.set("Fineract-Platform-TenantId", "default");
        return headers;
    }

    private UserRrgistrationRecord createUserInKeycloak(UserRrgistrationRecord userRrgistrationRecord) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRrgistrationRecord.firstname());
        user.setEmail(userRrgistrationRecord.email());
        user.setFirstName(userRrgistrationRecord.firstname());
        user.setLastName(userRrgistrationRecord.lastname());
        user.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userRrgistrationRecord.password());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(user);

        if (Objects.equals(201, response.getStatus())) {
            return userRrgistrationRecord;
        }

        // response.readEntity()
        return null;
    }



    public UserRepresentation getUserById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }


    public LocalClient retreiveClientByFineractIdFromLocalDateBase(Long fineractClientId){
        return clientRepository.findByFineractClientId(fineractClientId)
                .orElseThrow(() -> new UserNotFoundException("Client with Fineract ID " + fineractClientId + " not found"));
    }
    public LocalClient retreiveClientByEmail(String emailAdress){
        return clientRepository.findByEmailAddress(emailAdress)
                .orElseThrow(() -> new UserNotFoundException("Client with Fineract ID " + emailAdress + " not found"));
    }


}
