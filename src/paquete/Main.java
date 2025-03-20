/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package paquete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import paquete.Pregunta;
import paquete.Questions;
import paquete.Utilidades;
import java.awt.Color;
import javax.swing.Timer;
import javax.swing.SwingUtilities;




/**
 *  
 * @author pikip
 */
public class Main extends javax.swing.JFrame {
    
    public static Main instance;
    private final ArrayList<Pregunta> listaPreguntas;
    private final JLabel debugLabel; // Agregar JLabel para el texto de debug
    
    
    /**
     * Creates new form Main
     */
    public Main() {
        instance = this; // Inicializar instance al principio del constructor
        initComponents();
        InfoPanel.setVisible(false); // Agregar esta línea
        
        FondoInfo.setPreferredSize(new Dimension(340, 140)); // Fuerza un tamaño inicial (ajusta los valores según sea necesario)
        FondoInfo.setSize(200, 200); // Fuerza un tamaño inicial (ajusta los valores según sea necesario)

        SwingUtilities.invokeLater(() -> {
            String rutaImagen = "src/recursos/Panel_Info.png"; // Ruta correcta
            paquete.Utilidades.SetImageLabel(FondoInfo, rutaImagen);
        });
       
        // Paso 1: Agregar el ActionListener al JComboBox
        Desplegable1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar a la función para leer el CSV y mostrar las preguntas
                leerCSVYMostrarPreguntas();
                
                Body.revalidate();
                Body.repaint();
            }
        });
        
        listaPreguntas = new ArrayList<>();    
        Body.setLayout(new BoxLayout(Body, BoxLayout.Y_AXIS)); // Cambiar el layout del Body a BoxLayout
        
        // Agregar MouseListener al botón BtnMasOff1
        BtnMasOff1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                agregarNuevaPregunta();
            }
        });
        
        System.out.println("Panel de preguntas agregado al contenedor.");
        ScrollBody.setViewportView(Body); // Asegurar que el Body esté en el ScrollPane
        ScrollBody.revalidate();
        ScrollBody.repaint();
        
        //BOTÓN INFO
        BtnInfOff.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                InfoPanel.setVisible(!InfoPanel.isVisible()); // Cambiar la visibilidad del panel
              
            }
        });
        
        //BOTÓN CREAR
        CrearBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                List<Pregunta> listaPreguntas = obtenerListaPreguntas();
                System.out.println("Número de preguntas obtenidas: " + listaPreguntas.size());
                actualizarCSV(listaPreguntas);
                comprimirEnZIP();
            }
        });
        
        //BOTÓN EJECUTAR
        EjecutarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                List<Pregunta> listaPreguntas = obtenerListaPreguntas();
                System.out.println("Número de preguntas obtenidas: " + listaPreguntas.size());
                actualizarCSV(listaPreguntas);
            }
        });
        
        //BOTÓN GUARDAR
        GuardarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                List<Pregunta> listaPreguntas = obtenerListaPreguntas();
                System.out.println("Número de preguntas obtenidas: " + listaPreguntas.size());
                guardarCSV(listaPreguntas);
            }
        });
        
        // Inicializar el JLabel de debug
        debugLabel = new JLabel();
        Footer.add(debugLabel); // Agregar el JLabel al Footer o a un panel separado
        
        
    }
    
    //AÑADIR NUEVA PREGUNTA
    private void agregarNuevaPregunta() {
        Questions nuevaPregunta = new Questions();
        Body.add(nuevaPregunta);
        ajustarAltoBody();
        Body.revalidate();
        Body.repaint();
        mostrarRegistro("Pregunta añadida (actualmente " + Body.getComponentCount() + ")");
    }
    
    //ELIMINAR UNA PREGUNTA
    public void eliminarPregunta(Questions pregunta) {
        Body.remove(pregunta);
        ajustarAltoBody();
        Body.revalidate();
        Body.repaint();
        mostrarRegistro("Pregunta eliminada (quedan " + Body.getComponentCount() + ")");
    }
    
    //LEER CSV Y MOSTRAR PREGUNTAS
    private void leerCSVYMostrarPreguntas() {
        
        // Eliminar solo las instancias de Questions
        Component[] components = Body.getComponents();
        for (Component component : components) {
            if (component instanceof Questions) {
                Body.remove(component);
            }
        }
        
        listaPreguntas.clear();
      
        // Ruta del archivo CSV (ajusta la ruta al archivo correcto)
        String archivoCSV = "src/paquete/Preguntas.csv"; 

        try {
            // Paso 2: Usar la función readFile de Utilidades para leer el archivo
            String contenidoCSV = Utilidades.readFile(archivoCSV);

            // Paso 3: Procesar el contenido del CSV
            String[] lineas = contenidoCSV.split("\n"); // Se asume que cada línea es un registro

            for (String linea : lineas) {
                // Paso 4: Separar los valores de cada línea
                String[] columnas = linea.split(";"); 

                if (columnas.length >= 5) {
                    // Crear un objeto Pregunta con la información del CSV
                    Pregunta pregunta = new Pregunta(columnas[0], columnas[1], columnas[2], columnas[3], columnas[4]);
                                                        
                    // Añadir la pregunta a la lista
                    listaPreguntas.add(pregunta);
                }
            }
            
            // Verificar que las preguntas se han agregado correctamente
            System.out.println("Número de preguntas cargadas: " + listaPreguntas.size());

            // Mostrar la primera pregunta si hay preguntas
            if (!listaPreguntas.isEmpty()) {
                for (Pregunta pregunta : listaPreguntas){
                    mostrarPregunta(pregunta);
                }   
                ajustarAltoBody(); // Llamar a ajustarAltoBody()
            } else {
            System.out.println("No se encontraron preguntas en el archivo.");
            }  

        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }
    
    //MOSTRAR PREGUNTAS EN LOS TEXTFIELDS
    private void mostrarPregunta(Pregunta pregunta) {
            
            System.out.println("Mostrando pregunta: " + pregunta.getPregunta()); // Imprimir pregunta
                       
            Questions questionsPanel = new Questions();

            // Rellenar los JTextFields con la pregunta y las respuestas
            questionsPanel.getPregunta().setText(pregunta.getPregunta());
            questionsPanel.getRCorrecta().setText(pregunta.getRCorrecta());
            questionsPanel.getRIncorrecta1().setText(pregunta.getRIncorrecta1());
            questionsPanel.getRIncorrecta2().setText(pregunta.getRIncorrecta2());
            questionsPanel.getRIncorrecta3().setText(pregunta.getRIncorrecta3());
            
            // Imprimir las respuestas para verificar que se han volcado correctamente
            System.out.println("Respuesta Correcta: " + pregunta.getRCorrecta());
            System.out.println("Respuesta Incorrecta 1: " + pregunta.getRIncorrecta1());
            System.out.println("Respuesta Incorrecta 2: " + pregunta.getRIncorrecta2());
            System.out.println("Respuesta Incorrecta 3: " + pregunta.getRIncorrecta3());
    
            Body.add(questionsPanel);
                
    }
    
    private void ajustarAltoBody() {
        int alturaTotal = 0;
        for (Component component : Body.getComponents()) {
            alturaTotal += component.getPreferredSize().height;
        }
        Body.setPreferredSize(new Dimension(Body.getPreferredSize().width, alturaTotal));
        Body.revalidate();
        Body.repaint();
    }
    
   
    //CREAR LISTA DE PREGUNTAS
    private List<Pregunta> obtenerListaPreguntas() {
        List<Pregunta> listaPreguntas = new ArrayList<>();
        Component[] components = Body.getComponents();
        for (Component component : components) {
            if (component instanceof Questions) {
                Questions questionsPanel = (Questions) component;
                // Obtener los valores de los JTextFields y crear un objeto Pregunta
                String pregunta = questionsPanel.getPregunta().getText();
                String rCorrecta = questionsPanel.getRCorrecta().getText();
                String rIncorrecta1 = questionsPanel.getRIncorrecta1().getText();
                String rIncorrecta2 = questionsPanel.getRIncorrecta2().getText();
                String rIncorrecta3 = questionsPanel.getRIncorrecta3().getText();
                Pregunta preguntaObj = new Pregunta(pregunta, rCorrecta, rIncorrecta1, rIncorrecta2, rIncorrecta3);
                listaPreguntas.add(preguntaObj);
            }
        }
        return listaPreguntas;
    }
    
    //ACTUALIZAR CSV
    private void actualizarCSV(List<Pregunta> listaPreguntas) {
    String archivoCSV = "src/paquete/Preguntas.csv";
    try (PrintWriter writer = new PrintWriter(archivoCSV)) {
        for (Pregunta pregunta : listaPreguntas) {
            String linea = pregunta.getPregunta() + ";" + pregunta.getRCorrecta() + ";" + pregunta.getRIncorrecta1() + ";" + pregunta.getRIncorrecta2() + ";" + pregunta.getRIncorrecta3();
            writer.println(linea);
        }
        System.out.println("Archivo CSV actualizado correctamente.");
        mostrarConfirmacion("Las preguntas han sido guardadas (" + listaPreguntas.size() + " en total)");
    } catch (IOException e) {
        System.out.println("Error al actualizar el archivo CSV: " + e.getMessage());
        mostrarError("No se pudo crear el archivo de preguntas");
    }
    }
    
    //GUARDAR CSV
    private void guardarCSV(List<Pregunta> listaPreguntas) {
        String archivoCSV = "src/paquete/Preguntas.csv";
        try (PrintWriter writer = new PrintWriter(archivoCSV)) {
            for (Pregunta pregunta : listaPreguntas) {
                String linea = pregunta.getPregunta() + ";" + pregunta.getRCorrecta() + ";" + pregunta.getRIncorrecta1() + ";" + pregunta.getRIncorrecta2() + ";" + pregunta.getRIncorrecta3();
                writer.println(linea);
            }
            System.out.println("Archivo CSV guardado correctamente.");
            mostrarConfirmacion("Las preguntas han sido guardadas (" + listaPreguntas.size() + " en total)");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo CSV: " + e.getMessage());
            mostrarError("No se pudo crear el archivo de preguntas");
          }
    }

    //COMPRIMIR ZIP
    private void comprimirEnZIP() {
        String archivoCSV = "src/paquete/Preguntas.csv";
        String archivoZIP = "src/paquete/Preguntas.zip";
        try (FileOutputStream fos = new FileOutputStream(archivoZIP);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            // Agregar el archivo CSV al ZIP
            ZipEntry zipEntry = new ZipEntry(Paths.get(archivoCSV).getFileName().toString());
            zos.putNextEntry(zipEntry);
            Files.copy(Paths.get(archivoCSV), zos);
            zos.closeEntry();
            System.out.println("Archivo ZIP creado correctamente.");
            mostrarConfirmacion("Las preguntas se guardaron y se exportó el simulador");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo ZIP: " + e.getMessage());
            mostrarAviso("Las preguntas se guardaron, pero no se pudo comprimir el simulador");
        }
    }
    
    //MOSTRAR MENSAJES DEBUG
    private void mostrarMensaje(String mensaje, Color color, int duracion) {
    debugLabel.setForeground(color);
    debugLabel.setText(mensaje);
    Timer timer = new Timer(duracion * 1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            debugLabel.setText(""); // Borrar el mensaje después de la duración
        }
    });
    timer.setRepeats(false);
    timer.start();
}

    private void mostrarError(String mensaje) {
        mostrarMensaje(mensaje, new Color(0xEB4151), 3); // Rojo #EB4151, 3 segundos
    }

    private void mostrarAviso(String mensaje) {
        mostrarMensaje(mensaje, new Color(0xFF9C00), 2); // Amarillo #FF9C00, 2 segundos
    }

    private void mostrarConfirmacion(String mensaje) {
        mostrarMensaje(mensaje, new Color(0x86D295), 2); // Verde #86D295, 2 segundos
    }

    private void mostrarRegistro(String mensaje) {
        mostrarMensaje(mensaje, new Color(0xF7F7F7), 1); // Blanco #F7F7F7, 1 segundo
    }

    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        ScrollBody = new javax.swing.JScrollPane();
        Body = new javax.swing.JPanel();
        SmallBody = new javax.swing.JPanel();
        Subtitulo = new javax.swing.JLabel();
        Desplegable1 = new javax.swing.JComboBox<>();
        Subtitulo2 = new javax.swing.JLabel();
        BtnMasOff1 = new javax.swing.JLabel();
        BtnInfOff = new javax.swing.JLabel();
        InfoPanel = new javax.swing.JPanel();
        FondoInfo = new javax.swing.JLabel();
        FondoBody = new javax.swing.JLabel();
        Footer = new javax.swing.JPanel();
        CrearBtn = new javax.swing.JLabel();
        EjecutarBtn = new javax.swing.JLabel();
        GuardarBtn = new javax.swing.JLabel();
        Debug = new javax.swing.JLabel();
        FondoBase = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(430, 932));
        setMinimumSize(new java.awt.Dimension(430, 932));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(430, 932));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        Fondo.setMaximumSize(new java.awt.Dimension(430, 932));
        Fondo.setMinimumSize(new java.awt.Dimension(430, 932));
        Fondo.setOpaque(false);
        Fondo.setPreferredSize(new java.awt.Dimension(430, 932));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setMaximumSize(new java.awt.Dimension(430, 100));
        Header.setMinimumSize(new java.awt.Dimension(430, 100));
        Header.setOpaque(false);
        Header.setPreferredSize(new java.awt.Dimension(430, 100));
        Header.setRequestFocusEnabled(false);
        Header.setVerifyInputWhenFocusTarget(false);
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Titulo.setFont(new java.awt.Font("Raleway Medium", 0, 24)); // NOI18N
        Titulo.setForeground(new java.awt.Color(247, 247, 247));
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo.setText("Crea tu simulador teórico");
        Titulo.setFocusable(false);
        Titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Header.add(Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 390, 70));

        Fondo.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        ScrollBody.setBackground(new java.awt.Color(0, 0, 204));
        ScrollBody.setForeground(new java.awt.Color(0, 0, 204));
        ScrollBody.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollBody.setMaximumSize(new java.awt.Dimension(430, 560));
        ScrollBody.setMinimumSize(new java.awt.Dimension(430, 560));
        ScrollBody.setPreferredSize(new java.awt.Dimension(430, 560));
        ScrollBody.setViewportView(null);

        Body.setBackground(new java.awt.Color(0, 0, 204));
        Body.setMaximumSize(new java.awt.Dimension(430, 560));
        Body.setMinimumSize(new java.awt.Dimension(430, 560));
        Body.setOpaque(false);
        Body.setPreferredSize(new java.awt.Dimension(430, 560));
        Body.setRequestFocusEnabled(false);

        SmallBody.setMaximumSize(new java.awt.Dimension(430, 376));
        SmallBody.setMinimumSize(new java.awt.Dimension(430, 376));
        SmallBody.setOpaque(false);
        SmallBody.setPreferredSize(new java.awt.Dimension(430, 376));
        SmallBody.setRequestFocusEnabled(false);
        SmallBody.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Subtitulo.setFont(new java.awt.Font("Raleway", 0, 18)); // NOI18N
        Subtitulo.setForeground(new java.awt.Color(247, 247, 247));
        Subtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Subtitulo.setText("Tipo de Simulador");
        Subtitulo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Subtitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SmallBody.add(Subtitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 50));

        Desplegable1.setFont(new java.awt.Font("Raleway Medium", 0, 24)); // NOI18N
        Desplegable1.setForeground(new java.awt.Color(247, 247, 247));
        Desplegable1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ahora Aprendo", "El Cazador", "Atrapa los univercoins", "BAAM", "PiensoPalabra" }));
        SmallBody.add(Desplegable1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 360, 70));

        Subtitulo2.setFont(new java.awt.Font("Raleway Medium", 0, 18)); // NOI18N
        Subtitulo2.setForeground(new java.awt.Color(247, 247, 247));
        Subtitulo2.setText("Añadir una pregunta");
        SmallBody.add(Subtitulo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 260, 30));

        BtnMasOff1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BtnMasOff1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Mas_Off.png"))); // NOI18N
        BtnMasOff1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SmallBody.add(BtnMasOff1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 40, 40));

        BtnInfOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Info_Off.png"))); // NOI18N
        SmallBody.add(BtnInfOff, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 40, 40));

        InfoPanel.setMaximumSize(new java.awt.Dimension(390, 140));
        InfoPanel.setMinimumSize(new java.awt.Dimension(390, 140));
        InfoPanel.setOpaque(false);
        InfoPanel.setPreferredSize(new java.awt.Dimension(390, 140));
        InfoPanel.setLayout(new java.awt.BorderLayout());

        FondoInfo.setFont(new java.awt.Font("Raleway", 0, 12)); // NOI18N
        FondoInfo.setForeground(new java.awt.Color(247, 247, 247));
        FondoInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FondoInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Panel_Info.png"))); // NOI18N
        FondoInfo.setText("<html><div style='text-align: center; vertical-align: middle; height: 100%; display: flex; align-items: center; justify-content: center;'><div>Añade las preguntas que quieras que aparezcan durante la simulación.<br>Luego, pulsa el botón crear para exportar el archivo zip<br>que deberás subir a SharePoint.</div></div></html>");
        FondoInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FondoInfo.setMaximumSize(new java.awt.Dimension(320, 140));
        FondoInfo.setMinimumSize(new java.awt.Dimension(320, 140));
        FondoInfo.setPreferredSize(new java.awt.Dimension(320, 140));
        InfoPanel.add(FondoInfo, java.awt.BorderLayout.CENTER);

        InfoPanel.setVisible(false);

        SmallBody.add(InfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 350, -1));

        FondoBody.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Fondo.png"))); // NOI18N
        FondoBody.setMaximumSize(new java.awt.Dimension(430, 560));
        FondoBody.setMinimumSize(new java.awt.Dimension(430, 560));
        FondoBody.setPreferredSize(new java.awt.Dimension(430, 560));
        SmallBody.add(FondoBody, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout BodyLayout = new javax.swing.GroupLayout(Body);
        Body.setLayout(BodyLayout);
        BodyLayout.setHorizontalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addComponent(SmallBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        BodyLayout.setVerticalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addComponent(SmallBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 184, Short.MAX_VALUE))
        );

        ScrollBody.setViewportView(Body);

        Fondo.add(ScrollBody, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, -1));

        Footer.setForeground(new java.awt.Color(0, 0, 0));
        Footer.setMaximumSize(new java.awt.Dimension(430, 250));
        Footer.setMinimumSize(new java.awt.Dimension(430, 250));
        Footer.setOpaque(false);
        Footer.setPreferredSize(new java.awt.Dimension(430, 250));

        CrearBtn.setFont(new java.awt.Font("Raleway Medium", 0, 24)); // NOI18N
        CrearBtn.setForeground(new java.awt.Color(16, 24, 32));
        CrearBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CrearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Boton_On.png"))); // NOI18N
        CrearBtn.setText("Crear");
        CrearBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CrearBtn.setMaximumSize(new java.awt.Dimension(430, 72));
        CrearBtn.setMinimumSize(new java.awt.Dimension(430, 72));
        CrearBtn.setPreferredSize(new java.awt.Dimension(430, 72));
        Footer.add(CrearBtn);

        EjecutarBtn.setFont(new java.awt.Font("Raleway Medium", 0, 24)); // NOI18N
        EjecutarBtn.setForeground(new java.awt.Color(16, 24, 32));
        EjecutarBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EjecutarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Boton_On.png"))); // NOI18N
        EjecutarBtn.setText("Ejecutar");
        EjecutarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EjecutarBtn.setMaximumSize(new java.awt.Dimension(430, 72));
        EjecutarBtn.setMinimumSize(new java.awt.Dimension(430, 72));
        EjecutarBtn.setPreferredSize(new java.awt.Dimension(430, 72));
        Footer.add(EjecutarBtn);

        GuardarBtn.setFont(new java.awt.Font("Raleway Medium", 0, 24)); // NOI18N
        GuardarBtn.setForeground(new java.awt.Color(16, 24, 32));
        GuardarBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GuardarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Boton_On.png"))); // NOI18N
        GuardarBtn.setText("Guardar");
        GuardarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        GuardarBtn.setMaximumSize(new java.awt.Dimension(430, 72));
        GuardarBtn.setMinimumSize(new java.awt.Dimension(430, 72));
        GuardarBtn.setPreferredSize(new java.awt.Dimension(430, 72));
        Footer.add(GuardarBtn);

        Debug.setFont(new java.awt.Font("Raleway", 0, 8)); // NOI18N
        Footer.add(Debug);

        Fondo.add(Footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, -1, -1));

        FondoBase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FondoBase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Fondo.png"))); // NOI18N
        FondoBase.setMaximumSize(new java.awt.Dimension(430, 932));
        FondoBase.setMinimumSize(new java.awt.Dimension(430, 932));
        FondoBase.setPreferredSize(new java.awt.Dimension(430, 932));
        Fondo.add(FondoBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Fondo.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
         
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Body;
    private javax.swing.JLabel BtnInfOff;
    private javax.swing.JLabel BtnMasOff1;
    private javax.swing.JLabel CrearBtn;
    private javax.swing.JLabel Debug;
    private javax.swing.JComboBox<String> Desplegable1;
    private javax.swing.JLabel EjecutarBtn;
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel FondoBase;
    private javax.swing.JLabel FondoBody;
    private javax.swing.JLabel FondoInfo;
    private javax.swing.JPanel Footer;
    private javax.swing.JLabel GuardarBtn;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel InfoPanel;
    private javax.swing.JScrollPane ScrollBody;
    private javax.swing.JPanel SmallBody;
    private javax.swing.JLabel Subtitulo;
    private javax.swing.JLabel Subtitulo2;
    private javax.swing.JLabel Titulo;
    // End of variables declaration//GEN-END:variables
}
