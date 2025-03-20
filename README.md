# Simulador de Preguntas
##  Creador de Cuestionarios Personalizados para SharePoint

Simulador de Preguntas es una aplicación de escritorio desarrollada en **Java con Swing** que te permite diseñar y exportar cuestionarios personalizados para su uso en plataformas como SharePoint. Olvídate de las hojas de cálculo y los formatos complicados. Con Simulador de Preguntas, puedes crear cuestionarios atractivos en minutos y compartirlos fácilmente con tu equipo.

[![Imagen de ejemplo](src/recursos/Panel_Principal.png)](URL_del_vídeo_o_demo)

##  Características

✅ **Interfaz gráfica intuitiva** con componentes Swing.
 **Creación y edición de preguntas** personalizadas.
 **Importación y exportación** de preguntas desde archivos CSV.
 **Generación de archivos ZIP** para su fácil distribución en SharePoint.
 **Personalización de la apariencia** con imágenes y estilos.

##  Estructura del Proyecto

SimuladorPreguntas
│──  src/
│   │──  paquete/     # Clases principales del proyecto
│   │   │── Main.java
│   │   │── Main.form
│   │   │── Questions.java
│   │   │── Questions.form
│   │   │── Pregunta.java
│   │   │── Utilidades.java
│   │   │── Preguntas.csv
│   │   │── Preguntas_1.csv
│   │   │── Preguntas.zip
│   │──  recursos/   # Imágenes y otros recursos
│   │   │── ...
│──  libraries/     # Librerías externas necesarias
│── README.md         # Documentación del proyecto
│── build.xml        # Archivo de construcción de Ant
│── nbproject/       # Archivos de configuración de NetBeans

##  Instalación

### ✅ Requisitos y herramientas

* Java 8 o superior.
* NetBeans IDE (o cualquier otro compatible con Java Swing).

###  Dependencias

Este proyecto requiere las siguientes librerías externas:

* `AbsoluteLayout.jar` → Para la gestión de diseño en la interfaz gráfica.

Asegúrate de agregar esta librería en NetBeans tras clonar el proyecto.

###  Pasos para ejecutar el proyecto

1.  Clonar este repositorio:

    ```bash
    git clone [https://github.com/tu-usuario/SimuladorPreguntas.git](https://github.com/tu-usuario/SimuladorPreguntas.git)
    ```

2.  Abrir el proyecto en **NetBeans**.
3.  Agregar la librería necesaria desde la carpeta `libraries/`.
4.  Ejecutar el archivo `Main.java` desde NetBeans.

##  Notas adicionales

* Se recomienda utilizar **NetBeans** para facilitar la configuración y ejecución.
* Para futuras mejoras, se podría agregar una conexión a una base de datos para la gestión de preguntas y usuarios.
* Considera mejorar la gestión de la transparencia de las imágenes PNG.

¡Crea y comparte cuestionarios personalizados con **Simulador de Preguntas**! 
