package com.api.pedidos.controller;

import com.api.pedidos.entity.Pedido;
import com.api.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Pedido> criarPedidos(@RequestBody List<Pedido> pedidos) throws Exception {
        return pedidoService.salvarPedidos(pedidos);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Pedido> obterPedidos() {
        return pedidoService.obterPedidos();
    }

    @GetMapping(value = "/numero-controle/{numeroControle}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Pedido> consultarPedidosPorNumeroControle(@PathVariable String numeroControle) {
        return pedidoService.consultarPedidosPorNumeroControle(numeroControle);
    }

    @GetMapping(value = "/data-cadastro", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Pedido> consultarPedidosPorDataCadastro(@RequestParam("dataCadastro") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataCadastro) {
        return pedidoService.consultarPedidosPorDataCadastro(dataCadastro);
    }

    @GetMapping(value = "/todos", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Pedido> consultarTodosPedidos() {
        return pedidoService.obterPedidos();
    }
}

