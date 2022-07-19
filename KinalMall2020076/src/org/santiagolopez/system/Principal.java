package org.santiagolopez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.santiagolopez.controller.AdministracionController;
import org.santiagolopez.controller.CargoController;
import org.santiagolopez.controller.ClienteController;
import org.santiagolopez.controller.CuentaPorCobrarController;
import org.santiagolopez.controller.CuentaPorPagarController;
import org.santiagolopez.controller.DepartamentoController;
import org.santiagolopez.controller.EmpleadoController;
import org.santiagolopez.controller.HorarioController;
import org.santiagolopez.controller.LocalesController;
import org.santiagolopez.controller.LoginController;
import org.santiagolopez.controller.MenuPrincipalController;
import org.santiagolopez.controller.ProgramadorController;
import org.santiagolopez.controller.ProveedorController;
import org.santiagolopez.controller.TipoClienteController;
import org.santiagolopez.controller.UsuarioController;

/**
 * Programador: Santiago Adolfo López Ramírez
 * Carné: 2020076
 * Código Técnico: IN5AV
 * Fecha de Creacion: 
 * 
 * Modificaciones:
 * 27-05-2021
 * 01-06-2021 
 * 02-06-2021
 * 03-06-2021
 * 04-06-2021
 * 09-06-2021
 * 10-06-2021
 * 11-06-2021
 * 13-06-2021
 * 16-06-2021
 * 17-06-2021
 * 18-06-2021
 * 26-06-2021
 * 27-06-2021
 * 02-07-2021
 * 03-07-2021
 * 04-07-2021
 * 07-07-2021
 * 10-07-2021
 * 11-07-2021
 */
public class Principal extends Application {
    private final String paqueteVista = "/org/santiagolopez/view/" ;
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall");
        ventanaLogin();
        escenarioPrincipal.show();
        
    }
    
    public void menuPrincipal(){
        try{
           MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 410, 370); 
           menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",716,400);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaAdministracion(){
        try{
            AdministracionController administrador = (AdministracionController)cambiarEscena("AdministracionView.fxml",850,410);
            administrador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoCliente(){
        try{
            TipoClienteController tipoClientes = (TipoClienteController)cambiarEscena("TipoClienteView.fxml",640,440);
            tipoClientes.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLocales(){
        try{
            LocalesController locales = (LocalesController)cambiarEscena("LocalesView.fxml",955, 452);
            locales.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaDepartamento(){
        try{
            DepartamentoController departamento = (DepartamentoController)cambiarEscena("DepartamentosView.fxml", 660, 435);
            departamento.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaHorario(){
        try{
            HorarioController horario = (HorarioController)cambiarEscena("HorarioView.fxml", 810, 410);
            horario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaProveedor(){
        try{
            ProveedorController proveedor = (ProveedorController)cambiarEscena("ProveedorView.fxml", 1010, 427);
            proveedor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCargo(){
        try{
            CargoController cargo = (CargoController)cambiarEscena("CargoView.fxml", 535, 410);
            cargo.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaClientes(){
        try{
            ClienteController clientes = (ClienteController)cambiarEscena("ClientesView.fxml", 1145, 425);
            clientes.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }      
    }
    
    public void ventanaEmpleado(){
        try{
            EmpleadoController empleado = (EmpleadoController)cambiarEscena("EmpleadoView.fxml", 1300, 440);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentaPorPagar(){
        try{
            CuentaPorPagarController cuentaPorPagar = (CuentaPorPagarController)cambiarEscena("CuentaPorPagarView.fxml", 1045, 460);
            cuentaPorPagar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaCuentaPorCobrar(){
        try{
            CuentaPorCobrarController cuentaPorCobrar = (CuentaPorCobrarController)cambiarEscena("CuentaPorCobrarView.fxml", 1100, 450);
            cuentaPorCobrar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuarios(){
        try{
            UsuarioController usuario = (UsuarioController)cambiarEscena("UsuarioView.fxml", 700, 395);
            usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaLogin(){
        try{
            LoginController loginController = (LoginController)cambiarEscena("LoginView.fxml", 625, 445);
            loginController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int largo) throws IOException {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(paqueteVista+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(paqueteVista+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,largo);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
        
    
}
