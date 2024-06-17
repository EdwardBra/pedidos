package com.api.pedidos.repository;


import com.api.pedidos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByNumeroControle(String numeroControle);
    List<Pedido> findByNumeroControle(String numeroControle);
    List<Pedido> findByDataCadastro(Date dataCadastro);


}
