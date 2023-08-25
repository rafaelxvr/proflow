package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    public Client addClient(Client client);
    public Client getClientById(long id);
    public List<Client> getClients();
    public void deleteClient(long id);
    public Client updateClient(Client client);
}
