package Modelo;


public interface EfectoJugador {

    
    void aplicarEfectoInicial(Jugador jugador);

    
    void actualizarEfectoPorTurno(Jugador jugador);

    
    void removerEfecto(Jugador jugador);

    
    boolean haExpirado();

    
    String getNombre();
}