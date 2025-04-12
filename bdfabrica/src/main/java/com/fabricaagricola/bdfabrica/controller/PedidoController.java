package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Pedido;
import com.fabricaagricola.bdfabrica.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Listar todos os pedidos
    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // Buscar pedido por número
    @GetMapping("/{numero}")
    public ResponseEntity<Pedido> buscarPorNumero(@PathVariable String numero) {
        return pedidoRepository.findById(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo pedido
    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Atualizar pedido existente
    @PutMapping("/{numero}")
    public ResponseEntity<Pedido> atualizar(@PathVariable String numero, @RequestBody Pedido pedidoAtualizado) {
        return pedidoRepository.findById(numero).map(pedido -> {
            pedido.setNotaFiscal(pedidoAtualizado.getNotaFiscal());
            pedido.setDataEmissao(pedidoAtualizado.getDataEmissao());
            pedido.setValorTotal(pedidoAtualizado.getValorTotal());
            pedido.setStatus(pedidoAtualizado.getStatus());
            pedido.setFormaPagamento(pedidoAtualizado.getFormaPagamento());
            return ResponseEntity.ok(pedidoRepository.save(pedido));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar pedido
    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletar(@PathVariable String numero) {
        return pedidoRepository.findById(numero)
                .map(pedido -> {
                    pedidoRepository.delete(pedido);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
