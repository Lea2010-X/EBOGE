# Pruebas de Aceptación (Documento de Especificación de Requerimientos vs Funcionalidad del Software)

## Descripción del Reporte
El presente apartado tiene como objetivo documentar la validación funcional del software "EBOGE". Se realiza un cotejo directo entre los Requisitos Funcionales (RF) establecidos en la fase de análisis y las funcionalidades implementadas en la versión final del sistema.
Esta matriz de pruebas confirma que la lógica de negocio, las interfaces gráficas y las restricciones del sistema operan conforme a las especificaciones del proyecto, garantizando la calidad y estabilidad del producto entregado.

---

## 1. Validación de Requisitos Funcionales (RF)

### RF-01: Configuración de Jugadores
* **Descripción del Requisito:** El sistema debe permitir la configuración de una partida estableciendo un rango de participantes definido, admitiendo un mínimo de 2 y un máximo de 8 jugadores. Asimismo, se debe garantizar que cada jugador posea un identificador único (nombre y color) para evitar conflictos visuales durante el juego.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** La funcionalidad ha sido verificada satisfactoriamente mediante la implementación de validaciones lógicas en el controlador de configuración.
    * **Restricción de Cantidad:** Se aplicó una validación sobre la estructura de datos (`List<Jugador>`) que impide el inicio del flujo de juego si la colección contiene menos de dos instancias.
    * **Gestión de Unicidad:** Para el límite superior y la distinción visual, se implementó el uso de un `Enum` (`ColorJugador`) que restringe estrictamente la selección a un máximo de 8 colores predefinidos.

### RF-02: Configuración de Dado
* **Descripción del Requisito:** El sistema debe permitir al usuario seleccionar la complejidad del azar mediante la elección del número de caras del dado, aceptando valores en un rango de 3 a 20 caras.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** La clase `ControladorPrincipal` recibe y procesa el parámetro entero `carasDado` desde la vista de configuración. Este valor se inyecta en el constructor de la clase `Partida` y es utilizado posteriormente por el motor de juego para generar números aleatorios dentro del rango especificado.

### RF-03: Generación de Mapa
* **Descripción del Requisito:** El sistema debe generar un tablero de juego de tamaño variable, calculado dinámicamente en función de la configuración de la partida y distribuyendo aleatoriamente los tipos de casillas.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Se implementó la clase `GeneradorDeMapa`, la cual utiliza un algoritmo de generación procedimental. Este recibe las dimensiones del contenedor gráfico y el factor del dado para instanciar un objeto `Mapa` con una distribución estocástica de casillas (Trampa, Carta, Normal).

### RF-04: Orden de Turnos
* **Descripción del Requisito:** El sistema debe determinar y gestionar automáticamente la secuencia de turnos de los jugadores activos.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** La gestión del flujo se realiza mediante la clase `MotorDeTurnos`. Esta implementa una estructura de cola circular que cicla automáticamente al finalizar las acciones del jugador actual.

### RF-05: Movimiento
* **Descripción del Requisito:** Permitir el lanzamiento del dado virtual y actualizar la posición de la ficha del jugador correspondiente al resultado obtenido.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Al accionar el evento de lanzamiento en `ControladorMazosYTurnos`, se calcula el desplazamiento y se actualiza el atributo de posición en la instancia del `Jugador`. Visualmente, `ControladorTablero` refleja este cambio trasladando la ficha en el Grid.

### RF-06: Detección de Casilla
* **Descripción del Requisito:** El sistema debe identificar automáticamente el tipo de casilla en la que aterriza el jugador y desencadenar el evento correspondiente (activación de carta o efecto).
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El `MotorDeTurnos` evalúa la posición final tras el movimiento consultando al modelo `Mapa`. Se verifica el `TipoCasilla` y, si es especial, se invoca al método de robo del `Mazo` correspondiente.

