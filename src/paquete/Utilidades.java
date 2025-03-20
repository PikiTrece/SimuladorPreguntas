package paquete;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pikip
 */
public class Utilidades {
    
        //FUNCIÓN PARA ESTABLECER UNA IMAGEN EN UN JLABEL CON OPCIÓN DE MANTENER PROPORCIONES
        public static void SetImageLabel(JLabel labelName, String root, Boolean keepProportions) {
        // Cargar la imagen
        ImageIcon image = new ImageIcon(root);
        Image originalImage = image.getImage();

        // Si es true, mantener la relación de aspecto
        if (keepProportions) {
            int labelWidth = labelName.getWidth();
            int labelHeight = labelName.getHeight();

            // Obtener las dimensiones originales de la imagen
            int originalWidth = originalImage.getWidth(null);
            int originalHeight = originalImage.getHeight(null);

            // Calcular el factor de escala manteniendo las proporciones
            double scaleFactor = Math.min((double) labelWidth / originalWidth, (double) labelHeight / originalHeight);

            // Calcular las nuevas dimensiones de la imagen
            int newWidth = (int) (originalWidth * scaleFactor);
            int newHeight = (int) (originalHeight * scaleFactor);

            

            // Redimensionar la imagen manteniendo la proporción
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            labelName.setIcon(resizedIcon);
            labelName.repaint();
        } else {
            // Si es false, cambiar el aspecto de la imagen al tamaño del JLabel
            ImageIcon icon = new ImageIcon(originalImage.getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
            labelName.setIcon(icon);
            labelName.repaint();
        }
        
        
    }
        //FUNCIÓN PARA ESTABLECER UNA IMAGEN EN UN JLABEL CON DIMENSIONES ESPECÍFICAS
        public static void SetImageLabel(JLabel labelName, String root, Dimension dimension, boolean keepProportions) {
        ImageIcon image = new ImageIcon(root);
        Image originalImage = image.getImage();

        // Si se mantiene la relación de aspecto (proporciones)
        if (keepProportions) {
            int labelWidth = dimension.width;
            int labelHeight = dimension.height;

            // Obtener las dimensiones originales de la imagen
            int originalWidth = originalImage.getWidth(null);
            int originalHeight = originalImage.getHeight(null);

           // Calcular el factor de escala manteniendo las proporciones
            double scaleFactor = Math.min((double) labelWidth / originalWidth, (double) labelHeight / originalHeight);

            // Calcular las nuevas dimensiones de la imagen
            int newWidth = (int) (originalWidth * scaleFactor);
            int newHeight = (int) (originalHeight * scaleFactor);

            // Redimensionar la imagen manteniendo la proporción
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            labelName.setIcon(new ImageIcon(resizedImage));
        } else {
            // Si no se mantienen las proporciones, redimensionar según las dimensiones proporcionadas
            Image resizedImage = originalImage.getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
            labelName.setIcon(new ImageIcon(resizedImage));
        }

        labelName.repaint();
    }
    
           //FUNCIÓN PARA ESTABLECER UNA IMAGEN EN UN JLABEL CON AJUSTE AUTOMÁTICO DE LA IMAGEN AL JLABEL 
            public static void SetImageLabel(JLabel labelName, String root) {
            ImageIcon image = new ImageIcon(root);
            Icon icon = new ImageIcon(image.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
            labelName.setIcon (icon);
            labelName.repaint();
    }   

            //FUNCIÓN PARA MOSTRAR UN PANEL DENTRO DE UN CONTENEDOR
            public static void ShowPanel(JPanel contenedor, JPanel contenido){
            contenido.setSize(680, 420);
            contenido.setLocation (0, 0);

            //contenedor.removeAll();
            contenedor.add(contenido, FlowLayout.CENTER);
            contenedor.revalidate();
            contenedor.repaint();
        }
        
        
        //FUNCIÓN PARA CREAR UNA LISTA DE RUTAS DE ARCHIVOS    
        public static ArrayList<String> CreateStringList(String root, String name, String filetype, int size) {
        // Crear una lista ArrayList de tipo String
        ArrayList<String> fileList = new ArrayList<>();

        // Bucle para generar las rutas de los archivos con el formato "Foto1", "Foto2", ..., "FotoN"
        for (int i = 0; i < size; i++) {
            // Crear la ruta de cada archivo
            String filePath = root + "/" + name + i + "." + filetype;
            // Añadir la ruta a la lista
            fileList.add(filePath);
        }

        // Devolver la lista generada
        return fileList;
    }
        
       // FUNCIÓN PARA LEER EL CONTENIDO DE UN ARCHIVO Y DEVOLVERLO COMO UN STRING
        public static String readFile(String filePath) throws IOException {
            
        // Creamos un StringBuilder para almacenar el contenido del archivo
        StringBuilder content = new StringBuilder();
        
        // Intentamos leer el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            // Leemos el archivo línea por línea
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");  // Agregamos la línea leída al contenido
                
            }   
           
        } catch (IOException e) {
            // Si hay algún error mostramos un mensaje de error
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        
        // Convertimos el contenido acumulado en un String y lo devolvemos
        return content.toString();
    } 

      
}
