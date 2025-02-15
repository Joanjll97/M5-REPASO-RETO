package co.bancolombia.repaso.service;

import co.bancolombia.repaso.entity.DTO.PrestamoDTO;
import co.bancolombia.repaso.entity.Prestamo;
import co.bancolombia.repaso.repository.PrestamoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import co.bancolombia.repaso.exception.PrestamoNotFoundException;
import java.util.Optional;


@Service
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    public Optional<Prestamo> findById(Long id) {
        Optional<Prestamo> prestamo = prestamoRepository.findById(id);
        prestamo.ifPresentOrElse(
                p -> {},
                () -> { throw new PrestamoNotFoundException("Préstamo con ID: " + id); }
        );
        return prestamo;
    }

    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    public List<PrestamoDTO> findLastThreeByClienteId(Long clienteId) {
        return prestamoRepository.findByClienteIdOrderByFechaCreacionDesc(clienteId, PageRequest.of(0, 3))
                .stream()
                .map(prestamo -> {
                    PrestamoDTO dto = new PrestamoDTO();
                    dto.setId(prestamo.getId());
                    dto.setMonto(prestamo.getMonto());
                    dto.setInteres(prestamo.getInteres());
                    dto.setDuracionMeses(prestamo.getDuracionMeses());
                    dto.setEstado(prestamo.getEstado());
                    dto.setFechaCreacion(prestamo.getFechaCreacion());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<PrestamoDTO> findAllByClienteId(Long clienteId) {
        return prestamoRepository.findByClienteId(clienteId)
                .stream()
                .map(prestamo -> {
                    PrestamoDTO dto = new PrestamoDTO();
                    dto.setId(prestamo.getId());
                    dto.setMonto(prestamo.getMonto());
                    dto.setInteres(prestamo.getInteres());
                    dto.setDuracionMeses(prestamo.getDuracionMeses());
                    dto.setEstado(prestamo.getEstado());
                    dto.setFechaCreacion(prestamo.getFechaCreacion());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Prestamo approvePrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new PrestamoNotFoundException("Préstamo con ID: " + id));
        prestamo.setEstado("Aprobado");
        return prestamoRepository.save(prestamo);
    }

    public Prestamo rejectPrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new PrestamoNotFoundException("Préstamo con ID: " + id));
        prestamo.setEstado("Rechazado");
        return prestamoRepository.save(prestamo);
    }

    public Prestamo setPendingPrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new PrestamoNotFoundException("Préstamo con ID: " + id));
        prestamo.setEstado("Pendiente");
        return prestamoRepository.save(prestamo);
    }

    public double calcularCuotaMensual(double monto, double interes, int duracionMeses) {
        double tasaInteresMensual = interes / 100 / 12;
        return (monto * tasaInteresMensual * Math.pow(1 + tasaInteresMensual, duracionMeses)) /
                (Math.pow(1 + tasaInteresMensual, duracionMeses) - 1);
    }
}