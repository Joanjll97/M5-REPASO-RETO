package co.bancolombia.repaso.controller;

import co.bancolombia.repaso.entity.DTO.ClienteDTO;
import co.bancolombia.repaso.entity.Cliente;
import co.bancolombia.repaso.entity.DTO.PrestamoDTO;
import co.bancolombia.repaso.service.ClienteService;
import co.bancolombia.repaso.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final PrestamoService prestamoService;

    public ClienteController(ClienteService clienteService, PrestamoService prestamoService) {
        this.clienteService = clienteService;
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(cliente -> {
                    ClienteDTO clienteDTO = new ClienteDTO();
                    clienteDTO.setId(cliente.getId());
                    clienteDTO.setNombre(cliente.getNombre());
                    clienteDTO.setEmail(cliente.getEmail());
                    clienteDTO.setTelefono(cliente.getTelefono());
                    clienteDTO.setDireccion(cliente.getDireccion());
                    return ResponseEntity.ok(clienteDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @GetMapping("/{id}/historial")
    public ResponseEntity<List<PrestamoDTO>> getHistorialByClienteId(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(cliente -> {
                    List<PrestamoDTO> historial = prestamoService.findLastThreeByClienteId(id);
                    return ResponseEntity.ok(historial);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}