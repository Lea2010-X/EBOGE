package Modelo;

import java.util.HashMap;
import java.util.Map;

public class MotorDeTurnos {

    private Partida partida; 
    private Jugador jugadorEnTurno; 
    
    // Directorio de especialistas (Strategy Pattern)
    private Map<TipoCasilla, AccionCasilla> accionesPorCasilla; 

    public MotorDeTurnos(Partida partida) {
        this.partida = partida;
        this.accionesPorCasilla = new HashMap<>();
        
        inicializarAcciones();
    }

    private void inicializarAcciones() {
     // Aquí cargamos a todos los especialistas (falta por implementar)
        accionesPorCasilla.put(TipoCasilla.PROPIA,   new AccionCasillaPropia());
     /* accionesPorCasilla.put(TipoCasilla.GLOBAL,   new AccionCasillaGlobal());
        accionesPorCasilla.put(TipoCasilla.MAESTRA,  new AccionCasillaMaestra());
        accionesPorCasilla.put(TipoCasilla.BLANCO,   new AccionCasillaBlanco());  
        accionesPorCasilla.put(TipoCasilla.TRAMPA,   new AccionCasillaTrampa());
        
        accionesPorCasilla.put(TipoCasilla.NORMAL,   new AccionCasillaSegura());
        accionesPorCasilla.put(TipoCasilla.INICIO,   new AccionCasillaSegura());
        accionesPorCasilla.put(TipoCasilla.VICTORIA, new AccionCasillaSegura()); 
     */
    }

    public void ejecutarTurno() { 
        
        this.jugadorEnTurno = partida.getJugadorActual();

        // 1. Validar estado (Stun/Inmovilizado)
        // Asumimos que Jugador tiene lógica de efectos
        /* jugadorEnTurno.actualizarEfectos();
        if (jugadorEnTurno.estaInmovilizado()) {
             finalizarTurno();
             return;
        }
        */

        int pasos = realizarLanzamiento(); 
        System.out.println("Turno de " + jugadorEnTurno.getNombre() + " | Dado: " + pasos);

        
        realizarMovimiento(pasos); 
        
        //Para la logica de los tiros dobles
        while (jugadorEnTurno.requiereActivacion()) {
            jugadorEnTurno.setRequiereActivacion(false); //Ponemos la bandera en false para evitar ciclos infinitos

            int posicionActual = jugadorEnTurno.getPosicion();

            realizarAccionDeCasilla(posicionActual); 
        }
        
        finalizarTurno(); 
    }

    private int realizarLanzamiento() { 
        return partida.getDado().lanzar();
    }

    private int realizarMovimiento(int pasos) { 
        int totalCasillas = partida.getMapa().getTotalCasillas();
        
        jugadorEnTurno.avanzar(pasos, totalCasillas);
        
        return jugadorEnTurno.getPosicion();
    }

    private void realizarAccionDeCasilla(int posicion) { 
        TipoCasilla tipo = partida.getMapa().identificarTipoDeCasilla(posicion);
        
        AccionCasilla accion = accionesPorCasilla.get(tipo);

        if (accion != null) {
            accion.ejecutar(partida, jugadorEnTurno); 
        }
    }

    private void finalizarTurno() { 
        // Verificar condiciones de victoria
        if (jugadorEnTurno.getPosicion() >= partida.getMapa().getTotalCasillas() - 1) {
        } else {
            partida.pasarAlSiguienteJugador();
        }
    }
}
