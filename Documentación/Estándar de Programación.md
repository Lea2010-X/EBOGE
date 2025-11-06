# **Estándar de Programación: EBOGE**
## **1. Propósito**
El objetivo de este documento es definir el estándar de programación y las convenciones de código que se seguirá para el desarrollo del proyecto **EBOGE**.

El fin es asegurar que todo el código sea **consistente, legible y mantenible**, independientemente de qué miembro del equipo lo escriba.

Este estándar se basa principalmente en las **Convenciones de Código Java de Oracle**, adaptadas a las necesidades específicas de este proyecto.
## **2. Nomenclatura (Naming Conventions)**
Todas las nomenclaturas deben ser descriptivas, claras y escritas en **Español**, para mantener consistencia con el documento ERS y la documentación interna (Javadoc).

Se utilizarán PascalCase y camelCase según corresponda. La única excepción es el SNAKE\_CASE para constantes.
### **2.1. Paquetes (Packages)**
Los nombres de los paquetes son la base de la organización del código.

- **Regla:** Deben estar en minúsculas, sin guiones bajos (\_) ni camelCase.
- **Regla:** Deben seguir la convención de dominio inverso, en nuestro caso: com.eboge.
- **Regla:** Los sub-paquetes se organizarán por funcionalidad o capa (MVC).

**Ejemplo de Estructura de Paquetes:**

com.eboge

├── modelo         // Clases de negocio: Jugador, Tablero, Carta, Dado

├── vista          // Clases de UI: VentanaPrincipal, PanelTablero

├── controlador    // Lógica de juego: GestorTurnos, MotorJuego

└── util           // Clases de ayuda: Registrador, Constantes

- **Correcto:** com.eboge.modelo
- **Incorrecto:** com.eboge.Modelo, eboge.controlador, com.eboge.gestor\_turnos
### **2.2. Clases (Classes)**
Los nombres de clases representan los "sustantivos" de nuestro sistema.

- **Regla:** Deben usar PascalCase (también llamado UpperCamelCase).
- **Regla:** Deben ser sustantivos (ej. Jugador, Tablero) o frases sustantivadas.
- **Regla:** Usar palabras completas y evitar abreviaturas (ej. Gestor en lugar de Gstr).

**Ejemplos (basados en ERS):**

- **Correcto:** Jugador, Tablero, GestorJuego, CartaMaestra, CasillaRestriccion.
- **Incorrecto:** jugador (minúscula), tablero\_procedural (snake\_case), manejadorCartas (camelCase para una clase).
### **2.3. Interfaces (Interfaces)**
Las interfaces definen un "contrato" o una "capacidad".

