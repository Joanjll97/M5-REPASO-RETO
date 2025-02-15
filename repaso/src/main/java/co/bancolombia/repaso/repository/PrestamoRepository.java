package co.bancolombia.repaso.repository;

import co.bancolombia.repaso.entity.Prestamo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByClienteIdOrderByFechaCreacionDesc(Long clienteId, Pageable pageable);
    List<Prestamo> findByClienteId(Long clienteId);
}