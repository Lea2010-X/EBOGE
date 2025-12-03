**Checklist de Prácticas de Construcción de Software - EBOGE**

**1. NOMENCLATURA Y CONVENCIONES**

` `**Nombres Significativos y Reveladores de Intención**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Variables con nombres descriptivos|Aplicado|jugadoresConfigurados, casillasNormales, turnosRestantes|
|Nombres de clases como sustantivos|` `Aplicado|Jugador, Carta, Mapa, MotorDeTurnos|
|Nombres de métodos como verbos|` `Aplicado|iniciarPartida(), moverPorDado(), aplicarEfecto()|
|Nombres de constantes en MAYÚSCULAS|` `Aplicado|RUTA\_VISTA\_INICIO, CAPACIDAD, RONDAS\_ESPERADAS|
|Evitar prefijos/sufijos innecesarios|Aplicado|No hay notación húngara ni prefijos como str, int, etc.|

**2. FUNCIONES Y MÉTODOS**

**Principio de Responsabilidad Única (SRP)**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Métodos pequeños (≤ 20 líneas)|Aplicado|Mayoría de métodos son concisos: robar(), vacio(), terminarTurno()|
|Una sola responsabilidad por método|Aplicado|lanzarDado(), moverPorDado(), aplicarEfecto() hacen una sola cosa|
|Evitar efectos secundarios|Aplicado|Los métodos hacen lo que su nombre indica|
|Nivel de abstracción consistente|Aplicado|Métodos operan a un nivel coherente de detalle|

` `**Gestión de Argumentos**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Mínimo número de parámetros|Aplicado|Mayoría de métodos tienen ≤ 3 parámetros|
|Evitar banderas booleanas|Parcial|moverPorDado() vs moverPorCarta() - buena solución al problema|
|Usar objetos para múltiples parámetros|Aplicado|ControladorMazosYTurnos recibe objetos en lugar de listas largas|

**3. COMENTARIOS**

**Código Auto-Documentado**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Preferir código claro sobre comentarios|Aplicado|El código es muy legible sin necesidad de comentarios explicativos|
|Comentarios informativos mínimos|Aplicado|Solo se usan comentarios donde realmente aportan valor|
|Sin comentarios redundantes|Aplicado|No hay comentarios que repitan lo que dice el código|
|Comentarios TODO eliminados en producción|Aplicado|No se observan TODOs pendientes|

**4. FORMATEO Y ESTRUCTURA**

**Organización del Código**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Estructura vertical consistente|Aplicado|Orden lógico: campos → constructor → públicos → privados|
|Espaciado vertical apropiado|Aplicado|Separación clara entre métodos y secciones|
|Indentación consistente|Aplicado|Indentación uniforme en todo el proyecto|
|Líneas no excesivamente largas|Aplicado|Líneas generalmente < 100 caracteres|
|Agrupación lógica de código relacionado|Aplicado|Métodos relacionados están juntos|

**5. OBJETOS Y ESTRUCTURAS DE DATOS**

**Encapsulación**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Campos privados|Aplicado|Todos los campos son private o private final|
|Getters/Setters apropiados|Aplicado|Solo expuestos cuando es necesario|
|Evitar exponer detalles de implementación|Aplicado|Interfaces limpias: Mazo, Jugador, Partida|
|Ley de Demeter|Aplicado|Evita cadenas largas: partida.getMapa().getCasillas() es aceptable|

**Uso de Interfaces y Abstracción**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Programación a interfaces|Aplicado|EfectoJugador, EfectoConObjetivo, EfectoSinObjetivo, AccionCasilla|
|Polimorfismo sobre condicionales|Aplicado|Strategy Pattern en efectos y acciones|

**6. MANEJO DE ERRORES**

**Excepciones y Validaciones**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Usar excepciones en lugar de códigos|Aplicado|IllegalArgumentException en validaciones|
|Excepciones informativas|Aplicado|Mensajes claros: "El número de caras debe estar entre 3 y 30"|
|No retornar null innecesariamente|Aplicado|Uso de Optional implícito, validaciones tempranas|
|Try-catch solo donde tiene sentido|Aplicado|ControladorPantallaCarga - manejo de I/O|
|Validaciones en constructores|Aplicado|Dado, Jugador validan parámetros|

**7. PRUEBAS UNITARIAS**

**Testing Comprehensivo**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Tests por cada clase crítica|Aplicado|DadoTest, JugadorTest, MazoTest, MapaTest|
|Tests descriptivos con @DisplayName|Aplicado|Uso de anotaciones JUnit 5|
|Uso de @Nested para organización|Aplicado|JugadorTest organiza tests en contextos|
|Assertions múltiples con assertAll|Aplicado|Tests verifican múltiples condiciones coherentemente|
|Tests de casos límite|Aplicado|Validaciones de rangos, valores nulos, límites|
|@RepeatedTest para aleatoriedad|` `Aplicado|TipoCasillaTest, MazoTest verifican comportamiento aleatorio|

**8. PATRONES DE DISEÑO**

**Patrones Aplicados**

|**Patrón**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|**Strategy Pattern**|Aplicado|EfectoJugador, AccionCasilla - diferentes estrategias de efectos|
|**State Pattern**|` `Aplicado|EstadoJugador (NORMAL, INMOVILIZADO)|
|**Observer Pattern**|Aplicado|MovimientoEventManager, CambioCasillaEventManager|
|**Factory Method**|Parcial|generarCartaAleatoria() en Mazo|
|**Singleton**|` `No aplicado|ControladorPrincipal podría beneficiarse|


**9. ORGANIZACIÓN Y ARQUITECTURA**

` `**Estructura del Proyecto**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Paquetes bien organizados|Aplicado|modelo, controlador, vista claramente separados|
|Separación de concerns|Aplicado|Lógica de negocio separada de UI|
|Cohesión alta|Aplicado|Clases relacionadas agrupadas: cartas.\*, jugador.\*, mapa.\*|
|Acoplamiento bajo|Aplicado|Comunicación a través de interfaces y eventos|

**10. CÓDIGO LIMPIO - ASPECTOS ADICIONALES**

**Prácticas Avanzadas**

|**Práctica**|**Estado**|**Evidencia**|
| :-: | :-: | :-: |
|Evitar código duplicado (DRY)|Aplicado|Métodos reutilizables: notificarMovimiento(), buscarCeldaPorIndice()|
|Inmutabilidad cuando sea posible|Aplicado|Uso extensivo de final: private final String nombre|
|Null Safety|Aplicado|Validaciones con Objects.requireNonNull()|
|Enums sobre constantes|Aplicado|TipoCasilla, ColorJugador, EstadoJugador, TipoCarta|

