# Replicación de Interfaz - Práctica de UI en Android

## Objetivo
El objetivo de esta tarea es la creación de una serie de interfaces para practicar con distintos componentes y ganar soltura en la identificación, distribución y adición de funcionalidades a los elementos presentes en las UI.

---

## Desarrollo de la tarea
En esta tarea se incluye un video de referencia que muestra la interfaz que se deberá replicar.

### Componentes
- **Componentes principales:** La mayoría ya han sido vistos en clase. Si encuentras alguno desconocido, consulta la [documentación oficial de Android](https://developer.android.com/docs) y [Material Design](https://m3.material.io/).
- **Parte central desplazable:** 
  - Se puede realizar utilizando `RecyclerView`, `ScrollView`, `NestedScrollView`, entre otros.
  - Elige la opción que mejor se adapte a tu enfoque o experimenta con varias alternativas.

- **Transición en la parte superior:**
  - Al desplazarse verticalmente, la palabra **"Alarma"** cambia de tamaño.
  - Esto se puede implementar con `MotionLayout`. Consulta más detalles [aquí](https://developer.android.com/develop/ui/views/animations/motionlayout?hl=es-419).
  - Esta transición es opcional y no es obligatoria para que la tarea sea considerada completa.

---

## Funcionalidad de la aplicación

### Barra superior
- Debe mostrar las opciones que aparecen en el video.

### Parte central
- Cada alarma debe poder activarse y desactivarse:
  - **Al activar:** 
    - El contenido de la alarma debe hacerse más visible.
    - Aparece un **toast** con el mensaje: `Alarma ???? activada`.
  - **Al desactivar:**
    - El contenido de la alarma debe hacerse menos visible.
    - Aparece un **toast** con el mensaje: `Alarma ???? desactivada`.

### Botón "+"
- Al pulsar este botón, debe aparecer un **toast** con el mensaje: `Añadir nueva alarma`.

---

## Temas, estilos y valores recomendados

### Organización de archivos XML
- Se recomienda crear diferentes archivos XML para separar los atributos correspondientes:
  - **`dimen.xml`:** Dimensiones.
  - **`styles.xml`:** Estilos.
  - **`strings.xml`:** Cadenas de texto.
  - **`menu.xml`:** Menús.
  - **`font.xml`:** Fuentes.

### Colores
- Utiliza el [Material Theme Builder](https://m3.material.io/theme-builder) para elegir una gama de colores armónica y visualmente agradable.

### Fuentes
- Puedes utilizar las fuentes presentes en la UI del video o seleccionar otras que sean:
  - Visualmente correctas para una UI móvil.
  - Legibles para la mayoría de usuarios.

---

## Notas adicionales
Esta práctica tiene un enfoque en la creatividad y la experimentación. Anímate a probar nuevas formas de implementar los componentes y explora funcionalidades opcionales si tienes tiempo. ¡Buena suerte!

https://github.com/user-attachments/assets/8c27f36f-db2d-4a4c-a61e-53ebbdcb8676
