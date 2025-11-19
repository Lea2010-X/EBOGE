package Modelo;
<<<<<<< HEAD
=======

>>>>>>> 047dcec6e5d36a161280f8dd98b378e3f8089b6b

public interface EfectoJugador {

    
    void aplicarEfectoInicial(Jugador jugador);

    
    void actualizarEfectoPorTurno(Jugador jugador);

    
    void removerEfecto(Jugador jugador);

    
    boolean haExpirado();

    
    String getNombre();
}