package dev.ravel.proflow.presentation.controllers;

import dev.ravel.proflow.domain.services.ClientService;
import dev.ravel.proflow.infrastructure.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping("/health")
    public String Health() {
        return "Everything is working";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addClient(@RequestBody Client client) {
        try {
            var result = clientService.addClient(client);

            if (result != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Client added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add client");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred.");
        }
    }

    @RequestMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") long id) {
        var result = clientService.getClientById(id);

        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping("/clients")
    public ResponseEntity<List<Client>> getClients() {
        var result = clientService.getClients();

        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/clients")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        try {
            var result = clientService.updateClient(client);

            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted a client.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred.");
        }
    }
}
