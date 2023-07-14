import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;

public class OperationFrame extends JFrame
{
    Font mainFont = new Font("Times New Roman", Font.BOLD, 16);
    Color buttonColor = new Color(5,200,105);

    JLabel lb_resCarnet, lb_resNombre, lb_resCurso, lb_resCorreo;
    JTextField tf_buscar;

    Conexion conexion = new Conexion();

    public void inicializar(){
        /** Panel de la imagen */
        Icon logo = new ImageIcon(OperationFrame.class.getResource("/images/escuela.jpg"));
        JLabel lb_imagen = new JLabel(logo);

        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new GridLayout(1,1,10,10));
        imgPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        imgPanel.add(lb_imagen);

        /** Panel del input y botones  */
        JLabel lb_buscar = new JLabel("Carnet del estudiante:");
        lb_buscar.setFont(mainFont);

        tf_buscar = new JTextField();
        tf_buscar.setFont(mainFont);

            //CRUD = Create, Read, Update, Delete

        JButton btn_buscar = new JButton("Buscar");
        btn_buscar.setBackground(buttonColor);
        btn_buscar.setFont(mainFont);
        btn_buscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarDatos();
            }
        });

        JButton btn_nuevo = new JButton("Nuevo");
        btn_nuevo.setBackground(buttonColor);
        btn_nuevo.setFont(mainFont);
        btn_nuevo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Formulario form = new Formulario();
                form.setModal(true);
                form.setVisible(true);
                crearNuevoRegistro(Integer.parseInt(form.id), form.nombre, Integer.parseInt(form.clave), form.curso, form.correo);
            }   
        });

        JButton btn_editar = new JButton("Editar");
        btn_editar.setBackground(buttonColor);
        btn_editar.setFont(mainFont);
        btn_editar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               editarRegistro();
            }   
        });

        JButton btn_eliminar = new JButton("Eliminar");
        btn_eliminar.setBackground(buttonColor);
        btn_eliminar.setFont(mainFont);
        btn_eliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarRegistro();
            }           
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,3,5,5));
        buttonPanel.add(btn_nuevo);
        buttonPanel.add(btn_editar);
        buttonPanel.add(btn_eliminar);

        JPanel crudPanel = new JPanel();
        crudPanel.setLayout(new GridLayout(4, 1, 10, 10));
        crudPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        crudPanel.add(lb_buscar);
        crudPanel.add(tf_buscar);
        crudPanel.add(btn_buscar);
        crudPanel.add(buttonPanel);

        /** Panel del resultado de busquedas */
            //Etiquetas de cabecera
        JLabel lb_cabecera1 = new JLabel("Nombre y Apellido");
        JLabel lb_cabecera2 = new JLabel("Edad");
        JLabel lb_cabecera3 = new JLabel("Curso");
        JLabel lb_cabecera4 = new JLabel("Correo");
        
            //Fila de resultado
        lb_resCarnet = new JLabel("");
        lb_resNombre = new JLabel("");
        lb_resCorreo = new JLabel("");
        lb_resCurso = new JLabel("");

        JPanel resPanel = new JPanel();
        resPanel.setLayout(new GridLayout(0,4,3,3));
        resPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resPanel.add(lb_cabecera1);
        resPanel.add(lb_cabecera2);
        resPanel.add(lb_cabecera3);
        resPanel.add(lb_cabecera4);
        resPanel.add(lb_resCarnet);
        resPanel.add(lb_resNombre);
        resPanel.add(lb_resCurso);
        resPanel.add(lb_resCorreo);

        Component [] etiquetas = resPanel.getComponents();    
        for(int i = 0; i < etiquetas.length;  i++){
            JLabel aux = (JLabel) etiquetas[i];
            aux.setFont(mainFont);
            aux.setOpaque(true);
            aux.setHorizontalAlignment((int) CENTER_ALIGNMENT);
            aux.setBorder(BorderFactory.createLineBorder(Color.black));
            if(i < 4)
                aux.setBackground(new Color(238, 232, 170));
        }    

        JButton btn_reporte = new JButton("Reporte");
        btn_reporte.setFont(mainFont);
        btn_reporte.setBackground(buttonColor);
        resPanel.add(btn_reporte);
        btn_reporte.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte();
            }           
        });

        /** Esqueleto de la ventana */
        setTitle("CRUD Escuela");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
            //Agregar componentes
        add(imgPanel, BorderLayout.NORTH);
        add(crudPanel, BorderLayout.CENTER);
        add(resPanel, BorderLayout.SOUTH);
    }

    /* Metodos CRUD */
    private void buscarDatos(){
        Connection cn = null;    
		PreparedStatement pstm = null;
		ResultSet rs = null;

        String carnet_aux = tf_buscar.getText();
        int carnet = Integer.parseInt(carnet_aux);

		try{
			cn = conexion.conectar();

            String sql = "SELECT nombre, edad, curso, correo FROM alumnos WHERE id = ?";
			pstm = (PreparedStatement) cn.prepareStatement(sql);
			pstm.setInt(1, carnet);

			rs = pstm.executeQuery();

			if(rs.next()){
				lb_resCarnet.setText(rs.getString("nombre"));
                lb_resNombre.setText(String.valueOf(rs.getInt("edad")));
                lb_resCurso.setText(rs.getString("curso"));
                lb_resCorreo.setText(rs.getString("correo"));              
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    private void crearNuevoRegistro(int id, String nombre, int edad, String curso, String correo){
        Connection cn = null;
		PreparedStatement pstm = null;
		try{
			cn = conexion.conectar();

            String sql = "INSERT INTO alumnos VALUES(?, ?, ?, ?, ?)";
			pstm = (PreparedStatement) cn.prepareStatement(sql);
			pstm.setInt(1, id);
			pstm.setString(2, nombre);		 
            pstm.setInt(3, edad); 
            pstm.setString(4, curso);
	        pstm.setString(5, correo); 
			pstm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "REGISTRO COMPLETADO");
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    //En proceso ...
    private void editarRegistro(){
        Connection cn = null;
		PreparedStatement pstm = null;
		try{
			//String id = buscar.getText().trim();
			cn = conexion.conectar();
			pstm = (PreparedStatement) cn.prepareStatement("UPDATE alumnos SET nombre = ?, clave = ? WHERE id = +id");
			//pstm.setString(1, nombre.getText().trim());
			//pstm.setString(2, clave.getText());
			pstm.executeUpdate();
			JOptionPane.showMessageDialog(null, "Modificacion exitosa");
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    private void eliminarRegistro(){
        Connection cn = null;
		PreparedStatement pstm = null;

        String carnet_aux = tf_buscar.getText();
        int carnet = Integer.parseInt(carnet_aux);
		try{
			cn = conexion.conectar();

            String sql = "DELETE FROM alumnos WHERE id = ?";
			pstm = (PreparedStatement) cn.prepareStatement(sql);
			pstm.setInt(1, carnet);
			pstm.executeUpdate();

			lb_resCarnet.setText(""); lb_resNombre.setText("");  
            lb_resCorreo.setText(""); lb_resCurso.setText("");
			JOptionPane.showMessageDialog(null, "Registro eliminado");
            tf_buscar.setText("");
        }catch(Exception e){
			e.printStackTrace();
		}
    }

    private void generarReporte(){
        Document documento = new Document();
		try{
			String ruta = System.getProperty("user.home");
			PdfWriter.getInstance(documento, new FileOutputStream(ruta+"/Desktop/reporte_ej.pdf"));//save in desktop
			
			Image imagen = Image.getInstance(OperationFrame.class.getResource("/images/escuela.jpg"));
			imagen.scaleToFit(301,184);
			imagen.setAlignment(Chunk.ALIGN_CENTER);
			
			Paragraph parrafo = new Paragraph();
			parrafo.setAlignment(Paragraph.ALIGN_CENTER);
			parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.GREEN));
			parrafo.add("NOMINA DE ESTUDIANTES DE LA ESCUELA:) \n\n");
			
			documento.open(); //we will open the document to insert data in the pdf
			
			documento.add(imagen); documento.add(parrafo);
			
			PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Carnet de Identidad");
			tabla.addCell("Nombre completo");
			tabla.addCell("Edad");
			tabla.addCell("Correo");
            tabla.addCell("Curso");
			
			Connection cn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			try{
				cn = conexion.conectar();

                String sql = "SELECT * FROM alumnos";
				pstm = (PreparedStatement) cn.prepareStatement(sql);

				rs = pstm.executeQuery();
				while(rs.next()){
                    tabla.addCell(String.valueOf(rs.getInt(1)));
					tabla.addCell(rs.getString(2));
					tabla.addCell(String.valueOf(rs.getInt(3)));
					tabla.addCell(rs.getString(4));
                    tabla.addCell(rs.getString(5));
				}
				documento.add(tabla);
			}catch(Exception es){
				es.printStackTrace();
			}
			documento.close();
			JOptionPane.showMessageDialog(null, "Reporte Creado!!!");
		}catch(Exception e){
			e.printStackTrace();
		}
    }
}
