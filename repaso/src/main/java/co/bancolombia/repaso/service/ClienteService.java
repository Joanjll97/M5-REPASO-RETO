package co.bancolombia.repaso.service;

import co.bancolombia.repaso.entity.Cliente;
import co.bancolombia.repaso.exception.ClienteNotFoundException;
import co.bancolombia.repaso.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        cliente.ifPresentOrElse(
                c -> {},
                () -> { throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id); }
        );
        return cliente;
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}