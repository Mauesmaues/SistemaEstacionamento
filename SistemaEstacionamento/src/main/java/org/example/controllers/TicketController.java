package org.example.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.dal.TicketDAO;
import org.example.factory.TicketFactory;
import org.example.model.Ticket;
import org.example.model.Vaga;
import org.example.model.Veiculo;

public class TicketController {

    private List<Ticket> tickets;

    public TicketController() {
        this.tickets = new ArrayList<>();
    }

    public Ticket gerarTicket(Veiculo veiculo, Vaga vaga, double valor) throws Exception {
        int id = 1;
        try{
            if (!tickets.isEmpty()) {
                id = tickets.get(tickets.size() - 1).getId() + 1;
            }
            LocalDateTime dataHoraEntrada = veiculo.getDataHoraEntrada();
            LocalDateTime dataHoraSaida = LocalDateTime.now();
            int horasTotais = dataHoraEntrada.getHour() - dataHoraSaida.getHour();

            double valorTotal = valor * horasTotais;
            Ticket ticket = TicketFactory.criarTicket(id, veiculo, vaga, dataHoraEntrada, dataHoraSaida, valorTotal);
            tickets.add(ticket);
            return ticket;
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao gerar ticket: " + e.getMessage());
            throw new  Exception("Erro ao gerar ticket: " + e.getMessage(), e);
        }
    }

    public List<Ticket> listarTickets(Vaga vaga) throws Exception {
        try {
            return tickets;
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao listar tickets: " + e.getMessage());
            throw new Exception("Erro ao listar tickets: " + e.getMessage(), e);
        }
    }

    public List<Ticket> getTickets() throws Exception {
        try {
            return tickets;
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao obter tickets: " + e.getMessage());
            throw new Exception("Erro ao obter tickets: " + e.getMessage(), e);
        }
    }

    public Ticket getTicketById(int id) throws Exception {
        try{
            for (Ticket ticket : tickets) {
                if (ticket.getId() == id) {
                    return ticket;
                }
            }
            return null;
        }catch (Exception e){
            System.err.println("[Controller] Erro ao buscar ticket por ID: " + e.getMessage());
            throw  new Exception("Erro ao buscar ticket por ID: " + e.getMessage());
        }
    }

    public boolean atualizarTicket(int id, Veiculo veiculo, Vaga vaga, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, double valor) throws Exception {
        try{
            Ticket ticket = getTicketById(id);
            if (ticket != null) {
                ticket.setVeiculo(veiculo);
                ticket.setVaga(vaga);
                ticket.setDataHoraEntrada(dataHoraEntrada);
                ticket.setDataHoraSaida(dataHoraSaida);
                ticket.setValor(valor);
                return true;
            }
            return false;
        }catch (Exception e) {
            System.err.println("[Controller] Erro ao atualizar ticket: " + e.getMessage());
            throw new Exception("Erro ao atualizar ticket: " + e.getMessage(), e);
        }
    }

    public boolean removerTicket(int id) throws Exception {
        try {
            return tickets.removeIf(ticket -> ticket.getId() == id);
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao remover ticket: " + e.getMessage());
            throw new Exception("Erro ao remover ticket: " + e.getMessage(), e);
        }
    }

    public void salvar() throws IOException, ClassNotFoundException {
        try {
            TicketDAO.salvar(tickets);
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao salvar tickets: " + e.getMessage());
            throw new IOException("Erro ao salvar tickets no arquivo: " + e.getMessage(), e);
        }
    }
}
