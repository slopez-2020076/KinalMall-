package org.santiagolopez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.santiagolopez.bean.Horario;
import org.santiagolopez.db.Conexion;
import org.santiagolopez.system.Principal;


public class HorarioController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Horario> listaHorario;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private TextField txtCodigoHorario;
    @FXML private TextField txtHorarioEntrada;
    @FXML private TextField txtHorarioSalida;
    @FXML private TextField txtLunes;
    @FXML private TextField txtMartes;
    @FXML private TextField txtMiercoles;
    @FXML private TextField txtJueves;
    @FXML private TextField txtViernes;
    @FXML private TableView tblHorario;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colHorarioEntrada; 
    @FXML private TableColumn colHorarioSalida; 
    @FXML private TableColumn colLunes;
    @FXML private TableColumn colMartes;
    @FXML private TableColumn colMiercoles;
    @FXML private TableColumn colJueves;
    @FXML private TableColumn colViernes;
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/santiagolopez/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/santiagolopez/images/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
                        
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/santiagolopez/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/santiagolopez/images/559481.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;  
        }
    }
    
    
    public void guardar(){
        Horario registro = new Horario();
        registro.setHorarioEntrada(txtHorarioEntrada.getText());
        registro.setHorarioSalida(txtHorarioSalida.getText());
        registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
        registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
        registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
        registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
        registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
        if(txtHorarioEntrada.getText().equals("")|| txtHorarioSalida.getText().equals("")|| txtLunes.getText().equals("")||
                txtMartes.getText().equals("")|| txtMiercoles.getText().equals("")||
                txtJueves.getText().equals("")|| txtViernes.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Existen campos vacios");
        }else{
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarHorario(?,?,?,?,?,?,?)}");
                procedimiento.setString(1, registro.getHorarioEntrada());
                procedimiento.setString(2, registro.getHorarioSalida());
                procedimiento.setBoolean(3, registro.isLunes());
                procedimiento.setBoolean(4, registro.isMartes());
                procedimiento.setBoolean(5, registro.isMiercoles());
                procedimiento.setBoolean(6, registro.isJueves());
                procedimiento.setBoolean(7, registro.isViernes());
                procedimiento.execute();
                listaHorario.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }       
    }
    
    
    public void eliminar(){
        switch (tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/santiagolopez/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/santiagolopez/images/559481.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            
            default: 
                if(tblHorario.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminar el registro?", "Eliminar Horario", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarHorario(?)}");
                            procedimiento.setInt(1, ((Horario)tblHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
                            procedimiento.execute();
                            listaHorario.remove(tblHorario.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento.");
                }
        }
    }
    
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblHorario.getSelectionModel().getSelectedItem()!=null){
                   btnNuevo.setDisable(true);
                   btnEliminar.setDisable(true);
                   btnEditar.setText("Actualizar");
                   btnReporte.setText("Cancelar");
                   imgEditar.setImage(new Image("/org/santiagolopez/images/Refresh.png"));
                   imgReporte.setImage(new Image("/org/santiagolopez/images/cancelar.png"));
                   activarControles();
                   tipoDeOperacion = operaciones.ACTUALIZAR;
                  
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                }
                break;
            
            case ACTUALIZAR:
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/santiagolopez/images/Editar.png"));
                imgReporte.setImage(new Image("/org/santiagolopez/images/Report.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarHorario(?,?,?,?,?,?,?,?)}");
            Horario registro = (Horario)tblHorario.getSelectionModel().getSelectedItem();
            registro.setHorarioEntrada(txtHorarioEntrada.getText());
            registro.setHorarioSalida(txtHorarioSalida.getText());
            registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
            registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
            registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
            registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
            registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
            procedimiento.setInt(1, registro.getCodigoHorario());
            procedimiento.setString(2, registro.getHorarioEntrada());
            procedimiento.setString(3, registro.getHorarioSalida());
            procedimiento.setBoolean(4, registro.isLunes());
            procedimiento.setBoolean(5, registro.isMartes());
            procedimiento.setBoolean(6, registro.isMiercoles());
            procedimiento.setBoolean(7, registro.isJueves());
            procedimiento.setBoolean(8, registro.isViernes());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgNuevo.setImage(new Image("/org/santiagolopez/images/Editar.png"));
                imgReporte.setImage(new Image("/org/santiagolopez/images/Report.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    
    
    public void desactivarControles(){
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(false);
        txtHorarioSalida.setEditable(false);
        txtLunes.setEditable(false);
        txtMartes.setEditable(false);
        txtMiercoles.setEditable(false);
        txtJueves.setEditable(false);
        txtViernes.setEditable(false);
    }
    
    
    public void activarControles(){
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(true);
        txtHorarioSalida.setEditable(true);
        txtLunes.setEditable(true);
        txtMartes.setEditable(true);
        txtMiercoles.setEditable(true);
        txtJueves.setEditable(true);
        txtViernes.setEditable(true);
    }
    
    
    public void limpiarControles(){
        txtCodigoHorario.clear();
        txtHorarioEntrada.clear();
        txtHorarioSalida.clear();
        txtLunes.clear();
        txtMartes.clear();
        txtMiercoles.clear();
        txtJueves.clear();
        txtViernes.clear();
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }
    
    public void cargarDatos(){
        tblHorario.setItems(getHorario());
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Horario,Integer>("codigoHorario"));
        colHorarioEntrada.setCellValueFactory(new PropertyValueFactory<Horario, String>("horarioEntrada"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<Horario, String>("horarioSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("viernes"));
    }
    
    public ObservableList<Horario> getHorario(){
       ArrayList<Horario> lista = new ArrayList<Horario>();
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorario()}");
           ResultSet resultado = procedimiento.executeQuery();
           while(resultado.next()){
               lista.add(new Horario(resultado.getInt("codigoHorario"), resultado.getString("horarioEntrada"),
                                     resultado.getString("horarioSalida"), resultado.getBoolean("lunes"),
                                     resultado.getBoolean("martes"), resultado.getBoolean("miercoles"), 
                                     resultado.getBoolean("jueves"), resultado.getBoolean("viernes")));
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       return listaHorario = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if(tblHorario.getSelectionModel().getSelectedItem()!=null){
            txtCodigoHorario.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            txtHorarioEntrada.setText(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getHorarioEntrada());
            txtHorarioSalida.setText(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getHorarioSalida());
            txtLunes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isLunes()));
            txtMartes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isMartes()));
            txtMiercoles.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isMiercoles()));
            txtJueves.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isJueves()));
            txtViernes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isViernes()));           
        }
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }

}
