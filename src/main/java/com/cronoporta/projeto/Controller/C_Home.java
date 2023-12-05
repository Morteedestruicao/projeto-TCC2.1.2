package com.cronoporta.projeto.Controller;

import com.cronoporta.projeto.Model.M_Arduino;
import com.cronoporta.projeto.Model.M_Resposta;
import com.cronoporta.projeto.ProjetoApplication;
import com.cronoporta.projeto.Service.S_Email;
import com.cronoporta.projeto.Service.S_Reserva;
import com.cronoporta.projeto.Service.S_ReservaSema;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@SessionAttributes("usuario")
public class C_Home {
    private String emergencyButtonMessage = "";
    private String obstructionMessage = "";

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            model.addAttribute("usuario",session.getAttribute("usuario"));
            model.addAttribute("reservas",S_Reserva.listReservas());
            model.addAttribute("reservaSema",S_ReservaSema.listReservasSema());
            model.addAttribute("emergencyButtonMessage", emergencyButtonMessage);
            model.addAttribute("obstructionMessage", obstructionMessage);
            return "Home/home";
        } else {
            // A sessão não existe, redirecionar para a página de login
            return "redirect:/";
        }
    }
    @PostMapping("/Reserva")
    @ResponseBody
    public M_Resposta processReserva(
                                 @RequestParam("horarioE") LocalDateTime horario_abertura,
                                 @RequestParam("horarioS") LocalDateTime data_fechamento,
                                 @RequestParam("sala") int sala,
                                 HttpSession session
                                 ) {
        if(session.getAttribute("usuario") != null) {
            return S_Reserva.reservas(sala,horario_abertura,data_fechamento);
        }
        return null;
    }

    @PostMapping("/ReservaSema")
    @ResponseBody
    public M_Resposta processReservaSema(
            @RequestParam("horarioESema") String horario_abertura,
            @RequestParam("horarioSSema") String data_fechamento,
            @RequestParam("date_ini") String data_ini,
            @RequestParam("date_fim") String data_fim,
            @RequestParam("salaSema") int sala,
            @RequestParam("segunda") boolean segunda,
            @RequestParam("terca") boolean terca,
            @RequestParam("quarta") boolean quarta,
            @RequestParam("quinta") boolean quinta,
            @RequestParam("sexta") boolean sexta,
            @RequestParam("sabado") boolean sabado,
            @RequestParam("domingo") boolean domingo,
            HttpSession session
    ) {
        if(session.getAttribute("usuario") != null) {
           return S_ReservaSema.reservasSema(sala,segunda, terca, quarta, quinta,sexta,sabado,domingo,horario_abertura,data_fechamento, data_ini, data_fim);
        }
        return null;
    }

    @PostMapping("/Relatorio")
    @ResponseBody
    public M_Resposta enviarRelatorio(@RequestParam("email") String email){
        return S_Email.sendMail(email);
    }

    @Autowired
    private ProjetoApplication projetoApplication;

    @GetMapping("/getMessages")
    @ResponseBody
    public M_Arduino getMessages() {

        return new M_Arduino(emergencyButtonMessage,obstructionMessage);
    }

}
