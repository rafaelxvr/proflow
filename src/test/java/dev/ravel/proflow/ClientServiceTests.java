package dev.ravel.proflow;

import dev.ravel.proflow.domain.services.ClientServiceImpl;
import dev.ravel.proflow.infrastructure.model.Client;
import dev.ravel.proflow.infrastructure.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTests {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testAddClient() {
        Client newClient = new Client();
        newClient.setName("John Doe");
        newClient.setDocumentId("123456789"); // Added documentId
        newClient.setEmail("john@example.com");

        when(clientRepository.save(any(Client.class))).thenReturn(newClient);

        Client addedClient = clientService.addClient(newClient);

        assertNotNull(addedClient);
        assertEquals("John Doe", addedClient.getName());
        assertEquals("123456789", addedClient.getDocumentId()); // Check documentId
        assertEquals("john@example.com", addedClient.getEmail());

        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    public void testGetClientById() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setDocumentId("987654321"); // Added documentId
        client.setEmail("john@example.com");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client foundClient = clientService.getClientById(1L);

        assertNotNull(foundClient);
        assertEquals("John Doe", foundClient.getName());
        assertEquals("987654321", foundClient.getDocumentId()); // Check documentId
        assertEquals("john@example.com", foundClient.getEmail());

        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetClients() {
        List<Client> mockClientList = new ArrayList<>();
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("John Doe");
        client1.setEmail("john@example.com");

        Client client2 = new Client();
        client1.setId(2L);
        client1.setName("Jane Smith");
        client1.setEmail("jane@example.com");

        mockClientList.add(client1);
        mockClientList.add(client2);

        when(clientRepository.findAll()).thenReturn(mockClientList);

        List<Client> clients = clientService.getClients();

        assertNotNull(clients);
        assertEquals(2, clients.size());

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteClient() {
        long clientIdToDelete = 1L;

        clientService.deleteClient(clientIdToDelete);

        verify(clientRepository, times(1)).deleteById(clientIdToDelete);
    }

    @Test
    public void testUpdateClient() {
        Client clientToUpdate = new Client();
        clientToUpdate.setId(1L);
        clientToUpdate.setName("Updated Name");
        clientToUpdate.setEmail("updated@example.com");

        when(clientRepository.save(any(Client.class))).thenReturn(clientToUpdate);

        Client updatedClient = clientService.updateClient(clientToUpdate);

        assertNotNull(updatedClient);
        assertEquals("Updated Name", updatedClient.getName());
        assertEquals("updated@example.com", updatedClient.getEmail());

        verify(clientRepository, times(1)).save(any(Client.class));
    }
}