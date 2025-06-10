package org.example.factory;

import java.util.List;

import org.example.controllers.VagaController;
import org.example.model.Estacionamento;
import org.example.model.Vaga;

public abstract class EstacionamentoFactory {

    public static Estacionamento criarEstacionamento(String nome, int numeroDeVagas, String endereco, String telefone, String email) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do estacionamento não pode ser vazio.");
        }

        if (numeroDeVagas <= 0) {
            throw new IllegalArgumentException("Número de vagas deve ser maior que zero.");
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereço do estacionamento não pode ser vazio.");
        }

        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone do estacionamento não pode ser vazio.");
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email do estacionamento não pode ser vazio.");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        try{
            List<Vaga> vagas = fazerVagas(numeroDeVagas);
            Estacionamento estacionamento = new Estacionamento(nome, endereco, numeroDeVagas, telefone, email, vagas);
            return estacionamento;
        } catch (Exception e){
            System.err.println("[Factory] Erro inesperado ao criar estacionamento: " + e.getMessage());
            throw new Exception("Erro ao criar estacionamento: " + e.getMessage(), e);
        }
    }

    public static List<Vaga> fazerVagas(int numeroDeVagas) throws Exception {
        if (numeroDeVagas <= 0) {
            throw new IllegalArgumentException("Número de vagas deve ser maior que zero.");
        }
        try{
            VagaController  vagaController = new VagaController();
            List<Vaga> vagasProntas = vagaController.criarVagas(numeroDeVagas);
            return vagasProntas;
        } catch (Exception e) {
            System.err.println("[Factory] Erro inesperado ao criar vagas: " + e.getMessage());
            throw new Exception("Erro ao criar vagas: " + e.getMessage(), e);
        }

    }

}