- **Regla:** Deben usar PascalCase, igual que las clases.
- **Regla:** Generalmente son sustantivos (ej. List) o adjetivos que terminan en -able o -ible (ej. Runnable, Comparable).
- **Regla (Decisión de Equipo):** A diferencia de otras convenciones (como C#), **no usaremos el prefijo I-** (como ICarta) para mantenernos alineados al estándar puro de Oracle (ej. List, Map).

**Ejemplos:**

- **Correcto:** Activable (para una carta), Dibujable (para un objeto en la UI), EstrategiaMovimiento.
- **Incorrecto:** IActivable (prefijo I-), interfaz\_dibujable (snake\_case).
### **2.4. Métodos (Methods)**
Los métodos representan las "acciones" o "verbos" del sistema.

- **Regla:** Deben usar camelCase (también llamado lowerCamelCase).
- **Regla:** Deben ser verbos o frases verbales (ej. hacerAlgo()).
- **Regla (Getters/Setters):** Los métodos de acceso deben seguir el estándar JavaBean: getVariable() y setVariable().
- **Regla (Booleanos):** Los métodos que devuelven un booleano pueden empezar con is o esta (ej. estaListo()) o has o tiene (ej. tieneCartas()).

**Ejemplos (basados en ERS):**

- **Correcto:** generarMapaProcedural() (RF-03), lanzarDado() (RF-05), aplicarEfectoGlobal() (RF-09), seleccionarJugadorObjetivo() (RF-08), estaRestringido() (RF-14).
- **Incorrecto:** GenerarMapa() (PascalCase), lanzar\_dado (snake\_case), efectoGlobal() (sustantivo).
### **2.5. Variables (Variables)**
- **Regla:** Deben usar camelCase.
- **Regla:** Deben ser descriptivas. Evitar nombres de una sola letra, excepto en contadores de bucles (i, j, k).
- **Regla:** No deben comenzar con \_ o $.

**Ejemplos (Instance variables):**

- **Correcto:** int carasDado (RF-02), Jugador jugadorActivo, List<Carta> mazoMaestro.
- **Incorrecto:** CarasDado (PascalCase), jugador\_activo (snake\_case), int n (poco descriptivo).

**Ejemplos (Local variables, dentro de un método):**

- **Correcto:** int resultadoDado = lanzarDado();, Jugador jugadorObjetivo = seleccionarJugadorObjetivo();
- **Incorrecto:** int Resultado (PascalCase).
### **2.6. Constantes (Constants)**
Las constantes son variables cuyo valor no cambia.

- **Regla:** Deben ser declaradas como static final.
- **Regla:** Deben usar SCREAMING\_SNAKE\_CASE (todo en mayúsculas, separadas por \_).

**Ejemplos (basados en ERS):**

- **Correcto:**
- public static final int MIN\_JUGADORES = 2;       // RF-01
- public static final int MAX\_JUGADORES = 8;       // RF-01
- public static final int MIN\_CARAS\_DADO = 3;    // RF-02
- public static final int MAX\_CARAS\_DADO = 30;   // RF-02
- public static final int MAX\_TURNOS\_RESTRICCION = 3; // RF-14
- **Incorrecto:** static final int minJugadores = 2; (camelCase), final int MaxJugadores = 8; (PascalCase y no estática).
## **3. Formato de Código (Code Formatting)**
Un formato consistente es vital para la legibilidad. Todo el equipo debe configurar su IDE (Eclipse, IntelliJ, VSCode) para que siga estas reglas automáticamente.
### **3.1. Indentación (Indentation)**
- **Regla:** Se usarán **4 espacios** para cada nivel de indentación.
- **Regla:** No se deben usar caracteres de tabulación (Tabs). Se debe configurar el IDE para que inserte espacios al presionar la tecla Tab.
### **3.2. Llaves (Braces { })**
Se utilizará el estilo K&R (Kernighan & Ritchie), adoptado por Oracle.

- **Regla:** La llave de apertura ({) se coloca **al final de la misma línea** que la declaración de la estructura (clase, método, if, for, etc.).
- **Regla:** La llave de cierre (}) se coloca en su **propia línea**, alineada verticalmente con la declaración que la inició.
- **Regla:** **Siempre se deben usar llaves**, incluso para bloques de una sola línea (if, for, while). Esto previene errores de lógica.

/\*

` `\* EJEMPLO CORRECTO

` `\*/

public class Jugador {

`    `private int posicion;

`    `public void mover(int pasos) {

`        `if (pasos > 0) {

`            `this.posicion += pasos;

`            `verificarCasillaEspecial();

`        `}

`    `}

}

/\*

` `\* EJEMPLO INCORRECTO (Estilo Allman)

` `\*/

public class Jugador

{ // Llave en línea nueva

`    `private int posicion;

`    `public void mover(int pasos)

`    `{ // Llave en línea nueva

`        `if (pasos > 0) // Sin llaves, peligroso

`            `this.posicion += pasos;

`    `}

}
### **3.3. Longitud de Línea y Saltos (Line Length and Wrapping)**
- **Regla:** Cada línea de código debe tener un máximo de **100 caracteres**.
- **Regla (Wrapping):** Cuando una línea excede los 100 caracteres, debe dividirse.
  - Dividir *antes* de un operador (+, -, &&, ||, .).
  - El salto de línea debe tener un nivel de indentación superior (generalmente **+8 espacios** desde el inicio de la línea original).
  - Nunca se debe dividir el nombre de un método de sus paréntesis ().

// EJEMPLO CORRECTO (Wrapping)

int danioTotal = jugadorUno.getFuerza() + jugadorDos.getBono()

`        `+ efectoCarta.getNivelPoder() + resultadoDado; // +8 espacios

// EJEMPLO INCORRECTO (Difícil de leer)

int danioTotal = jugadorUno.getFuerza() + jugadorDos.getBono() + efectoCarta.getNivelPoder() + resultadoDado;
### **3.4. Espacios en Blanco (Whitespace)**
El uso adecuado de espacios mejora la legibilidad.

- **Regla:** Usar un espacio alrededor de operadores binarios (=, ==, !=, +, -, \*, /, &&, ||, etc.).
  - a = b + c; (Correcto) vs. a=b+c; (Incorrecto).
- **Regla:** Usar un espacio *después* de las comas (,) en listas de parámetros.
  - mover(jugador, pasos); (Correcto) vs. mover(jugador,pasos); (Incorrecto).
- **Regla:** Usar un espacio después de un cast.
  - (Jugador) objetivo; (Correcto) vs. (Jugador)objetivo; (Incorrecto).
- **Regla:** **No** usar espacios entre el nombre de un método y su paréntesis de apertura ().
  - lanzarDado(); (Correcto) vs. lanzarDado (); (Incorrecto).
- **Regla:** En un for, usar un espacio después del punto y coma ;.
  - for (int i = 0; i < 10; i++) { ... } (Correcto).
### **3.5. Declaraciones (Declarations)**
- **Regla:** Solo se permite **una declaración por línea**. Facilita la lectura y los comentarios.
  - **Correcto:**
  - int pasosMaximos;
  - int posicionActual;
  - **Incorrecto:** int pasosMaximos, posicionActual;
- **Regla:** Las variables locales deben declararse lo más cerca posible de su primer uso.
### **3.6. Estructura del Fichero (.java File Structure)**
Cada archivo .java debe seguir un orden estricto para que cualquier miembro del equipo sepa dónde encontrar cada componente.

/\*

` `\* 1. Declaración de Paquete

` `\*/

package com.eboge.modelo;

/\*

` `\* 2. Declaraciones de Imports

` `\* (Agrupadas, ej. java.\*, luego javax.\*, luego org.\*, etc.)

` `\*/

import java.util.List;

import java.util.Random;

/\*\*

` `\* 3. Comentario Javadoc de la Clase

` `\* Describe la clase aquí.

` `\*/

public class Tablero {

`    `/\*

`     `\* 4. Variables Estáticas (static)

`     `\* (public, protected, private)

`     `\*/

`    `public static final int TAMANIO\_TABLERO = 100;

`    `private static final int MAX\_RESTRICCIONES = 5;

`    `/\*

`     `\* 5. Variables de Instancia

`     `\* (public, protected, private)

`     `\*/

`    `private List<Casilla> casillas;

`    `private int contadorRestricciones;

`    `/\*

`     `\* 6. Constructores

`     `\*/

`    `public Tablero() {

`        `this.casillas = generarMapaProcedural();

`    `}

`    `/\*

`     `\* 7. Métodos

`     `\* (Agrupados por funcionalidad si es posible, 

`     `\* ej. métodos públicos, luego privados/de ayuda)

`     `\*/

`    `public Casilla getCasilla(int posicion) {

`        `// ...

`    `}

`    `/\*\*

`     `\* RF-03: Genera el mapa procedural.

`     `\*/

`    `private List<Casilla> generarMapaProcedural() {

`        `// ... lógica de generación

`    `}



`    `// ... más métodos ...



`    `/\*

`     `\* 8. Getters y Setters

`     `\*/

`    `public List<Casilla> getCasillas() {

`        `return this.casillas;

`    `}

}
## **4. Comentarios y Javadoc**
El código debe ser autodocumentado siempre que sea posible (buena nomenclatura). Los comentarios se usan para explicar el "por qué" (la intención), no el "qué" (la sintaxis).
### **4.1. Reglas Generales**
- **Regla:** Todos los comentarios y Javadoc se escribirán en **Español**, para mantener consistencia con el documento ERS y el código fuente.
- **Regla:** Comentar todo el código que no sea obvio. Si un bloque de lógica es complejo, necesita un comentario que explique el algoritmo o la intención.
- **Regla:** No dejar código "comentado" (código muerto) en el repositorio. Si el código no se usa, debe ser eliminado. Se puede recuperar del historial de Git si es necesario.
- **Regla:** Los comentarios deben mantenerse actualizados. Un comentario incorrecto es peor que ningún comentario.
### **4.2. Javadoc (/\*\* ... \*/)**
El Javadoc es mandatorio para todas las clases, interfaces y métodos públicos. Es la documentación oficial de nuestra API interna.

- **Regla:** Debe preceder a la declaración de la clase, interfaz, método o variable de instancia.
- **Regla:** El Javadoc se usa para:
  - **Clases/Interfaces:** Describir su propósito y responsabilidad.
  - **Métodos:** Describir su funcionalidad, parámetros (@param), valor de retorno (@return) y excepciones que lanza (@throws).
  - **Variables de Instancia:** Solo si su propósito no es obvio por el nombre.

/\*\*

` `\* Representa el tablero de juego procedural de EBOGE.

` `\* Esta clase gestiona la lista de casillas, la generación del mapa

` `\* y la posición de los jugadores.

` `\*

` `\* @see Jugador

` `\* @see Casilla

` `\*/

public class Tablero {

`    `/\*\*

`     `\* Lista ordenada de casillas que componen el tablero.

`     `\*/

`    `private List<Casilla> casillas;

`    `/\*\*

`     `\* Genera un nuevo tablero y lo inicializa.

`     `\* Llama internamente a generarMapaProcedural().

`     `\*

`     `\* @see #generarMapaProcedural()

`     `\*/

`    `public Tablero() {

`        `// ...

`    `}

`    `/\*\*

`     `\* Mueve un jugador un número determinado de pasos y activa

`     `\* el efecto de la casilla de destino.

`     `\*

`     `\* @param jugador El jugador que debe moverse.

`     `\* @param pasos El número de pasos a avanzar (resultado del dado).

`     `\* @return La casilla final a la que llegó el jugador.

`     `\* @throws IllegalArgumentException si el jugador es nulo o los pasos son negativos.

`     `\*/

`    `public Casilla moverJugador(Jugador jugador, int pasos) {

`        `if (jugador == null || pasos < 0) {

`            `throw new IllegalArgumentException("Jugador o pasos no válidos.");

`        `}

`        `// ... lógica del método

`    `}

}
### **4.3. Comentarios de Implementación**
Se usan *dentro* de los métodos para explicar la lógica interna.
#### *4.3.1. Comentarios de Bloque (/\* ... \*/)*
- **Regla:** Se usan para describir secciones de código, no para comentar métodos completos (para eso se usa Javadoc).
- **Regla:** Deben tener el mismo nivel de indentación que el código que comentan.

public void aplicarEfectoCarta(Carta carta) {

`    `/\*

`     `\* Lógica para Cartas Target (RF-08):

`     `\* 1. Pedir al jugador activo que seleccione un objetivo.

`     `\* 2. Validar que el objetivo sea válido (no sea él mismo, etc.).

`     `\* 3. Aplicar el efecto al jugador objetivo.

`     `\*/

`    `if (carta.getTipo() == TipoCarta.TARGET) {

`        `Jugador objetivo = ui.solicitarObjetivo(jugadorActivo);

`        `if (objetivo != null) {

`            `carta.aplicarEfecto(objetivo);

`        `}

`    `}

`    `// ...

}
#### *4.3.2. Comentarios de Fin de Línea (// ...)*
- **Regla:** Se usan para comentarios cortos sobre una sola línea o una declaración.
- **Regla:** Deben alinearse si comentan varias líneas seguidas, o ir justo después de la línea de código si el espacio lo permite.

private void verificarRestriccion(Jugador jugador) {

`    `if (jugador.estaRestringido()) {

`        `int turnosRestantes = jugador.getTurnosRestriccion();

`        `jugador.setTurnosRestriccion(turnosRestantes - 1); // Decrementa el contador

`        `if (jugador.getTurnosRestriccion() == 0) {

`            `jugador.setRestringido(false); // Libera al jugador

`            `ui.registrarEvento(jugador.getNombre() + " ya no está inmovilizado.");

`        `} else {

`            `// El jugador sigue inmovilizado, saltar turno

`            `ui.registrarEvento(jugador.getNombre() + " pierde el turno. Turnos restantes: " + jugador.getTurnosRestriccion());

`        `}

`    `}

}
## **5. Prácticas de Programación (Best Practices)**
Estas reglas definen cómo escribir código de alta calidad, robusto y mantenible.
### **5.1. Principios Generales**
- **SRP (Principio de Responsabilidad Única):** Cada clase debe tener una sola razón para cambiar.
  - *Ejemplo:* La clase Jugador maneja el estado del jugador (posición, nombre, estado de restricción). La clase Tablero maneja la estructura del tablero. La clase GestorJuego maneja el flujo del juego (turnos, condición de victoria).
- **DRY (Don't Repeat Yourself - No te Repitas):** Si un bloque de código se usa en más de un lugar, debe ser extraído a un método privado (o de utilidad).
- **KISS (Keep It Simple, Stupid - Mantenlo Simple):** Preferir la solución más simple y clara. Evitar sobre-ingeniería. Los métodos deben ser cortos (idealmente menos de 30 líneas).
### **5.2. Evitar "Números Mágicos" y "Strings Mágicos"**
- **Regla:** No usar valores literales (ej. 3, 100, "CasillaRoja") directamente en la lógica del programa.
- **Justificación:** Dificultan el mantenimiento y la lectura. Si el valor cambia, hay que buscarlo en múltiples lugares.
- **Solución:** Usar constantes (static final), como se definió en la sección 2.6.

// INCORRECTO

if (jugador.getTurnosRestantes() == 3) { ... }

// CORRECTO

if (jugador.getTurnosRestantes() == MAX\_TURNOS\_RESTRICCION) { ... }
### **5.3. Encapsulación y Modificadores de Acceso**
- **Regla:** Todas las variables de instancia (campos) deben ser private.
- **Regla:** El acceso a los campos desde otras clases debe ser a través de métodos public (getters y setters).
- **Regla:** Usar siempre el modificador de acceso más restrictivo posible (private > protected > public). Si un método solo es usado por la propia clase, debe ser private.
### **5.4. Manejo de Excepciones**
- **Regla:** **Nunca** se debe capturar una excepción con un bloque catch vacío (catch (Exception e) {}). Si se captura, se debe manejar (ej. loggear el error) o relanzar.
- **Regla:** Capturar excepciones específicas (ej. IllegalArgumentException) en lugar de la genérica Exception.
- **Regla:** No usar excepciones para control de flujo normal.
### **5.5. Inmutabilidad y final**
- **Regla:** Declarar variables como final siempre que sea posible. Esto se aplica a parámetros de métodos, variables locales y campos que no cambiarán después del constructor.
- **Justificación:** Reduce la complejidad, previene bugs de reasignación accidental y ayuda a crear código más seguro (especialmente en concurrencia).

// Parámetro 'final' asegura que no se reasigne dentro del método

public void aplicarEfecto(final Jugador jugadorObjetivo) {

`    `// jugadorObjetivo = new Jugador(); // <-- Esto daría error de compilación

`    `jugadorObjetivo.setPosicion(0); // Modificar el objeto está bien

}
### **5.6. Uso de la API Estándar de Java**
- **Regla:** Preferir el uso de las clases del API de Colecciones de Java (List, Map, Set) sobre arrays (Jugador[]) cuando la cantidad de elementos sea dinámica.
- **Regla:** Al devolver una colección, **nunca retornar null**. Se debe retornar una colección vacía (ej. Collections.emptyList()) para evitar NullPointerException en el código que llama.

// INCORRECTO

public List<Jugador> getJugadoresRestringidos() {

`    `if (lista.isEmpty()) {

`        `return null; // ¡MAL!

`    `}

`    `return lista;

}

// CORRECTO

public List<Jugador> getJugadoresRestringidos() {

`    `if (lista.isEmpty()) {

`        `return Collections.emptyList(); // ¡BIEN!

`    `}

`    `return lista;

}
### **5.7. Complejidad Ciclomática (Anidación)**
- **Regla:** Evitar la anidación excesiva. Un método no debe tener más de **3 niveles** de indentación (ej. if { for { if { ... } } }).
- **Solución:** Si un método es muy complejo, se debe refactorizar (dividir) en métodos privados más pequeños y con nombres claros.

// INCORRECTO (Difícil de leer)

public void procesarTurno(Jugador jugador) {

`    `if (jugador.estaListo()) {

`        `for (Carta carta : jugador.getMano()) {

`            `if (carta.esUtilizable()) {

`                `if (carta.getTipo() == TipoCarta.MAESTRA) {

`                    `// ... lógica muy anidada

`                `}

`            `}

`        `}

`    `}

}

// CORRECTO (Refactorizado)

public void procesarTurno(Jugador jugador) {

`    `if (!jugador.estaListo()) {

`        `return; // "Guard clause"

`    `}



`    `for (Carta carta : jugador.getMano()) {

`        `procesarCartaUtilizable(carta);

`    `}

}

private void procesarCartaUtilizable(Carta carta) {

`    `if (carta.esUtilizable() && carta.getTipo() == TipoCarta.MAESTRA) {

`        `// ... lógica más simple

`    `}

}