### RF-07: Cartas Propias
* **Descripción del Requisito:** Implementación de cartas que apliquen efectos beneficiosos o de estado únicamente al jugador que las activa.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Verificado mediante la clase `Mazo` y el Switch de tipo `PROPIA`. Clases concretas como `Flash` o `RegresoACasa` encapsulan la lógica que modifica los atributos exclusivamente de la instancia del `Jugador` actual.

### RF-08: Cartas Target (Objetivo)
* **Descripción del Requisito:** Implementación de cartas que requieran la selección de un rival específico para aplicar un efecto perjudicial.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Validado mediante el tipo `BLANCO` en la clase `Mazo`. Cartas como `Plegaria` activan una interfaz de selección en la vista, permitiendo al usuario escoger una instancia de `Jugador` objetivo.

### RF-09: Cartas Globales
* **Descripción del Requisito:** Implementación de cartas cuyos efectos alteren el estado de todos los jugadores en la partida simultáneamente.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Implementado a través del tipo `GLOBAL` en la gestión de mazos. Cartas como `Monzon` iteran sobre la colección completa de `jugadoresPartida`, aplicando modificaciones a cada entidad.

### RF-10: Cartas Maestras
* **Descripción del Requisito:** Implementación de cartas de alto impacto que ofrezcan al jugador la decisión estratégica de activarlas o no.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Gestionado por el tipo `MAESTRA` (ej. `GrilletesDelAlma`). El sistema despliega un cuadro de diálogo modal que pausa el flujo del motor esperando la confirmación del usuario.

### RF-11: Modificación del Tablero
* **Descripción del Requisito:** Capacidad del sistema para alterar estructuralmente el mapa (agregar/quitar casillas o cambiar tipos) mediante efectos de cartas.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** La clase `Mapa` expone métodos para la mutación dinámica. Al activarse ciertas Cartas Maestras, se invoca al `GestorDeCambios`, provocando una actualización inmediata en la vista (`ControladorTablero`).

### RF-12: Rebarajado
* **Descripción del Requisito:** El sistema debe detectar cuando un mazo de cartas se agota y regenerarlo automáticamente para permitir la continuidad del juego.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Se implementó una validación en el método `robar()` de la clase `Mazo`. Mediante la condición `if (this.vacio())`, el sistema invoca automáticamente al método `rellenar()`.

### RF-13: Condición de Victoria
* **Descripción del Requisito:** El sistema debe monitorear la posición de los jugadores y declarar un ganador cuando uno alcance o supere la casilla final.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El `MotorDeTurnos` verifica tras cada movimiento si el índice de posición del `Jugador` actual es mayor o igual al tamaño total de la lista de casillas del `Mapa`.

### RF-14: Casilla de Restricción
* **Descripción del Requisito:** Implementación de casillas o efectos que inmovilicen al jugador por un número determinado de turnos (entre 1 y 3).
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** La clase `Jugador` incluye un atributo de estado (contador de turnos perdidos). El `MotorDeTurnos` valida este atributo al inicio de cada ciclo; si es mayor a cero, omite la fase de movimiento.

### RF-15: Casilla Normal
* **Descripción del Requisito:** Existencia de casillas neutras que no desencadenen efectos ni activación de cartas, sirviendo solo como transición.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El switch de detección de casillas contempla el caso `DEFAULT` o `NORMAL`, finalizando el turno inmediatamente después del movimiento sin activar efectos.

---

## 3. Validación de Requisitos No Funcionales (RNF)

### 3.1. Validación de Aleatoriedad

#### RNF-01: Aleatoriedad
* **Descripción del Requisito:** La generación de resultados del dado y la distribución de las casillas en el tablero deben comportarse de manera estocástica e impredecible.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Se validó la implementación de `java.util.Random` corrigiendo la instanciación del objeto (atributo de clase reutilizable) para evitar semillas repetidas, garantizando una distribución estadística uniforme.

### 3.2. Validación de Usabilidad

