package com.cronoporta.projeto.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class M_Arduino{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private boolean emergencyButtonMessage;
    private boolean obstructionMessage;

    public M_Arduino(String emergencyButtonMessage, String obstructionMessage) {
    }


    public boolean isEmergencyButtonMessage() {
        return emergencyButtonMessage;
    }

    public void setEmergencyButtonMessage(boolean emergencyButtonMessage) {
        this.emergencyButtonMessage = emergencyButtonMessage;
    }

    public boolean isObstructionMessage() {
        return obstructionMessage;
    }

    public void setObstructionMessage(boolean obstructionMessage) {
        this.obstructionMessage = obstructionMessage;
    }
}
