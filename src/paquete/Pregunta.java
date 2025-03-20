/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paquete;

/**
 *
 * @author pikip
 */
public class Pregunta {
    
    private final String pregunta;
    private final String RCorrecta;
    private final String RIncorrecta1;
    private final String RIncorrecta2;
    private final String RIncorrecta3;

    // Constructor con 5 parámetros
    public Pregunta(String pregunta, String respuestaCorrecta, String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3) {
        this.pregunta = pregunta;
        this.RCorrecta = respuestaCorrecta;
        this.RIncorrecta1 = respuestaIncorrecta1;
        this.RIncorrecta2 = respuestaIncorrecta2;
        this.RIncorrecta3 = respuestaIncorrecta3;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getRCorrecta() {
        return RCorrecta;
    }

    public String getRIncorrecta1() {
        return RIncorrecta1;
    }

    public String getRIncorrecta2() {
        return RIncorrecta2;
    }

    public String getRIncorrecta3() {
        return RIncorrecta3;
    }
    
}
