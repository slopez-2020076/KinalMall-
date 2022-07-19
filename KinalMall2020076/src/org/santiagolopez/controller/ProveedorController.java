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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.santiagolopez.bean.Administracion;
import org.santiagolopez.bean.Proveedor;
import org.santiagolopez.db.Conexion;
import org.santiagolopez.system.Principal;


public class ProveedorController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Proveedor> listaProveedor;
    private ObservableList<Administracion> listaAdministracion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private TextField txtCodigoProveedor;
    @FXML private TextField txtNITProveedor;
    @FXML private TextField txtServicioPrestado;
    @FXML private TextField txtTelefonoProveedor;
    @FXML private TextField txtDireccionProveedor;
    @FXML private TextField txtSaldoFavor;
    @FXML private TextField txtSaldoContra;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private TableView tblProveedor;
    @FXML private TableColumn colCodigoProveedor;
    @FXML private TableColumn colNITProveedor;
    @FXML private TableColumn colServicioPrestado;
    @FXML private TableColumn colTelefonoProveedor;
    @FXML private TableColumn colDireccionProveedor;
    @FXML private TableColumn colSaldoFavor;
    @FXML private TableColumn colSaldoContra;
    @FXML private TableColumn colCodigoAdministracion; 
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                activarCombo();
                limpiarControles();
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
        }
    }
    
    
    public void guardar(){
        Proveedor registro = new Proveedor();
        registro.setNitProveedor(txtNITProveedor.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
        registro.setDireccionProveedor(txtDireccionProveedor.getText());
        registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
        registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedor(?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNitProveedor());
            procedimiento.setString(2, registro.getServicioPrestado());
            procedimiento.setString(3, registro.getTelefonoProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setDouble(5, registro.getSaldoFavor());
            procedimiento.setDouble(6, registro.getSaldoContra());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaProveedor.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void eliminar(){
        switch(tipoDeOperacion){
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
                if(tblProveedor.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta segiro de eliminar este registro?", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                           procedimiento.setInt(1, ((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                           procedimiento.execute();
                           listaProveedor.remove(tblProveedor.getSelectionModel().getSelectedIndex());
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
                if(tblProveedor.getSelectionModel().getSelectedItem()!=null){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/santiagolopez/images/Refresh.png"));
                    imgReporte.setImage(new Image("/org/santiagolopez/images/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR; 
                }else{
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un registro");
                }
                break;
                
            case ACTUALIZAR:
                actualizar();
                activarControles();
                limpiarControles();
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProveedor(?,?,?,?,?,?,?,?)}");
            Proveedor registro = (Proveedor)tblProveedor.getSelectionModel().getSelectedItem();
            registro.setNitProveedor(txtNITProveedor.getText());
            registro.setServicioPrestado(txtServicioPrestado.getText());
            registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
            registro.setDireccionProveedor(txtDireccionProveedor.getText());
            registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
            registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
            registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNitProveedor());
            procedimiento.setString(3, registro.getServicioPrestado());
            procedimiento.setString(4, registro.getTelefonoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setDouble(6, registro.getSaldoFavor());
            procedimiento.setDouble(7, registro.getSaldoContra());
            procedimiento.setInt(8, registro.getCodigoAdministracion());
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
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtServicioPrestado.setEditable(false);
        txtTelefonoProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
    }
    
    
    public void activarControles(){
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
    }   
        
    
    public void activarCombo(){
        cmbCodigoAdministracion.setDisable(false);
    }  
    
    public void limpiarControles(){
        txtCodigoProveedor.clear();
        txtNITProveedor.clear();
        txtServicioPrestado.clear();
        txtTelefonoProveedor.clear();
        txtDireccionProveedor.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
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
        tblProveedor.setItems(getProveedor());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor, Integer>("codigoProveedor"));
        colNITProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("nitProveedor"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("servicioPrestado"));
        colTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("telefonoProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("direccionProveedor"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Proveedor, Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Proveedor, Double>("saldoContra"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("codigoAdministracion"));
        cmbCodigoAdministracion.setItems(getAdministracion());
    }
    
    
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministrador()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"), 
                            resultado.getString("direccion"), resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    
    public Administracion buscarAdministrador(int codigoAdministracion){
        Administracion resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministrador(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Administracion(registro.getInt("codigoAdministracion"), 
                                               registro.getString("direccion"),
                                               registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    public ObservableList<Proveedor> getProveedor(){
        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedor(resultado.getInt("codigoProveedor"), resultado.getString("nitProveedor"), 
                                        resultado.getString("servicioPrestado"), resultado.getString("telefonoProveedor"), 
                                        resultado.getString("direccionProveedor"), resultado.getDouble("saldoFavor"), 
                                        resultado.getDouble("saldoContra"), resultado.getInt("codigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        } 
        return listaProveedor = FXCollections.observableArrayList(lista);
    }
    
    
    public void seleccionarElemento(){
        if(tblProveedor.getSelectionModel().getSelectedItem()!=null){
            txtCodigoProveedor.setText(String.valueOf(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNITProveedor.setText(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getNitProveedor());
            txtServicioPrestado.setText(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getServicioPrestado());
            txtTelefonoProveedor.setText(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getTelefonoProveedor());
            txtDireccionProveedor.setText(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtSaldoFavor.setText(String.valueOf(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getSaldoContra()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministrador(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        }
    }
    
    
    public void ventanaCuentaPorPagar(){
        escenarioPrincipal.ventanaCuentaPorPagar();
    }
    
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
