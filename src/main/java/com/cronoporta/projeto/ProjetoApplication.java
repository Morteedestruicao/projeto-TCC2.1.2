package com.cronoporta.projeto;

import com.cronoporta.projeto.Model.M_Porta;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoApplication {

	private static boolean emergencyButtonPressed = false;
	private static boolean doorObstructed = false;
	public static void main(String[] args) {
		SpringApplication.run(ProjetoApplication.class, args);
		iniciarArduino();
	}

	public static void iniciarArduino() {
		SerialPort[] ports = SerialPort.getCommPorts();
		SerialPort chosenPort = null;

		// Seleciona a porta serial correta
		for (SerialPort port : ports) {
			if (port.getDescriptivePortName().contains("Arduino")) {
				chosenPort = port;
				break;
			}
		}

		if (chosenPort == null) {
			System.out.println("Nenhuma porta serial encontrada para o Arduino.");
			return;
		}

		// Abre a porta serial selecionada
		if (chosenPort.openPort()) {
			System.out.println("Porta serial aberta para comunicação com o Arduino.");

			// Configura os parâmetros da porta serial (velocidade de transmissão, etc.)
			chosenPort.setBaudRate(9600);
			chosenPort.setNumDataBits(8);
			chosenPort.setNumStopBits(1);
			chosenPort.setParity(SerialPort.NO_PARITY);

			// Loop para leitura dos dados da porta serial
			while (true) {
				// Verifica se há dados disponíveis para leitura
				if (chosenPort.bytesAvailable() > 0) {
					byte[] newData = new byte[chosenPort.bytesAvailable()];
					int numRead = chosenPort.readBytes(newData, newData.length);
					String receivedData = new String(newData).trim();

					if (receivedData.equalsIgnoreCase("true")) {
						emergencyButtonPressed = true;
					} else if (receivedData.equalsIgnoreCase("false")) {
						emergencyButtonPressed = false;
					} else {
						System.out.println("Mensagem não reconhecida: " + receivedData);
					}

				}

				// Adiciona um pequeno atraso para evitar consumo excessivo de CPU
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Não foi possível abrir a porta serial.");
		}
	}}