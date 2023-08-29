package dev.ravel.proflow.Controllers;

import dev.ravel.proflow.domain.services.ClientService;
import dev.ravel.proflow.infrastructure.model.Client;
import dev.ravel.proflow.presentation.controllers.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientControllerTests {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddClient() {
        Client client = new Client();
        when(clientService.addClient(client)).thenReturn(client);

        ResponseEntity<Client> response = clientController.addClient(client);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void testGetClientById() {
        long clientId = 1L;
        Client client = new Client();
        when(clientService.getClientById(clientId)).thenReturn(client);

        ResponseEntity<Client> response = clientController.getClientById(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void testGetClients() {
        List<Client> clients = Collections.singletonList(new Client());
        when(clientService.getClients()).thenReturn(clients);

        ResponseEntity<List<Client>> response = clientController.getClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
    }

    @Test
    void testUpdateClient() {
        Client client = new Client();
        when(clientService.updateClient(client)).thenReturn(client);

        ResponseEntity<Client> response = clientController.updateClient(client);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void testDeleteClient() {
        long clientId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted a client.");
        doNothing().when(clientService).deleteClient(clientId);

        ResponseEntity<String> response = clientController.deleteClient(clientId);

        assertEquals(expectedResponse, response);
    }
}
