# 🛣️ SilkRoad — Simulador de Robots y Tiendas

## 📘 Descripción del Proyecto
**SilkRoad** es una simulación interactiva desarrollada en **Java** que representa un tablero compuesto por **celdas (Cells)**, las cuales pueden contener **robots (Robots)** y **tiendas (Stores)** con distintos comportamientos.  

El objetivo del proyecto fue aplicar conceptos avanzados de **Programación Orientada a Objetos (POO)**, incluyendo:
- Herencia y polimorfismo  
- Encapsulamiento  
- Sobrecarga y sobrescritura de métodos  
- Manipulación de colecciones (`ArrayList`)  
- Visualización gráfica mediante figuras (clases `Rectangle`, `Triangle`, etc.)  

---

## 🧩 Funcionalidades Principales

### 🦾 Robots
- Los **robots** pueden moverse a lo largo del tablero, gastar o ganar recursos (tenges), e interactuar con tiendas.  
- Cada movimiento tiene un **costo asociado** según la distancia recorrida.  
- Existen varios tipos de robots especializados, como `TenderRobot` o `NeverbackRobot`.

### 🏠 Tiendas (Stores)
Las **tiendas** son puntos clave del tablero. Cada una tiene su propio comportamiento cuando un robot llega:
- 🟢 **Store (base):** almacena y regenera recursos.  
- 🤖 **AutonomousStore:** se autorregenera de forma independiente.  
- ⚔️ **FighterStore:** puede defenderse y limitar el robo de recursos.  
- 💘 **LoveStore:** si dos robots coinciden en la misma celda, crea un **nuevo robot** (¡la “tienda motel” del sistema!).  

Cada tienda utiliza un esquema de colores aleatorio y cambia visualmente según su estado (vacía o con recursos).

---

## ⚙️ Estructura del Proyecto

```plaintext
src/
├── Cell.java              # Representa una celda del tablero
├── Robot.java             # Clase base de los robots
├── Store.java             # Clase base de las tiendas
├── AutonomousStore.java   # Tienda con regeneración automática
├── FighterStore.java      # Tienda defensiva
├── LoveStore.java         # Tienda especial (motel)
├── SilkRoad.java          # Controlador principal del juego
├── Rectangle.java / Triangle.java  # Clases gráficas
└── ... (otras clases de soporte)
