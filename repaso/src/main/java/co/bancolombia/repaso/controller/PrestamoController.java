package co.bancolombia.repaso.controller;


import co.bancolombia.repaso.entity.DTO.PrestamoDTO;
import co.bancolombia.repaso.entity.Prestamo;
import co.bancolombia.repaso.service.ClienteService;
import co.bancolombia.repaso.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;
    private final ClienteService clienteService;

    public PrestamoController(PrestamoService prestamoService, ClienteService clienteService) {
        this.prestamoService = prestamoService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Prestamo> getAllPrestamos() {
        return prestamoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamoById(@PathVariable Long id) {
        return prestamoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Prestamo createPrestamo(@RequestBody Prestamo prestamo) {
        // Verificar que el cliente existe
        clienteService.findById(prestamo.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return prestamoService.save(prestamo);
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Prestamo> approvePrestamo(@PathVariable Long id) {
        try {
            Prestamo prestamo = prestamoService.approvePrestamo(id);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Prestamo> rejectPrestamo(@PathVariable Long id) {
        try {
            Prestamo prestamo = prestamoService.rejectPrestamo(id);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/pendiente")
    public ResponseEntity<Prestamo> setPendingPrestamo(@PathVariable Long id) {
        try {
            Prestamo prestamo = prestamoService.setPendingPrestamo(id);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PrestamoDTO>> getAllPrestamosByClienteId(@PathVariable Long clienteId) {
        List<PrestamoDTO> prestamos = prestamoService.findAllByClienteId(clienteId);
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/simulacion")
    public ResponseEntity<Double> simularCuota(@RequestParam double monto, @RequestParam double interes, @RequestParam int duracionMeses) {
        double cuota = prestamoService.calcularCuotaMensual(monto, interes, duracionMeses);
        return ResponseEntity.ok(cuota);
    }
}