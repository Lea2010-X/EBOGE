# Especificación de Requisitos de Software (ERS)
**Proyecto:** EBOGE  
**Basado en:** IEEE Std 830-1998  

---

## 1. Introducción

### 1.1 Propósito  
El presente documento tiene como finalidad establecer de forma estructurada, verificable y trazable los **requisitos funcionales** del sistema **EBOGE**, un juego de mesa digital desarrollado en **Java**.  
Este documento servirá como **base contractual y técnica** para el diseño, desarrollo, pruebas y mantenimiento del sistema, de acuerdo con las recomendaciones del estándar **IEEE Std 830-1998**.

---

### 1.2 Alcance del sistema  
**EBOGE** es un juego de mesa digital multijugador local en el que los participantes compiten por alcanzar la meta antes que sus oponentes.  
Los jugadores lanza un **dado configurable (de 3 a 20 caras)** para avanzar por el tablero. 
El tablero tiene un tamaño definido según el numero de caras del dado y las casillas se general de forma aleatoria, permitiendo variaciones dinámicas en cada partida.   
Según el tipo de casilla en que caiga, el sistema activa una carta de uno de los **cuatro mazos** disponibles:  
- **Propia**  
- **Target**  
- **Global**  
- **Maestra**

---

### 1.3 Definiciones, acrónimos y abreviaturas  

| Término | Definición |
|----------|-------------|
| **Casilla normal** | Casilla sin efecto especial; no dispara cartas ni aplica estados. |
| **Casilla de restricción** | Casilla que aplica una inmovilización temporal al jugador durante *n* turnos, con 1 ≤ n ≤ 3. |
| **Casilla propia** | Casilla que dispara una carta del mazo **Propia**, afectando únicamente al jugador activo. |
| **Casilla target** | Casilla que dispara una carta del mazo **Target**, aplicando su efecto a un jugador objetivo. |
| **Casilla global** | Casilla que dispara una carta del mazo **Global**, aplicando su efecto a todos los jugadores. |
| **Casilla maestra** | Casilla que dispara una carta del mazo **Maestra**; el jugador decide si activarla. Puede modificar el tablero. |
| **Casilla meta** | Casilla final del recorrido; al alcanzarla o sobrepasarla, el jugador gana la partida. |
| **Mapa procedural** | Tablero generado mediante algoritmo pseudoaleatorio con distribución variable de casillas. |
| **Estado de inmovilización** | Condición temporal que impide mover por *n* turnos. |
| **Turno** | Ciclo de juego: lanzamiento de dado → movimiento → resolución de efectos. |
| **Dado N** | Dado configurable de *N* caras (3 ≤ N ≤ 30). |


---

### 1.4 Referencias  
- **IEEE Std 830-1998.** Recommended Practice for Software Requirements Specifications.

---

### 1.5 Visión general del documento  
El documento se estructura en cuatro secciones principales:

1. Introducción y definiciones.  
2. Descripción general del sistema.  
3. Requisitos funcionales.  
---

## 2. Descripción general

### 2.1 Perspectiva del producto  
EBOGE es un **software de escritorio autónomo**, ejecutable en una sola máquina.  
No depende de red ni servidores externos.  
Su núcleo se basa en:  
- Un **motor de turnos**,  
- Un **sistema de generacion aleatorio de casillas**,  
- Un **sistema modular de cartas**.

---

### 2.2 Funciones del producto  
- Configuración inicial de partida (jugadores, dado, generación del mapa).  
- Generación aleatoria  de casillas normales, de restricción y especiales.  
- Gestión automática del orden de turnos.  
- Lanzamiento de dado y movimiento del jugador.  
- Activación y resolución de efectos de cartas (*propia, target, global, maestra*).  
- Registro y notificación de eventos en pantalla.  
- Detección de condición de victoria.

---

### 2.3 Características de los usuarios  
El sistema está dirigido a **jugadores casuales**, sin requerimientos técnicos previos.

---

### 2.4 Restricciones  

| Tipo | Descripción |
|------|--------------|
| **Lenguaje** | Java (JDK 21 o superior). |
| **Modalidad** | Multijugador local. |
| **Idioma** | Español. |
| **Entrada** | Teclado y ratón. |

---

### 2.5 Suposiciones y dependencias  
- Todos los jugadores comparten el mismo dispositivo.  
- Los efectos de cartas y la generación aleatoria están predefinidos.

---

### 2.6 Requisitos futuros  
- Integración con red local o juego en línea.

---

## 3. Requisitos específicos

### 3.1 Interfaces externas  
**Interfaz de usuario (IU):**
- Visualización gráfica del tablero.  
- Panel de estado (turno actual, dado, cartas, estados de restricción).  
- Panel de control.  
- Registro de eventos.

---

### 3.2 Requisitos funcionales  

| ID | Requisito Funcional | Descripción |
|----|----------------------|--------------|
| **RF-01** | Configuración de jugadores | Permitir entre 2 y 8 jugadores con nombres únicos. |
| **RF-02** | Configuración de dado | Seleccionar número de caras entre 3 y 20. |
| **RF-03** | Generación de mapa | Crear mapa con tamaño variable según las caras del dado con distribución aleatoria de casillas. |
| **RF-04** | Orden de turnos | Determinar automáticamente el orden de los jugadores. |
| **RF-05** | Movimiento | Lanzar el dado y mover la ficha según el resultado. |
| **RF-06** | Detección de casilla | Determinar el tipo de casilla y activar carta si aplica. |
| **RF-07** | Cartas propias | Aplicar efectos al jugador activo. |
| **RF-08** | Cartas target | Solicitar objetivo y aplicar efecto al jugador seleccionado. |
| **RF-09** | Cartas globales | Aplicar efectos simultáneamente a todos los jugadores. |
| **RF-10** | Cartas maestras | Mostrar opción para decidir si usar la carta maestra. |
| **RF-11** | Modificación del tablero | Aplicar cambios estructurales si se usa la carta maestra. |
| **RF-12** | Rebarajado | Rebarajar los mazos al agotarse. |
| **RF-13** | Condición de victoria | Declarar ganador al llegar o sobrepasar la casilla meta. |
| **RF-14** | Casilla de restricción | Aplicar inmovilización por *n* turnos (1 ≤ n ≤ 3). |
| **RF-15** | Casilla normal | No aplicar efecto adicional ni activar carta alguna. |
