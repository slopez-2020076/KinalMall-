
package org.santiagolopez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.santiagolopez.system.Principal;

/**
 *
 * @author Santiago
 */
public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos(); 
    }
        
    public void cargarDatos(){
        
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    } 
    
    
    public void ventanaAdministracion(){
        escenarioPrincipal.ventanaAdministracion();
    }
    
    
    public void ventanaTipoCliente(){
        escenarioPrincipal.ventanaTipoCliente();
    }
    
    
    public void ventanaLocales(){
        escenarioPrincipal.ventanaLocales();
    }
    
    
    public void ventanaDepartamento(){
        escenarioPrincipal.ventanaDepartamento();
    }
    
    
    public void ventanaHorario(){
        escenarioPrincipal.ventanaHorario();
    }
    
    
    public void ventanaProveedor(){
        escenarioPrincipal.ventanaProveedor();
    }
    
    
    public void ventanaCargo(){
        escenarioPrincipal.ventanaCargo();
    }
   
    
    public void ventanaUsuarios(){
        escenarioPrincipal.ventanaUsuarios();
    }
    
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
}
