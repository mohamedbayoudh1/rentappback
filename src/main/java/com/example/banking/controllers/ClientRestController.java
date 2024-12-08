package com.example.banking.controllers;

import com.example.banking.entities.ClientData;
import com.example.banking.entities.LocalClient;
import com.example.banking.services.Impl.ClientServiceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("client")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ClientRestController {


    private final ClientServiceImpl clientService;

    @GetMapping("/clients")
    public String getClients() {
        return clientService.getClients();
    }

    @PostMapping("/addClient")
    public String addClient(@RequestBody ClientData clientData) {
        return clientService.addClient(clientData);
    }

    @GetMapping("retreiveClientByFineractId/{fineractClientId}")
    public LocalClient retreiveClientByFineractIdFromLocalDateBase(@PathVariable Long fineractClientId) {
        return clientService.retreiveClientByFineractIdFromLocalDateBase(fineractClientId);
    }


    @GetMapping("retreiveClientByEmail/{emailAdress}")
    public LocalClient retreiveClientByEmail(@PathVariable String emailAdress) {
        return clientService.retreiveClientByEmail(emailAdress);
    }




  /*  @GetMapping("/{clientId}")
    public ResponseEntity<ClientDetailsResponse> getClientById(@PathVariable("clientId") String clientId) {
        return clientService.getClientById(clientId);
    }*/


}

