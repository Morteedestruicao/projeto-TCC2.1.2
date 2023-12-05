package com.cronoporta.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fazecast.jSerialComm.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

@SpringBootApplication
public class ProjetoApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProjetoApplication.class, args);
		initialize();

	}


	public static void initialize() {
		SerialPort[] ports = SerialPort.getCommPorts();
		SerialPort chosenPort = null;

		// Seleciona a porta serial correta
		for (SerialPort port : ports) {
			if (port.getDescriptivePortName().contains("COM3")) {
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

			chosenPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

			chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 0);


			BufferedReader input = new BufferedReader(new InputStreamReader(chosenPort.getInputStream()));
			OutputStream output = chosenPort.getOutputStream();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			while (true) {
				byte[] newData = new byte[chosenPort.bytesAvailable()];
				int numRead = chosenPort.readBytes(newData, newData.length);
				String receivedData = new String(newData).trim();

				try {
					if (input.ready()) {
//						String botaoEmergencia = input.readLine();
						System.out.println(input.readLine());
						if (receivedData.equalsIgnoreCase("true")) {
							boolean botaoEmergencia = true;
							System.out.println("Dados recebidos: " + botaoEmergencia);
					} else if (receivedData.equalsIgnoreCase("false")) {
							boolean botaoEmergencia = false;
							System.out.println("Dados recebidos: " + botaoEmergencia);
					} else {
						System.out.println("Mensagem não reconhecida: " + receivedData);
					}
						String portaObstruida = input.readLine();

						System.out.println("Dados recebidos: " + portaObstruida);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("deu ruim");
		}
	}
}


//package com.cronoporta.projeto;
//
//import com.cronoporta.projeto.Model.M_Porta;
//import com.fazecast.jSerialComm.SerialPort;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ProjetoApplication {
//
//	private static boolean emergencyButtonPressed = false;
//	private static boolean doorObstructed = false;
//	public static void main(String[] args) {
//		SpringApplication.run(ProjetoApplication.class, args);
//		iniciarArduino();
//	}
//
//	public static void iniciarArduino() {
//		SerialPort[] ports = SerialPort.getCommPorts();
//		SerialPort chosenPort = null;
//
//		// Seleciona a porta serial correta
//		for (SerialPort port : ports) {
//			if (port.getDescriptivePortName().contains("COM4")) {
//				chosenPort = port;
//				break;
//			}
//		}
//
//		if (chosenPort == null) {
//			System.out.println("Nenhuma porta serial encontrada para o Arduino.");
//			return;
//		}
//
//		// Abre a porta serial selecionada
//		if (chosenPort.openPort()) {
//			System.out.println("Porta serial aberta para comunicação com o Arduino.");
//
//			// Configura os parâmetros da porta serial (velocidade de transmissão, etc.)
//			chosenPort.setBaudRate(9600);
//			chosenPort.setNumDataBits(8);
//			chosenPort.setNumStopBits(1);
//			chosenPort.setParity(SerialPort.NO_PARITY);
//
//			// Loop para leitura dos dados da porta serial
//			while (true) {
//				// Verifica se há dados disponíveis para leitura
//				if (chosenPort.bytesAvailable() > 0) {
//					byte[] newData = new byte[chosenPort.bytesAvailable()];
//					int numRead = chosenPort.readBytes(newData, newData.length);
//					String receivedData = new String(newData).trim();
//
//					boolean mensagemRecebida = input.readline;
//
//					if (receivedData.equalsIgnoreCase(true)) {
//						emergencyButtonPressed = true;
//					} else if (receivedData.equalsIgnoreCase("false")) {
//						emergencyButtonPressed = false;
//					} else {
//						System.out.println("Mensagem não reconhecida: " + receivedData);
//					}
//
//					if (receivedData.equalsIgnoreCase("true")) {
//						doorObstructed = true;
//					} else if (receivedData.equalsIgnoreCase("false")) {
//						doorObstructed = false;
//					} else {
//						System.out.println("Mensagem não reconhecida: " + receivedData);
//					}
//				}
//
//
//
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		} else {
//			System.out.println("Não foi possível abrir a porta serial.");
//		}
//	}}