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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.santiagolopez.bean.Administracion;
import org.santiagolopez.bean.Clientes;
import org.santiagolopez.bean.Locales;
import org.santiagolopez.bean.TipoCliente;
import org.santiagolopez.db.Conexion;
import org.santiagolopez.report.GenerarReporte;
import org.santiagolopez.system.Principal;


public class ClienteController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Locales> listaLocales;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<TipoCliente> listaTipoCliente;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCliente;
    @FXML private TextField txtNombreCliente;
    @FXML private TextField txtApellidoCliente;
    @FXML private TextField txtTelCliente;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEmail;
    @FXML private ComboBox cmbCodigoLocal;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoTipoCliente;
    @FXML private TableView tblClientes;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colNombreCliente;
    @FXML private TableColumn colApellidoCliente;
    @FXML private TableColumn colTelefonoCliente;
    @FXML private TableColumn colDireccionCliente;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colCodigoLocal;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoTipoCliente;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

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
        Clientes registro = new Clientes();
        registro.setNombreCliente(txtNombreCliente.getText());
        registro.setApellidoCliente(txtApellidoCliente.getText());
        registro.setTelefonoCliente(txtTelCliente.getText());
        registro.setDireccionCliente(txtDireccion.getText());
        registro.setEmail(txtEmail.getText());
        registro.setCodigoLocal(((Locales)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoTipoCliente(((TipoCliente)cmbCodigoTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCliente(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreCliente());
            procedimiento.setString(2, registro.getApellidoCliente());
            procedimiento.setString(3, registro.getTelefonoCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.setString(5, registro.getEmail());
            procedimiento.setInt(6, registro.getCodigoLocal());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.setInt(8, registro.getCodigoTipoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
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
                if(tblClientes.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta segiro de eliminar este registro?", "Eliminar CuentaPorPagar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento.");
                }
        }
    }
    
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblClientes.getSelectionModel().getSelectedItems()!=null){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/santiagolopez/images/Refresh.png"));
                    imgReporte.setImage(new Image("/org/santiagolopez/images/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debes Seleccionar un Elemento.");
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCliente(?,?,?,?,?,?,?,?,?)}");
            Clientes registro = (Clientes)tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombreCliente(txtNombreCliente.getText());
            registro.setApellidoCliente(txtApellidoCliente.getText());
            registro.setTelefonoCliente(txtTelCliente.getText());
            registro.setDireccionCliente(txtDireccion.getText());
            registro.setEmail(txtEmail.getText());
            registro.setCodigoLocal(((Locales)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
            registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setCodigoTipoCliente(((TipoCliente)cmbCodigoTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getTelefonoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getEmail());
            procedimiento.setInt(7, registro.getCodigoLocal());
            procedimiento.setInt(8, registro.getCodigoAdministracion());
            procedimiento.setInt(9, registro.getCodigoTipoCliente());
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
        int codCliente = ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente();
        parametros.put("codCliente", codCliente);
        GenerarReporte.mostrarReporte("ReporteClienteIndividual.jasper", "Reporte Cliente", parametros);
    }
            
    
    
    public void desactivarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtTelCliente.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoTipoCliente.setDisable(true);
    }
    
    
    public void activarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtTelCliente.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
    }
    
    
    public void activarCombo(){
        cmbCodigoLocal.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoTipoCliente.setDisable(false); 
    }
    
    public void limpiarControles(){
        txtCodigoCliente.clear();
        txtNombreCliente.clear();
        txtApellidoCliente.clear();
        txtTelCliente.clear();
        txtDireccion.clear();
        txtEmail.clear();
        cmbCodigoLocal.getSelectionModel().clearSelection();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoTipoCliente.getSelectionModel().clearSelection();
    }
    
    
     @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();  
    }
    
    
    
    public void cargarDatos(){
        tblClientes.setItems(getCliente());
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Clientes, String>("email"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales, Integer>("codigoLocal"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("codigoAdministracion"));
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoCliente, Integer>("codigoTipoCliente"));
        cmbCodigoLocal.setItems(getLocales());
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoTipoCliente.setItems(getTipoCliente());
    }

    
    public ObservableList<Locales> getLocales(){
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocal()}");
           ResultSet resultado = procedimiento.executeQuery();
           while(resultado.next()){
               lista.add(new Locales(resultado.getInt("codigoLocal"), resultado.getDouble("saldoFavor"), 
                                        resultado.getDouble("saldoContra"), resultado.getInt("mesesPendientes"),
                                        resultado.getBoolean("disponibilidad"), resultado.getDouble("valorLocal"), 
                                        resultado.getDouble("valorAdministracion")));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaLocales = FXCollections.observableArrayList(lista);
    }
    
    
    public Locales buscarLocal(int codigoLocal){
        Locales resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocal(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Locales(registro.getInt("codigoLocal"),
                                            registro.getDouble("saldoFavor"), 
                                            registro.getDouble("saldoContra"),
                                            registro.getInt("mesesPendientes"),
                                            registro.getBoolean("disponibilidad"), 
                                            registro.getDouble("valorLocal"),
                                            registro.getDouble("valorAdministracion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            
        return resultado;
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
    
    
    public ObservableList<TipoCliente> getTipoCliente(){
        ArrayList<TipoCliente> lista = new ArrayList<TipoCliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoCliente(resultado.getInt("codigoTipoCliente"),
                            resultado.getString("descripcion")));  
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoCliente = FXCollections.observableArrayList(lista);
    }
    
    
    public TipoCliente buscarTipoCliente(int codigoTipoCliente){
        TipoCliente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoCliente(?)}");
            procedimiento.setInt(1, codigoTipoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TipoCliente(registro.getInt("codigoTipoCliente"),
                                                registro.getString("descripcion"));
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    public ObservableList<Clientes> getCliente(){
        ArrayList<Clientes> lista = new ArrayList<Clientes>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Clientes(resultado.getInt("codigoCliente"), resultado.getString("nombreCliente"), resultado.getString("apellidoCliente"),
                                       resultado.getString("telefonoCliente"), resultado.getString("direccionCliente"), resultado.getString("email"), 
                                       resultado.getInt("codigoLocal"), resultado.getInt("codigoAdministracion"), resultado.getInt("codigoTipoCliente")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableArrayList(lista);
    }
    
    
    public void seleccionarElemento(){
        if(tblClientes.getSelectionModel().getSelectedItem()!=null){
           txtCodigoCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
           txtNombreCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
           txtApellidoCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
           txtDireccion.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
           txtTelCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
           txtEmail.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getEmail());
           cmbCodigoLocal.getSelectionModel().select(buscarLocal(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoLocal()));
           cmbCodigoAdministracion.getSelectionModel().select(buscarAdministrador(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
           cmbCodigoTipoCliente.getSelectionModel().select(buscarTipoCliente(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
        
        }
    }
    
    public void ventanaTipoCliente(){
        escenarioPrincipal.ventanaTipoCliente();
    }
}
