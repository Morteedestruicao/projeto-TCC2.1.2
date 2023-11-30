package com.cronoporta.projeto.Service;

import com.cronoporta.projeto.Model.M_Reserva;
import com.cronoporta.projeto.Model.M_ReservaSema;
import com.cronoporta.projeto.Model.M_Resposta;
import com.cronoporta.projeto.Repository.R_Reserva;
import com.cronoporta.projeto.Repository.R_ReservaSema;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class S_Reserva {
    private static R_Reserva reserva;
    public S_Reserva(R_Reserva reserva) {
        this.reserva = reserva;
    }

    public static M_Resposta reservas(int id_porta, LocalDateTime horario_abertura, LocalDateTime  data_fechamento){
        boolean podeSalvar = true;
        String mensagem = "";
        LocalDateTime dataAtual = LocalDateTime.now();
//        listReservas();
        if(horario_abertura.isBefore(dataAtual)){
            podeSalvar = false;
            mensagem += "O Horario de abertura tem que ser maior que a data atual.";
        }
        if(data_fechamento.isBefore(horario_abertura)){
            podeSalvar = false;
            mensagem += "O Horario de abertura não pode ser maior que o horario de fechamento.";
        }
//        for(){
//
//        }
        if (S_Generico.textoEstaVazio(String.valueOf(id_porta))) {
            podeSalvar = false;
            mensagem += "A sala precisa ser preenchida.";
        }
        if (S_Generico.textoEstaVazio(String.valueOf(horario_abertura))) {
            podeSalvar = false;
            mensagem += "O horario de abertura precisa ser informada.";
        }
        if (S_Generico.textoEstaVazio(String.valueOf(data_fechamento))) {
            podeSalvar = false;
            mensagem += "O horario de fechamento precisa ser informada.";
        }
        if (podeSalvar) {
            M_Reserva m_reserva = new M_Reserva();
            m_reserva.setData_abertura(horario_abertura);
            m_reserva.setData_fechamento(data_fechamento);
            m_reserva.setId_porta(id_porta);
            try {
                M_Reserva m_reserva1 = reserva.save(m_reserva);
                mensagem += "Horario Salvo com Sucesso!";
                return new M_Resposta(podeSalvar,mensagem, m_reserva1.getId());
            } catch (DataIntegrityViolationException e) {
                mensagem += "Houve um problema ao tentar salvar o horario!";
                podeSalvar = false;
            }
        }

        return new M_Resposta(podeSalvar,mensagem);
    }


    public static M_Resposta deletarHorario(long id){

        boolean deletou;
        String mensagem = "";
        try {
            reserva.deleteById(id);
            deletou = true;
            mensagem += "Horario deletado com sucesso!";
        }catch (DataIntegrityViolationException e){
            deletou = false;
            mensagem += "Houve um problema ao tentar deletar o horario!";
        }
        M_Resposta m_resposta = new M_Resposta(deletou,mensagem);
        return m_resposta;
    }

    public static ArrayList<M_Reserva> listReservas(){
        return reserva.listReservas();
    }


}