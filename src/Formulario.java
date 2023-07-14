import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Formulario extends JDialog
{
    JTextField tf_carnet, tf_nombre, tf_curso, tf_edad, tf_correo;
    Font mainFont = new Font("Tahoma", Font.BOLD, 15);
    JButton btn_guardar;
    protected String id, nombre, curso, clave, correo;

    public Formulario(){
        /** Panel de la imagen */
        Icon imagen = new ImageIcon(Formulario.class.getResource("/images/estudiantes.png"));       
        JLabel lb_imagen = new JLabel(imagen);

        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new GridLayout(1,1,5,5));
        imgPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        imgPanel.setBackground(Color.white);
        imgPanel.add(lb_imagen);

        /** Panel del formulario */
        JLabel lb_titulo = new JLabel("FORMULARIO DE ALUMNOS");
        lb_titulo.setFont(new Font("Algerian", Font.BOLD, 18));
        lb_titulo.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        JLabel lb_carnet = new JLabel("Carnet:");
        lb_carnet.setFont(mainFont);

        tf_carnet = new JTextField();
        tf_carnet.setFont(mainFont);

        JLabel lb_nombre = new JLabel("Nombre y Apellido:");
        lb_nombre.setFont(mainFont);

        tf_nombre = new JTextField();
        tf_nombre.setFont(mainFont);

        JLabel lb_correo = new JLabel("Correo:");
        lb_correo.setFont(mainFont);

        tf_correo = new JTextField();
        tf_correo.setFont(mainFont);

        JLabel lb_edad = new JLabel("Edad:");
        lb_edad.setFont(mainFont);

        tf_edad = new JTextField();
        tf_edad.setFont(mainFont);

        JLabel lb_curso = new JLabel("Curso:");
        lb_curso.setFont(mainFont);

        tf_curso = new JTextField();
        tf_curso.setFont(mainFont);

        btn_guardar = new JButton("Guardar");
        btn_guardar.setFont(mainFont);
        btn_guardar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {      
                id = tf_carnet.getText(); nombre = tf_nombre.getText(); clave = tf_edad.getText(); curso = tf_curso.getText(); correo = tf_correo.getText();
                JOptionPane.showMessageDialog(null,"Registro creado con exito");
                dispose();
            }        
        });

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(12,1,5,5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(238, 232, 170));
        formPanel.add(lb_titulo);
        formPanel.add(lb_carnet);
        formPanel.add(tf_carnet);
        formPanel.add(lb_nombre);
        formPanel.add(tf_nombre);   
        formPanel.add(lb_edad);
        formPanel.add(tf_edad);
        formPanel.add(lb_curso);
        formPanel.add(tf_curso);
	    formPanel.add(lb_correo);
        formPanel.add(tf_correo);
        formPanel.add(btn_guardar);

        /** Esqueleto de la ventana Dialog*/
             //Agregar componentes
        add(imgPanel, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);

        setTitle("Formulario del Registro");
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

}