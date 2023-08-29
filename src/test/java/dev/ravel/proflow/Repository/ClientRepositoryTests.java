package dev.ravel.proflow.Repository;

import dev.ravel.proflow.infrastructure.model.Client;
import dev.ravel.proflow.infrastructure.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase
class ClientRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testSaveClient() {
        Client client = new Client();
        client.setName("John Doe");
        client.setDocumentId("123456789");
        client.setEmail("john@example.com");

        Client savedClient = clientRepository.save(client);

        assertEquals(client.getName(), savedClient.getName());
        assertEquals(client.getDocumentId(), savedClient.getDocumentId());
        assertEquals(client.getEmail(), savedClient.getEmail());
    }

    @Test
    void testFindClientById() {
        Client client = new Client();
        client.setName("John Doe");
        client.setDocumentId("123456789");
        client.setEmail("john@example.com");
        entityManager.persist(client);
        entityManager.flush();

        Optional<Client> foundClient = clientRepository.findById(client.getId());

        assertTrue(foundClient.isPresent());
        assertEquals(client.getName(), foundClient.get().getName());
        assertEquals(client.getDocumentId(), foundClient.get().getDocumentId());
        assertEquals(client.getEmail(), foundClient.get().getEmail());
    }

    @Test
    void testFindAllClients() {
        Client client1 = new Client();
        client1.setName("John Doe");
        client1.setDocumentId("123456789");
        client1.setEmail("john@example.com");
        entityManager.persist(client1);
        entityManager.flush();

        Client client2 = new Client();
        client2.setName("Jane Doe");
        client2.setDocumentId("987654321");
        client2.setEmail("jane@example.com");
        entityManager.persist(client2);
        entityManager.flush();

        Iterable<Client> foundClients = clientRepository.findAll();

        int count = 0;
        for (Client client : foundClients) {
            if (client.getId() == client1.getId()) {
                assertEquals(client1.getName(), client.getName());
                assertEquals(client1.getDocumentId(), client.getDocumentId());
                assertEquals(client1.getEmail(), client.getEmail());
                count++;
            } else if (client.getId() == client2.getId()) {
                assertEquals(client2.getName(), client.getName());
                assertEquals(client2.getDocumentId(), client.getDocumentId());
                assertEquals(client2.getEmail(), client.getEmail());
                count++;
            }
        }
        assertEquals(2, count);
    }

    @Test
    void testDeleteClient() {
        Client client = new Client();
        client.setName("John Doe");
        client.setDocumentId("123456789");
        client.setEmail("john@example.com");
        entityManager.persist(client);
        entityManager.flush();

        clientRepository.deleteById(client.getId());

        assertTrue(clientRepository.findById(client.getId()).isEmpty());
    }
}