#### RNF-02: Facilidad de Inicio de Partida
* **Descripción del Requisito:** El flujo de interacción debe ser eficiente, requiriendo un número máximo de 5 clics para iniciar el juego.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El flujo de navegación consta de únicamente 2 interacciones obligatorias (Botón "Jugar" y Botón "Continuar"), cumpliendo el requisito con un margen de eficiencia superior al 50%.

#### RNF-03: Claridad de Estado
* **Descripción del Requisito:** Los estados alterados de los jugadores (ej. inmovilizado) deben ser representados visualmente de forma inequívoca.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El `ControladorTablero` actualiza el panel de información del jugador activo y emite notificaciones textuales en el área de logs cuando cambian los atributos de estado.

### 3.3. Validación de Seguridad

#### RNF-04: Manejo de Errores
* **Descripción del Requisito:** La aplicación debe interceptar fallos en tiempo de ejecución y gestionarlos de forma controlada.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Se implementó una arquitectura `try-catch` en puntos críticos y un método centralizado `manejarErrorCritico` en `ControladorPrincipal` que captura excepciones no controladas y vuelca la traza en la consola de depuración sin cerrar la app.

### 3.4. Validación de Mantenibilidad y Calidad de Código

#### RNF-05: Cobertura de Pruebas
* **Descripción del Requisito:** El núcleo lógico del sistema debe contar con una cobertura de pruebas unitarias mínima del 80%.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El proyecto incluye un paquete de pruebas (`EBOGE/test/modelo/...`) utilizando JUnit, verificando casos críticos como la generación de mapas y la asignación de turnos.

#### RNF-06: Estructura Modular
* **Descripción del Requisito:** La arquitectura debe presentar alta cohesión y bajo acoplamiento.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Se aplicó polimorfismo mediante la interfaz genérica `Carta`. Esto permite agregar nuevas clases en `modelo.cartas` sin alterar el código del motor de juego (Principio Open/Closed).

#### RNF-07: Documentación de Clases
* **Descripción del Requisito:** Inclusión de documentación Javadoc en clases y métodos públicos.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Se añadieron bloques de documentación Javadoc explicando el funcionamiento y la justificación técnica en métodos complejos refactorizados.

#### RNF-08: Estándares de Codificación
* **Descripción del Requisito:** Adhesión a los estándares de codificación Java.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El código ha sido auditado mediante SonarCloud, resolviendo "Code Smells" y respetando las convenciones de *CamelCase* y *PascalCase*.

#### RNF-09: Abstracción de la UI
* **Descripción del Requisito:** Separación estricta entre la capa de presentación y la lógica de negocio (MVC).
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Existe separación total: Interfaz en `.fxml` (Vista), lógica en clases Java (Modelo) y `Controladores` como intermediarios.

#### RNF-10: Control de Versiones
* **Descripción del Requisito:** Gestión del código fuente mediante Git.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El repositorio refleja un historial de commits semánticos y un flujo de trabajo basado en ramas, con resolución de conflictos mediante Merge.

---

## 4. Validación de Restricciones de Diseño (RD)

### RD-01: Lenguaje y Entorno
* **Descripción del Requisito:** Desarrollo en Java versión 21 o posteriores.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** El proyecto está configurado y compilado bajo el entorno **JavaSE-23**.

### RD-02: Framework de la Interfaz Gráfica
* **Descripción del Requisito:** La IU debe ser implementada obligatoriamente mediante JavaFX.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Todas las vistas utilizan archivos `.fxml` y el código controlador emplea librerías del paquete `javafx.*`.

### RD-03: Arquitectura
* **Descripción del Requisito:** Diseño adherido al patrón Modelo-Vista-Controlador (MVC).
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** La estructura de paquetes (`modelo`, `vista`, `controlador`) refleja fielmente la separación de responsabilidades del patrón.

### RD-04: Estándares de Codificación
* **Descripción del Requisito:** Seguir estándares para asegurar consistencia y mantenibilidad.
* **Dictamen de Estatus:** **CUMPLIDO**
* **Evidencia de Implementación Técnica:** Se realizó refactorización orientada a Clean Code, optimización de recursos y validación mediante análisis estático.
