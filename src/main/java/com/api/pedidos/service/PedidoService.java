package com.api.pedidos.service;

import com.api.pedidos.entity.Pedido;
import com.api.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido salvarPedido(Pedido pedido) throws Exception {
        validarPedido(pedido);
        aplicarRegrasNegocio(pedido);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> salvarPedidos(List<Pedido> pedidos) throws Exception {
        if (pedidos.size() > 10) {
            throw new Exception("Número máximo de pedidos excedido (10)");
        }

        for (Pedido pedido : pedidos) {
            salvarPedido(pedido);
        }
        return pedidos;
    }

    public List<Pedido> obterPedidos() {
        return pedidoRepository.findAll();
    }

    private void validarPedido(Pedido pedido) throws Exception {
        if (pedidoRepository.existsByNumeroControle(pedido.getNumeroControle())) {
            throw new Exception("Número de controle já cadastrado");
        }
        if (pedido.getCodigoCliente() < 1 || pedido.getCodigoCliente() > 10) {
            throw new Exception("Código de cliente inválido");
        }
    }

    private void aplicarRegrasNegocio(Pedido pedido) {
        if (pedido.getDataCadastro() == null) {
            pedido.setDataCadastro(new Date());
        }
        if (pedido.getQuantidade() == null) {
            pedido.setQuantidade(1);
        }

        double valorTotal = pedido.getValor() * pedido.getQuantidade();
        if (pedido.getQuantidade() >= 10) {
            valorTotal *= 0.90;
        } else if (pedido.getQuantidade() > 5) {
            valorTotal *= 0.95;
        }
        pedido.setValorTotal(valorTotal);
    }

    public List<Pedido> consultarPedidosPorNumeroControle(String numeroControle) {
        return pedidoRepository.findByNumeroControle(numeroControle);
    }

    public List<Pedido> consultarPedidosPorDataCadastro(Date dataCadastro) {
        return pedidoRepository.findByDataCadastro(dataCadastro);
    }
}
