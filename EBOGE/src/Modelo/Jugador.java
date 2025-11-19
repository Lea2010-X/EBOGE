package Modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * (Patrones Strategy y State)
 *
 * Representa a un jugador.
 * -STATE: Mantiene una referencia a su estado actual (estado).
 * -STRATEGY: Mantiene una lista de EfectoJugador (estrategias)
 * y delega en ellas la ejecución de los efectos.
 */
public class Jugador {

    private final String nombre;
    private final ColorJugador color;
    private int posicion;
    private int ultimoMovimiento; 
    private boolean requiereActivacion;

    /**
     * (Patrón State) El estado actual del jugador.
     */
    private EstadoJugador estado;

    /**
     * (Patrón Strategy) La lista de "estrategias" de efectos activas.
     */
    private final List<EfectoJugador> efectosActivos;

    // --- Constructor ---

    /**
     * Construye un nuevo jugador en su estado inicial.
     *
     * @param nombre El nombre del jugador (debe ser único).
     * @param color El color de la ficha del jugador.
     */
    public Jugador(String nombre, ColorJugador color) {
        // Validación de "Guard Clause"
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede ser nulo o vacío.");
        }
        
        this.nombre = nombre.trim();
        this.color = Objects.requireNonNull(color, "El color no puede ser nulo.");
        this.posicion = 0;
        this.estado = EstadoJugador.NORMAL; 
        this.efectosActivos = new ArrayList<>();
        this.ultimoMovimiento = 0;
        this.requiereActivacion = false;
    }

    // --- Métodos de Lógica de Juego (Delegación) ---

    /**
     * Añade una nueva estrategia de efecto al jugador.
     *
     * @param efecto La estrategia concreta (ej. un EfectoStun).
     */
    public void aplicarEfecto(EfectoJugador efecto) {
        Objects.requireNonNull(efecto, "El efecto no puede ser nulo.");
        this.efectosActivos.add(efecto);
        
        // Delega la aplicación inicial a la estrategia
        efecto.aplicarEfectoInicial(this);
    }

    /**
     * Actualiza todos los efectos activos.
     * Este método debe ser llamado al inicio del turno del jugador.
     *
     * Delega la lógica de actualización a cada estrategia
     * y remueve las que han expirado.
     */
    
     public void actualizarEfectos() {
        
        Iterator<EfectoJugador> iterador = this.efectosActivos.iterator();

        while (iterador.hasNext()) {
            EfectoJugador efecto = iterador.next();  
            
            efecto.actualizarEfectoPorTurno(this);
            
            if (efecto.haExpirado()) {
                
                efecto.removerEfecto(this);
                
                iterador.remove();
            }
        }
    }

     /**
      * Mueve al jugador hacia adelante y registra el movimiento.
      * El MotorDeTurnos debe usar esto.
      * * @param cantidad La cantidad de casillas a avanzar (resultado del dado).
      * @param totalCasillas El total de casillas en el mapa (para no pasarse).
      */
     public void avanzar(int cantidad, int totalCasillas) {
         this.ultimoMovimiento = cantidad; // ¡Guardamos el movimiento!
         
         int nuevaPosicion = this.posicion + cantidad;
         
         // Lógica para no pasarse de la meta (asumiendo que la meta es la última casilla)
         if (nuevaPosicion >= totalCasillas) {
             this.posicion = totalCasillas - 1; 
         } else {
             this.posicion = nuevaPosicion;
         }
         this.requiereActivacion = true; 
     }

     /**
      * Mueve al jugador hacia atrás y registra el movimiento.
      * Usado por cartas como Cronorruptura.
      * * @param cantidad La cantidad de casillas a retroceder.
      */
     public void retroceder(int cantidad) {
         this.ultimoMovimiento = -cantidad; // Guardamos el retroceso
         this.posicion -= cantidad;
         
         if (this.posicion < 0) {
             this.posicion = 0; // No ir a posiciones negativas
         }
     }
     

    /**
     * Simula el lanzamiento de un dado.
     * El comportamiento puede variar según los efectos activos.
     *
     * @param dado El dado de la partida.
     * @return El resultado del lanzamiento.
     */
    public int lanzarDado(Dado dado) {
        Objects.requireNonNull(dado, "El dado no puede ser nulo.");
        
        boolean tieneMovimientoDoble = this.efectosActivos.stream()
            .anyMatch(efecto -> efecto.getNombre().equals("Movimiento Doble"));

        int resultadoBase = dado.lanzar();

        return tieneMovimientoDoble ? (resultadoBase * 2) : resultadoBase;
    }

    // --- Getters y Setters (Manejo de Estado) ---

    /**
     * (Patrón State) Obtiene el estado actual.
     *
     * @return El EstadoJugador (NORMAL, INMOVILIZADO).
     */
    public EstadoJugador getEstado() {
        return estado;
    }

    /**
     * (Patrón State) Permite a los efectos (Estrategias) cambiar el estado.
     *
     * @param estado El nuevo estado del jugador.
     */
    void setEstado(EstadoJugador estado) {
        this.estado = estado;
    }

    /**
     * (Patrón State) Método de conveniencia para consultar el estado.
     *
     * @return true si el estado es INMOVILIZADO.
     */
    public boolean estaInmovilizado() {
        return this.estado == EstadoJugador.INMOVILIZADO;
    }
    
    // --- Getters estándar ---

    public String getNombre() {
        return nombre;
    }

    public ColorJugador getColor() {
        return color;
    }

    public int getPosicion() {
        return posicion;
    }

     public int getUltimoMovimiento(){
        return ultimoMovimiento;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    public boolean requiereActivacion() {
        return requiereActivacion;
    }

    public void setRequiereActivacion(boolean requiereActivacion) {
        this.requiereActivacion = requiereActivacion;
    }

   

    /**
     * Devuelve una lista (solo lectura) de los efectos activos.
     *
     * @return Una nueva lista con los efectos actuales.
     */
    public List<EfectoJugador> getEfectosActivos() {
        return new ArrayList<>(this.efectosActivos);
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Jugador jugador = (Jugador) obj;
        // RF-01 implica que el nombre es la clave única
        return nombre.equalsIgnoreCase(jugador.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase());
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", color=" + color +
                ", posicion=" + posicion +
                ", estado=" + estado +
                '}';
    }
}