
package org.santiagolopez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.santiagolopez.bean.Administracion;
import org.santiagolopez.db.Conexion;
import org.santiagolopez.report.GenerarReporte;
import org.santiagolopez.system.Principal;

public class AdministracionController implements Initializable {
    private Principal escenarioPrincipal; 
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Administracion> listaAdministracion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoAdministracion;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TableView tblAdministracion;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
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
        Administracion registro = new Administracion();
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        if(txtDireccion.getText().equals("")|| txtTelefono.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Existen campos vacios"); 
        }else{    
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarAdministrador(?,?)}");
                procedimiento.setString(1, registro.getDireccion());
                procedimiento.setString(2, registro.getTelefono());
                procedimiento.execute();
                listaAdministracion.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
   
   
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/santiagolopez/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/santiagolopez/images/559481.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default: 
                if(tblAdministracion.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Administrador", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarAdministrador(?)}"); 
                            procedimiento.setInt(1, ((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
                            procedimiento.execute();
                            listaAdministracion.remove(tblAdministracion.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }   
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                }
        }
    }
    
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblAdministracion.getSelectionModel().getSelectedItem()!=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarAdministrador(?,?,?)}");
            Administracion registro = (Administracion)tblAdministracion.getSelectionModel().getSelectedItem();
            registro.setDireccion(txtDireccion.getText());
            registro.setTelefono(txtTelefono.getText());
            procedimiento.setInt(1, registro.getCodigoAdministracion());
            procedimiento.setString(2, registro.getDireccion());
            procedimiento.setString(3, registro.getTelefono());
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
                imgEditar.setImage(new Image("/org/santiagolopez/images/Editar.png"));
                imgReporte.setImage(new Image("/org/santiagolopez/images/Report.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
                
            case NINGUNO:
                imprimirReporte();
                break;
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoAdministracion", null);
        GenerarReporte.mostrarReporte("ReporteAdministracion.jasper", "Reporte Datos Administracion", parametros);
    }
    
    public void desactivarControles(){
        txtCodigoAdministracion.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
    }
    
    
    public void activarControles(){
       txtCodigoAdministracion.setEditable(false);
       txtDireccion.setEditable(true);
       txtTelefono.setEditable(true); 
    }
    
    
    public void limpiarControles(){
        txtCodigoAdministracion.clear();
        txtDireccion.clear();
        txtTelefono.clear();
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
        tblAdministracion.setItems(getAdministracion());
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Administracion,String>("Direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Administracion,String>("Telefono"));
    }
    
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministrador()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"), 
                            resultado.getString("Direccion"), resultado.getString("Telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
  
    
    public void seleccionarElemento(){
        if(tblAdministracion.getSelectionModel().getSelectedItem()!=null){
            txtCodigoAdministracion.setText(String.valueOf(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            txtDireccion.setText(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getDireccion());
            txtTelefono.setText(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getTelefono());
        }
        
    }
    
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
 
  
      
