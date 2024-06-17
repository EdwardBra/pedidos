package com.api;

import com.api.pedidos.entity.Pedido;
import com.api.pedidos.repository.PedidoRepository;
import com.api.pedidos.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSalvarPedido_ComNumeroControleExistente_DeveLancarExcecao() {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle("12345");
        pedido.setNome("Produto Teste");
        pedido.setValor(100.0);
        pedido.setCodigoCliente(1);

        when(pedidoRepository.existsByNumeroControle("12345")).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> {
            pedidoService.salvarPedido(pedido);
        });

        assertThat(exception.getMessage()).isEqualTo("Número de controle já cadastrado");
    }

    @Test
    public void testSalvarPedido_QuantidadeNaoInformada_DeveDefinirQuantidadeParaUm() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle("12346");
        pedido.setNome("Produto Teste");
        pedido.setValor(100.0);
        pedido.setCodigoCliente(1);

        when(pedidoRepository.existsByNumeroControle("12346")).thenReturn(false);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido savedPedido = pedidoService.salvarPedido(pedido);

        assertThat(savedPedido.getQuantidade()).isEqualTo(1);
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void testSalvarPedido_AplicarDescontoParaQuantidadesGrandes() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle("12347");
        pedido.setNome("Produto Teste");
        pedido.setValor(100.0);
        pedido.setQuantidade(10);
        pedido.setCodigoCliente(1);

        when(pedidoRepository.existsByNumeroControle("12347")).thenReturn(false);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido savedPedido = pedidoService.salvarPedido(pedido);

        assertThat(savedPedido.getValorTotal()).isEqualTo(900.0); // 10% de desconto
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void testConsultarPedidosPorNumeroControle() {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle("12345");
        pedido.setNome("Produto Teste");
        pedido.setValor(100.0);
        pedido.setCodigoCliente(1);

        when(pedidoRepository.findByNumeroControle("12345")).thenReturn(Arrays.asList(pedido));

        List<Pedido> pedidos = pedidoService.consultarPedidosPorNumeroControle("12345");

        assertThat(pedidos).hasSize(1);
        assertThat(pedidos.get(0).getNumeroControle()).isEqualTo("12345");
    }

    @Test
    public void testConsultarPedidosPorDataCadastro() {
        Pedido pedido = new Pedido();
        Date date = new Date();
        pedido.setNumeroControle("12345");
        pedido.setDataCadastro(date);
        pedido.setNome("Produto Teste");
        pedido.setValor(100.0);
        pedido.setCodigoCliente(1);

        when(pedidoRepository.findByDataCadastro(date)).thenReturn(Arrays.asList(pedido));

        List<Pedido> pedidos = pedidoService.consultarPedidosPorDataCadastro(date);

        assertThat(pedidos).hasSize(1);
        assertThat(pedidos.get(0).getDataCadastro()).isEqualTo(date);
    }
}
