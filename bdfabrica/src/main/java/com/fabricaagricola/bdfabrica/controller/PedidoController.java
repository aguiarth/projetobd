package com.fabricaagricola.bdfabrica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fabricaagricola.bdfabrica.model.Pedido;
import com.fabricaagricola.bdfabrica.service.PedidoService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
	private PedidoService pedidoService;
	
	@Autowired
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	@PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.salvar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Pedido> buscarPorNumero(@PathVariable String numero) {
        Optional<Pedido> pedido = pedidoService.buscarPorNumero(numero);
        return pedido.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{numero}")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable String numero, @RequestBody Pedido pedido) {
        if (!numero.equals(pedido.getNumero())) {
            return ResponseEntity.badRequest().build();
        }
            return ResponseEntity.ok(pedidoService.atualizar(pedido));
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletar(@PathVariable String numero) {
        pedidoService.excluir(numero);
        return ResponseEntity.noContent().build();
    }
}