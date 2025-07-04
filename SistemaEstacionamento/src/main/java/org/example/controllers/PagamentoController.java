package org.example.controllers;

import org.example.factory.PagamentoFactory;
import org.example.model.Moto;
import org.example.model.Pagamento;
import org.example.model.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import org.example.dal.PagamentoDAO;

public class PagamentoController {

    List<Pagamento> pagamentos;

    public PagamentoController() {
        pagamentos = new ArrayList<Pagamento>();
    }

    public String pagar(Ticket ticket, double valor, String formaDePagamento) throws Exception {
        int id = 1;
        if (!pagamentos.isEmpty()) {
            id = pagamentos.getLast().getId();
        }
        try {
            Pagamento pagamento = PagamentoFactory.criarPagamento(id, ticket, valor, formaDePagamento);
            pagamentos.add(pagamento);
            if (pagamento != null) {
                if (ticket.getVeiculo().getClass() == Moto.class) {
                    ticket.getVaga().alterarDisponibilidadeMoto(true);
                } else {
                    ticket.getVaga().alterarDisponibilidade(true);
                }
                return "pagamento criado com sucesso!";
            }
            return "pagamento nao criado!";
        } catch (IllegalFormatException e) {
            System.err.println("[Controller] Erro inesperado argumentos errados de pagamento: " + e.getMessage());
            throw new Exception("Argumentos errados de pagamento" + e.getMessage());
        }

    }

    public String editarPagamentoPorID(int id) throws Exception {
        try {
            Pagamento pagamento = pagamentos.stream().filter(p -> p.getId() == id).findFirst().get();
            if (pagamento != null) {
                return "pagamento editado com sucesso!";
            }
            return "pagamento não encontrado";
        } catch (Exception e) {
            System.err.println("[Controller] Erro inesperado ao editar pagamento: " + e.getMessage());
            throw new Exception("pagamento não encontrado" + e.getMessage());
        }
    }

    public List<String> listarPagamentos() throws Exception {
        try {
            return pagamentos.stream().map(Pagamento::toString).toList();
        } catch (Exception e) {
            System.err.println("[Controller] Erro inesperado ao listar pagamentos: " + e.getMessage());
            throw new Exception("Nenhum pagamento encontrado" + e.getMessage());
        }
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public String removerPagamentoPorId(int id) throws Exception {
        try {
            Pagamento pg = pagamentos.stream().filter(p -> p.getId() == id).findFirst().get();
            pagamentos.remove(pg);
            return "pagamento excluido com sucesso!";
        } catch (Exception e) {
            System.err.println("[Controller] Erro inesperado ao remover pagamento: " + e.getMessage());
            throw new Exception("Nenhum pagamento encontrado" + e.getMessage());
        }
    }

    public void salvar() throws IOException, ClassNotFoundException{
        PagamentoDAO.salvar(pagamentos);
    }
}
