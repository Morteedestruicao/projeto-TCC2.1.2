package com.cronoporta.projeto.Service;

import com.cronoporta.projeto.Model.M_Porta;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

@Service
public class S_Arduino {

    private static SerialPort serialPort;
    public static void mandarArduino() {
        if (serialPort != null && serialPort.isOpen()) { // Verifica se a porta serial está aberta
            M_Porta m_porta = new M_Porta();
            boolean ativoAtual = m_porta.isAtivo();
            String message = ativoAtual ? "true" : "false";

            serialPort.setBaudRate(9600); // Configura a taxa de transmissão (baud rate)
            serialPort.writeBytes(message.getBytes(), message.getBytes().length);
            System.out.println("Enviado para Arduino: " + message);

        } else {
            System.err.println("A porta serial não está aberta ou não foi inicializada.");
        }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
    }}}