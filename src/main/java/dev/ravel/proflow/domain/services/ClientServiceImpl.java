package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Client;
import dev.ravel.proflow.infrastructure.model.Project;
import dev.ravel.proflow.infrastructure.model.Task;
import dev.ravel.proflow.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ProjectService projectService;

    public ClientServiceImpl(ClientRepository clientRepository, ProjectService projectService) {
        this.clientRepository = clientRepository;
        this.projectService = projectService;;
    }

    @Override
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client getClientById(long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public List<Client> getClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public void deleteClient(long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            List<Project> projects = projectService.getProjectsByClientId((int) id);
            if (projects != null) {
                projects.forEach(project -> {
                    projectService.deleteProject((long)project.getId());
                });
            }
            clientRepository.deleteById(id);
        }
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }
}
