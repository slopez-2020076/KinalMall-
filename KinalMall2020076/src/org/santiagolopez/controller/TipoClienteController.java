
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
import org.santiagolopez.bean.TipoCliente;
import org.santiagolopez.db.Conexion;
import org.santiagolopez.system.Principal;


public class TipoClienteController implements Initializable {
    private Principal escenarioPrincipal; 
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoCliente> listaTipoCliente;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoTipoCliente;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblTipoCliente;
    @FXML private TableColumn colCodigoTipoCliente;
    @FXML private TableColumn colDescripcion;
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
        TipoCliente registro = new TipoCliente();
        registro.setDescripcion(txtDescripcion.getText());
        if(txtDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Existe Un dato Vacio");
        }else{
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoCliente(?)}");
                procedimiento.setString(1, registro.getDescripcion());
                procedimiento.execute();
                listaTipoCliente.add(registro);
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
                if(tblTipoCliente.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar TipoCliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoCliente(?)}");
                          procedimiento.setInt(1, ((TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
                          procedimiento.execute();
                          listaTipoCliente.remove(tblTipoCliente.getSelectionModel().getSelectedIndex());
                          limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();                           
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        
        }
    }
    
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblTipoCliente.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/santiagolopez/images/Refresh.png"));
                    imgReporte.setImage(new Image("/org/santiagolopez/images/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
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
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoCliente(?,?)}");
           TipoCliente registro = (TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem();
           registro.setDescripcion(txtDescripcion.getText());
           procedimiento.setInt(1, registro.getCodigoTipoCliente());
           procedimiento.setString(2, registro.getDescripcion());
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
       }
   }
    
    

    
    public void desactivarControles(){
        txtCodigoTipoCliente.setEditable(false);
        txtDescripcion.setEditable(false);
    }
    
    
    public void activarControles(){
        txtCodigoTipoCliente.setEditable(false);
        txtDescripcion.setEditable(true);
    }
    
    
    public void limpiarControles(){
        txtCodigoTipoCliente.clear();
        txtDescripcion.clear();
    }
    
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      cargarDatos();  
    }
    
    
    public void cargarDatos(){
        tblTipoCliente.setItems(getTipoCliente());
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoCliente,Integer>("codigoTipoCliente"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoCliente,String>("Descripcion"));
    }
    
    public ObservableList<TipoCliente> getTipoCliente(){
        ArrayList<TipoCliente> lista = new ArrayList<TipoCliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoCliente(resultado.getInt("codigoTipoCliente"),
                            resultado.getString("Descripcion")));  
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoCliente = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if(tblTipoCliente.getSelectionModel().getSelectedItem()!=null){
           txtCodigoTipoCliente.setText(String.valueOf(((TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
           txtDescripcion.setText(((TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem()).getDescripcion());
        }
    }
    
    public void ventanaCliente(){
        escenarioPrincipal.ventanaClientes();
    }
    
    public void menuPrincipal(){
            escenarioPrincipal.menuPrincipal();
    }
    
}



