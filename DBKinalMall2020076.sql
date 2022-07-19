Drop Database if exists DBKinalMall2020076;
Create Database DBKinalMall2020076;

Use DBKinalMall2020076;

/*
Programador: SantiagoAdolfoLópez Ramírez
Carné: 2020076
Código Técnico: IN5AV
Fecha de Creación:

Modificaciones:
	27-05-2021
  	01-06-2021 
  	02-06-2021
  	03-06-2021
  	04-06-2021
	09-06-2021
    10-06-2021
    15-06-2021
    16-06-2021
    17-062021
    18-06-2021
	26-06-2021
    27-06-2021
    02-07-2021
    03-07-2021
    04-07-2021
    07-07-2021
    10-07-2021
    11-07-2021
    04-08-2021
    05-08-2021
    06-08-2021
    07-08-2021
    08-08-2021
    09-08-2021
    10-08-2021
    11-08-2021
    12-08-2021
    13-08-2021
*/

Create table Administracion(
	codigoAdministracion int auto_increment not null,
    direccion varchar(45) not null,
    telefono varchar (8),
    primary key Pk_codigoAdministracion (codigoAdministracion)

);


Create table Departamentos(
	codigoDepartamento int auto_increment not null,
    nombreDepartamento varchar(45) not null,
    primary key PK_codigoDepartamento (codigoDepartamento)

);

Create table Cargos(
	codigoCargo int auto_increment not null,
    nombreCargo varchar (45) not null,
    primary key PK_codigoCargo (codigoCargo)

);


Create table Horarios(
	codigoHorario int auto_increment not null,
    horarioEntrada varchar (5) not null,
    horarioSalida varchar (5) not null,
    lunes boolean,
    martes boolean,
    miercoles boolean,
    jueves boolean,
    viernes boolean,
    primary key PK_codigoHorarios (codigoHorario)
    
);


Create table TipoCliente(
	codigoTipoCliente int auto_increment not null,
    descripcion varchar (45) not null,
    primary key PK_codigoTipoCliente (codigoTipoCliente)

);

Create table Locales(
	codigoLocal int auto_increment not null,
    saldoFavor double (11,2) default 0.0,
    saldoContra double (11,2) default 0.0,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal double (11,2) not null,
    valorAdministracion double (11,2) not null,
    primary key PK_codigoLocal (codigoLocal)

);

Create table Empleados(
	codigoEmpleado int auto_increment not null,
    nombreEmpleado varchar (45) not null, 
    apellidoEmpleado varchar (45) not null,
    correoElectronico varchar (45) not null,
    telefonoEmpleado varchar (8) not null,
    fechaContratacion date not null,
    sueldo double (11,2) not null,
    codigoDepartamento int not null,
    codigoCargo int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
    constraint FK_Empleados_Departamentos foreign key (codigoDepartamento)
		references Departamentos (codigoDepartamento),
    constraint FK_Empleados_Cargo foreign key (codigoCargo)
		references Cargos (codigoCargo),
	constraint FK_Empleados_Horarios foreign key (codigoHorario)
		references Horarios (codigoHorario),
	constraint FK_Empleados_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion)

);


Create table Clientes(
	codigoCliente int auto_increment not null,
    nombreCliente varchar (45) not null,
    apellidoCliente varchar (45) not null,
    telefonoCliente varchar (8) not null,
    direccionCliente varchar (60) not null,
    email varchar (45) not null,
    codigoLocal int not null,
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
    primary key PK_codigoCliente (codigoCliente),
	constraint FK_Clientes_Locales foreign key (codigoLocal)
		references Locales (codigoLocal),
	constraint FK_Clientes_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_Clientes_TipoCliente foreign key (codigoTipoCliente)
		references TipoCliente (codigoTipoCliente)
);


Create table Proveedores(
	codigoProveedor int auto_increment not null,
    NITproveedor varchar (45) not null,
    servicioPrestado varchar (45) not null,
    telefonoProveedor varchar (8) not null,
    direccionProveedor varchar (60) not null,
    saldoFavor double (11,2) not null,
    saldoContra double (11,2) not null,
    codigoAdministracion int not null,
    primary key PK_codigoProveedor (codigoProveedor),
    constraint FK_Proveedores_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion)
        
);


Create table CuentasPorPagar(
	codigoCuentaPorPagar int auto_increment not null,
    numeroFactura varchar (45) not null,
    fechaLimitePago date not null,
    estadoPago varchar (45) not null,
    valorNetoPago double (11,2) not null,
    codigoAdministracion int not null,
    codigoProveedor int not null,
    primary key PK_codigoCuentaPorPagar (codigoCuentaPorPagar),
    constraint FK_CuentasPorPagar_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_CuentasPorPagar_Proveedores foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
        
);


Create table CuentasPorCobrar (
	codigoCuentaPorCobrar int auto_increment not null,
    numeroFactura varchar (45) not null,
    anio varchar (4) not null,
    mes int (2) not null,
    valorNetoPago double (11,2) not null,
    estadoPago varchar (45) not null,
    codigoAdministracion int not null,
    codigoCliente int not null,
    codigoLocal int not null,
    primary key PK_codigoCuentaPorCobrar (codigoCuentaPorCobrar),
    constraint FK_CuentasPorCobrar_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_CuentasPorCobrar_Clientes foreign key (codigoCliente)
		references Clientes (codigoCliente),
	constraint FK_CuentasPorCobrar_Locales foreign key (codigoLocal)
		references Locales (codigoLocal)
    
);


Create table Usuarios(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar (100) not null, 
    apellidoUsuario varchar (100) not null, 
    usuarioLogin varchar (50) not null, 
    contrasena varchar (50) not null, 
    primary key PK_codigoUsuario (codigoUsuario)
);


Create table Login(
	usuarioMaster varchar(50), 
    passwordLogin varchar (50),
    primary key PK_usuarioMaster (usuarioMaster)
);





-- Procedimientos de Administracion
-- Agregar
Delimiter $$
	Create procedure sp_AgregarAdministrador(
		in direccion varchar(45),
        in telefono varchar (8))
	Begin
		insert into Administracion(direccion, telefono) values (direccion, telefono);
    End$$
Delimiter ;


-- Enlistar 
Delimiter $$
	Create procedure sp_ListarAdministrador()
		Begin
			Select
				A.codigoAdministracion,
                A.direccion,
				A.telefono
			From Administracion A;
        End$$
Delimiter ;


-- Buscar
Delimiter $$
	Create procedure sp_BuscarAdministrador(in codAdministracion int)
		Begin			
			Select
				A.codigoAdministracion,
                A.direccion,
				A.telefono
			From Administracion A
			where codigoAdministracion = codAdministracion;                
		End$$
Delimiter ;


-- Actualizar
Delimiter $$
	Create procedure sp_ActualizarAdministrador(
		in codAdministracion int,
        in direcc varchar(45),
        in telef varchar (8))
	Begin 
		Update Administracion 
			Set 
				direccion=direcc,
				telefono= telef
			Where codigoAdministracion = codAdministracion;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarAdministrador( in codAdministracion  int)
		Begin
			delete from Administracion where codigoAdministracion = codAdministracion;
        End$$
Delimiter ;





-- Procedimientos de Proveedores 
-- Agregar
Delimiter $$
	Create procedure sp_AgregarProveedor(
		in NITproveedor varchar (45) ,
		in servicioPrestado varchar (45),
		in telefonoProveedor varchar (8),
		in direccionProveedor varchar (60),
		in saldoFavor double (11,2),
		in saldoContra double (11,2),
		in codigoAdministracion int)
	Begin
		insert into Proveedores(NITproveedor, servicioPrestado, telefonoProveedor, direccionProveedor,
								saldoFavor, saldoContra, codigoAdministracion) values (NITproveedor, servicioPrestado,
                                telefonoProveedor, direccionProveedor, saldoFavor, saldoContra, codigoAdministracion);
    End$$
Delimiter ;


-- Enlistar 
Delimiter $$
	Create procedure sp_ListarProveedor()
		Begin
			Select
				P.codigoProveedor,
				P.NITproveedor,
				P.servicioPrestado,
				P.telefonoProveedor,
				P.direccionProveedor,
				P.saldoFavor,
				P.saldoContra,
                P.codigoAdministracion
			From Proveedores P;
        End$$
Delimiter ;


-- Buscar
Delimiter $$
	Create procedure sp_BuscarProveedor(in codProveedor int)
		Begin			
			Select
				P.codigoProveedor,
				P.NITproveedor,
				P.servicioPrestado,
				P.telefonoProveedor,
				P.direccionProveedor,
				P.saldoFavor,
				P.saldoContra,
                P.codigoAdministracion
			From Proveedores P
			Where codigoProveedor = codProveedor;                
		End$$
Delimiter ;


-- Actualizar
Delimiter $$
	Create procedure sp_ActualizarProveedor(
		in codProveedor int,
		in NITprovee varchar (45) ,
		in servicioPresta varchar (45),
		in telefonoProvee varchar (8),
		in direccionProvee varchar (60),
		in saldoFav double (11,2),
		in saldoCont double (11,2))
	Begin 
		Update Proveedores
			Set 
				NITproveedor = NITprovee,
				servicioPrestado = servicioPresta,
				telefonoProveedor = telefonoProvee,
				direccionProveedor = direccionProvee,
				saldoFavor = saldoFav,
				saldoContra = saldoCont
			Where codigoProveedor = codProveedor;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarProveedor( in codProveedor int)
		Begin
			delete from Proveedores where codigoProveedor = codProveedor;
        End$$
Delimiter ;





-- Procedimientos de CuentasPorPagar
-- Agregar
Delimiter $$
	Create procedure sp_AgregarCuentaPorPagar(
		in numeroFactura varchar (45),
		in fechaLimitePago date,
		in estadoPago varchar (45),
		in valorNetoPago double (11,2),
		in codigoAdministracion int,
		in codigoProveedor int)
	Begin
		insert into CuentasPorPagar(numeroFactura, fechaLimitePago, estadoPago, valorNetoPago,
									codigoAdministracion, codigoProveedor) values (numeroFactura, fechaLimitePago, 
                                    estadoPago, valorNetoPago, codigoAdministracion, codigoProveedor);
    End$$
Delimiter ;


-- Listar
Delimiter $$
	Create procedure sp_ListarCuentaPorPagar()
		Begin
			Select
				CP.codigoCuentaPorPagar,
				CP.numeroFactura,
				CP.fechaLimitePago,
				CP.estadoPago,
				CP.valorNetoPago,
				CP.codigoAdministracion,
				CP.codigoProveedor
			From CuentasPorPagar CP;
        End$$
Delimiter ;


-- Buscar
Delimiter $$
	Create procedure sp_BuscarCuentaPorPagar(in codCuentaPorPagar int)
		Begin			
			Select
				CP.codigoCuentaPorPagar,
				CP.numeroFactura,
				CP.fechaLimitePago,
				CP.estadoPago,
				CP.valorNetoPago,
				CP.codigoAdministracion,
				CP.codigoProveedor
			From CuentasPorPagar CP
			Where codigoCuentaPorPagar = codCuentaPorPagar;                
		End$$
Delimiter ;


-- Actualizar
Delimiter $$
	Create procedure sp_ActualizarCuentaPorPagar(
		in codCuentaPorPagar int,
        in numeroFact varchar (45),
		in fechaLimitePag date,
		in estadoPag varchar (45),
		in valorNetoPag double (11,2))
	Begin 
		Update CuentasPorPagar
			Set 
				numeroFactura = numeroFact,
				fechaLimitePago = fechaLimitePag,
				estadoPago = estadoPag,
				valorNetoPago = valorNetoPag
			Where codigoCuentaPorPagar = codCuentaPorPagar;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarCuentaPorPagar( in codCuentaPorPagar int)
		Begin
			delete from CuentasPorPagar where codigoCuentaPorPagar = codCuentaPorPagar;
        End$$
Delimiter ;


-- Procedimientos de CuentasPorCobrar
-- Agregar
Delimiter $$
	Create procedure sp_AgregarCuentaPorCobrar(
		in numeroFactura varchar (45),
		in anio varchar(4),
		in mes int(2),
		in valorNetoPago double (11,2),
		in estadoPago varchar (45),
		in codigoAdministracion int,
		in codigoCliente int, 
		in codigoLocal int)
	Begin
		insert into CuentasPorCobrar(numeroFactura, anio, mes, valorNetoPago,estadoPago,
						codigoAdministracion, codigoCliente, codigoLocal) values (numeroFactura,
						anio, mes, valorNetoPago,estadoPago, codigoAdministracion, codigoCliente, codigoLocal);
    End$$
Delimiter ;


-- Listar
Delimiter $$
	Create procedure sp_ListarCuentaPorCobrar()
		Begin
			Select
				CC.codigoCuentaPorCobrar,
				CC.numeroFactura,
				CC.anio,
				CC.mes,
				CC.valorNetoPago,
				CC.estadoPago,
				CC.codigoAdministracion,
				CC.codigoCliente, 
				CC.codigoLocal
			From CuentasPorCobrar CC;
        End$$
Delimiter ;


-- Buscar
Delimiter $$
	Create procedure sp_BuscarCuentaPorCobrar(in codCuentaPorCobrar int)
		Begin			
			Select
				CC.codigoCuentaPorCobrar,
				CC.numeroFactura,
				CC.anio,
				CC.mes,
				CC.valorNetoPago,
				CC.estadoPago,
				CC.codigoAdministracion,
				CC.codigoCliente, 
				CC.codigoLocal
			From CuentasPorCobrar CC
			Where codigoCuentaPorCobrar = codCuentaPorCobrar;                
		End$$
Delimiter ;


-- Actualizar
Delimiter $$
	Create procedure sp_ActualizarCuentaPorCobrar(
		in codCuentaPorCobrar int,
		in numeroFact varchar (45),
		in ani varchar(4),
		in me int(2),
		in valorNetoPag double (11,2),
		in estadoPag varchar (45))
	Begin 
		Update CuentasPorCobrar
			Set 
				numeroFactura = numeroFact,
				anio = ani,
				mes = me, 
				valorNetoPago = valorNetoPag,
				estadoPago = estadoPag
			Where codigoCuentaPorCobrar = codCuentaPorCobrar;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarCuentaPorCobrar( in codCuentaPorCobrar int)
		Begin
			delete from CuentasPorCobrar where codigoCuentaPorCobrar = codCuentaPorCobrar;
        End$$
Delimiter ;



-- Procedimientos de Departamentos
-- Agregar
Delimiter $$
	Create procedure sp_AgregarDepartamento(
		in nombreDepartamento varchar(45))
	Begin
		insert into Departamentos(nombreDepartamento) values (nombreDepartamento);
    End$$
Delimiter ;


-- Enlistar 
Delimiter $$
	Create procedure sp_ListarDepartamento()
		Begin
			Select
				D.codigoDepartamento,
                D.nombreDepartamento
			From Departamentos D;
        End$$
Delimiter ;


-- Buscar
Delimiter $$
	Create procedure sp_BuscarDepartamento(in codDepartamento int)
		Begin			
			Select
				D.codigoDepartamento,
                D.nombreDepartamento
			From Departamentos D
			Where codigoDepartamento = codDepartamento;                
		End$$
Delimiter ;


-- Actualizar
Delimiter $$
	Create procedure sp_ActualizarDepartamento(
		in codDepartamento int,
        in nomDepartamento varchar(45))
	Begin 
		Update Departamentos 
			Set 
				nombreDepartamento=nomDepartamento
			Where codigoDepartamento = codDepartamento;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarDepartamento( in codDepartamento int)
		Begin
			delete from Departamentos where codigoDepartamento = codDepartamento;
        End$$
Delimiter ;




-- Procedimientos de Cargos
-- Agregar
Delimiter $$
	Create procedure sp_AgregarCargo(
		in nombreCargo varchar(45))
	Begin 
		insert into Cargos(nombreCargo) values (nombreCargo);
	End$$
Delimiter ; 


-- Enlistar
Delimiter $$ 
	Create procedure sp_ListarCargo()
		Begin
			Select
				C.codigoCargo, 
                C.nombreCargo
			From Cargos C;
		End$$
Delimiter ; 


-- Buscar 
Delimiter $$
	Create procedure sp_BuscarCargo(in codCargo int)
		Begin 
			Select
				C.codigoCargo,
                C.nombreCargo
			From Cargos C
			Where codigoCargo = codCargo;
		End$$
Delimiter ;


-- Actualizar 
Delimiter $$
	Create procedure sp_ActualizarCargo( 
		in codCargo int,
        in nomCargo varchar (45))
	Begin 
		Update Cargos
			Set 
				nombreCargo=nomCargo
			Where codigoCargo = codCargo;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarCargo(in codCargo int)
    Begin 
		Delete from Cargos where codigoCargo = codCargo;
	End$$
Delimiter ;


call sp_ListarAdministrador();


-- Procedimientos de Horarios
-- Agregar
Delimiter $$
	Create procedure sp_AgregarHorario(
		in horarioEntrada varchar(5),
        in horarioSalida varchar (5),
        in lunes boolean,
        in martes boolean,
        in miercoles boolean,
        in jueves boolean,
        in viernes boolean)
	Begin
		insert into Horarios(horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes)
			values (horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes);
	End$$
Delimiter ;


-- Enlistar 
Delimiter $$
	Create procedure sp_ListarHorario()
	Begin
		Select
			H.codigoHorario,
            H.horarioEntrada,
            H.horarioSalida,
            H.lunes,
            H.martes,
            H.miercoles,
            H.jueves,
            H.viernes
		from Horarios H;
	End$$
Delimiter ;


-- Buscar 
Delimiter $$
	Create procedure sp_BuscarHorario(in codHorario int)
    Begin 
		Select 
			H.codigoHorario,
            H.horarioEntrada,
            H.horarioSalida,
            H.lunes,
            H.martes,
            H.miercoles,
            H.jueves,
            H.viernes
		from Horarios H
		Where codigoHorario = codHorario;
	End$$
Delimiter ;


-- Actualizar 
Delimiter $$
	Create procedure sp_ActualizarHorario(
		in codHorario int,
		in horaEntrada varchar(5),
        in horaSalida varchar (5),
        in lun boolean,
        in mart boolean,
        in mierco boolean,
        in juev boolean,
        in viern boolean)
	Begin 
		Update Horarios
			Set 
				horarioEntrada=horaEntrada,
				horarioSalida=horaSalida,
				lunes=lun,
				martes=mart,
				miercoles=mierco,
				jueves=juev,
				viernes=viern
			Where codigoHorario = codHorario;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarHorario(in codHorario int)
    Begin 
		Delete from Horarios where codigoHorario = codHorario;
	End$$
Delimiter ;


-- Procedimientos de Empleado
-- Agregar 

Delimiter $$
	Create procedure sp_AgregarEmpleado(
		in nombreEmpleado varchar(45), 
        in apellidoEmpleado varchar(45), 
        in correoElectronico varchar(45),
        in telefonoEmpleado varchar(8), 
        in fechaContratacion date, 
        in sueldo double(11,2),
        in codigoDepartamento int, 
        in codigoCargo int, 
        in codigoHorario int, 
        in codigoAdministracion int)
	Begin 
		Insert into Empleados (nombreEmpleado, apellidoEmpleado, correoElectronico, telefonoEmpleado, fechaContratacion,
						sueldo, codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion) values (nombreEmpleado, 
                        apellidoEmpleado, correoElectronico, telefonoEmpleado, fechaContratacion,
						sueldo, codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion);
	End$$
Delimiter ;


-- Listar
Delimiter $$
	Create procedure sp_ListarEmpleado()
    Begin
		Select
			EM.codigoEmpleado,
			EM.nombreEmpleado, 
			EM.apellidoEmpleado, 
			EM.correoElectronico,
			EM.telefonoEmpleado, 
			EM.fechaContratacion, 
			EM.sueldo,
			EM.codigoDepartamento, 
			EM.codigoCargo, 
			EM.codigoHorario, 
			EM.codigoAdministracion
        from Empleados EM;
	End$$
Delimiter ; 


-- Buscar 
Delimiter $$
	Create procedure sp_BuscarEmpleado(in codEmpleado int)
    Begin
		Select
			EM.codigoEmpleado,
			EM.nombreEmpleado, 
			EM.apellidoEmpleado, 
			EM.correoElectronico,
			EM.telefonoEmpleado, 
			EM.fechaContratacion, 
			EM.sueldo,
			EM.codigoDepartamento, 
			EM.codigoCargo, 
			EM.codigoHorario, 
			EM.codigoAdministracion
        from Empleados EM
        where codigoEmpleado = codEmpleado;
	End$$
Delimiter ;


-- Actualizar 
Delimiter $$
	Create procedure sp_ActualizarEmpleado(
		in codEmpleado int,
		in nomEmpleado varchar(45), 
		in apellEmpleado varchar(45), 
		in corrElectronico varchar(45),
		in telEmpleado varchar(8), 
		in fechContratacion date, 
		in suel double(11,2))
	Begin 
		Update Empleados
			Set 
              codigoEmpleado = codEmpleado,
			  nombreEmpleado = nomEmpleado, 
			  apellidoEmpleado = apellEmpleado, 
			  correoElectronico = corrElectronico,
			  telefonoEmpleado = telEmpleado, 
			  fechaContratacion = fechContratacion, 
			  sueldo = suel
			where codigoEmpleado = codEmpleado;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarEmpleado( in codEMpleado int)
    Begin 
		Delete from Empleados where codigoEmpleado = codEmpleado;
	End$$
Delimiter ;




-- Procedimientos de TipoCliente
-- Agregar
Delimiter $$
	Create procedure sp_AgregarTipoCliente(
    in descripcion varchar (45))
    Begin
		insert into TipoCliente (descripcion) values (descripcion);
	End$$
Delimiter ;


-- Enlistar
Delimiter $$
	Create procedure sp_ListarTipoCliente()
    Begin 
		Select 
			TC.codigoTipoCliente,
			TC.descripcion
		from TipoCliente TC;
	End$$
Delimiter ;


-- Buscar
Delimiter $$
	Create procedure sp_BuscarTipoCliente(in codTipoCliente int)
    Begin 
		Select 
			TC.codigoTipoCliente,
			TC.descripcion
		from TipoCliente TC
		Where codigoTipoCliente = codTipoCliente;
	End$$
Delimiter ;


-- Actualizar
Delimiter $$
	Create procedure sp_ActualizarTipoCliente(
    in codTipoCliente int,
    in descrip varchar (45))
    Begin 
		Update TipoCliente
			Set 
				descripcion=descrip
			Where codigoTipoCliente = codTipoCliente;
	End$$
Delimiter ; 


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarTipoCliente(in codTipoCliente int)
    Begin  
		Delete from TipoCliente where codigoTipoCliente = codTipoCliente;
	End$$
Delimiter ;





-- Procedimientos de Locales 
-- Agregar 
Delimiter $$
	Create procedure sp_AgregarLocal(
		in disponibilidad boolean,
		in valorLocal double(11,2),
		in valorAdministracion double(11,2))
	Begin 
		insert into Locales ( disponibilidad, valorLocal, valorAdministracion)
			Values ( disponibilidad, valorLocal, valorAdministracion);
	End$$
Delimiter ;


-- Enlistar 
Delimiter $$
	Create procedure sp_ListarLocal()
    Begin 
		Select 
			L.codigoLocal,
            L.saldoFavor,
            L.saldoContra,
            L.mesesPendientes,
            L.disponibilidad,
            L.valorLocal,
            L.valorAdministracion
		from Locales L;
	End$$
Delimiter ;


-- Buscar 
Delimiter $$
	Create procedure sp_BuscarLocal(in codLocal int)
    Begin 
		Select 
			L.codigoLocal,
            L.saldoFavor,
            L.saldoContra,
            L.mesesPendientes,
            L.disponibilidad,
            L.valorLocal,
            L.valorAdministracion
		from Locales L
		Where codigoLocal = codLocal;
	End$$
Delimiter ;


-- Actualizar 
Delimiter $$
	Create procedure sp_ActualizarLocal(
		in codLocal int,
		in disponib boolean,
		in valLocal double(11,2),
		in valAdministracion double(11,2))
	Begin 
		Update Locales
			Set
				disponibilidad=disponib,
				valorLocal=valLocal,
				valorAdministracion=valAdministracion
			Where codigoLocal = codLocal;
	End$$
Delimiter ;


-- Eliminar 
Delimiter $$
	Create procedure sp_EliminarLocal(in codLocal int)
    Begin
		Delete from Locales where codigoLocal = codLocal;
	End$$
Delimiter ;





-- Procedimientos Clientes
-- Listar
Delimiter $$
	Create procedure sp_ListarCliente()
    Begin 
		Select
			C.codigoCliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.telefonoCliente,
            C.direccionCliente,
            C.email,
            C.codigoLocal,
            C.codigoAdministracion,
            C.codigoTipoCliente
		From Clientes C;
	End$$
Delimiter ;


-- Agregar
Delimiter $$
	Create procedure sp_AgregarCliente(
		in nombreCliente varchar (45),
		in apellidoCliente varchar (45),
		in telefonoCliente varchar (8),
		in direccionCliente varchar (60),
		in email varchar (45),
		in codigoLocal int,
        in codigoAdministracion int,
		in codigoTipoCliente int)
	Begin 
		insert into Clientes(nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, email, codigoLocal, codigoAdministracion, codigoTipoCliente)
						values (nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, email, codigoLocal, codigoAdministracion, codigoTipoCliente);
	End$$
Delimiter ;


-- Buscar 
Delimiter $$
	Create procedure sp_BuscarCliente(in codCliente int)
	Begin
		Select
			C.codigoCliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.telefonoCliente,
            C.direccionCliente,
            C.email,
            C.codigoLocal,
            C.codigoAdministracion,
            C.codigoTipoCliente
		From Clientes C
		Where codigoCliente = codCliente;
	End$$
Delimiter ;


-- Actualizar
Delimiter $$
	Create procedure sp_ActualizarCliente(
		in codCliente int,
        in nomCliente varchar (45),
		in apellCliente varchar (45),
		in telCliente varchar (8),
		in direccCliente varchar (60),
		in emai varchar (45))
	Begin 
		Update Clientes
			Set
				nombreCliente = nomCliente,
				apellidoCliente = apellCliente,
				telefonoCliente = telCliente,
				direccionCliente = direccCliente,
				email = emai
			Where codigoCliente = codCliente;
	End$$
Delimiter ;


-- Eliminar
Delimiter $$
	Create procedure sp_EliminarCliente(in codCliente int)
    Begin 
		Delete from Clientes where codigoCliente = codCliente;
	End$$
Delimiter ;


-- Procedimentos Usuarios
-- Agregar
Delimiter $$
	Create procedure sp_AgregarUsuario(in nombreUsuario varchar(100), in apellidoUsuario varchar(100), 
		in usuarioLogin varchar(50), in contrasena varchar(50))
	Begin 
		Insert Into Usuarios(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena)
			values (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
	End$$
Delimiter ; 

-- Listar
Delimiter $$ 
	Create procedure sp_ListarUsuarios()
		Begin
			Select 
				U.codigoUsuario, 
                U.nombreUsuario,
                U.apellidoUsuario, 
                U.usuarioLogin, 
                U.contrasena
			from Usuarios U;
		End$$
Delimiter ;







-- Administracion 
call sp_AgregarAdministrador('14 avenida 28-26 zona 2', '56587541');
call sp_AgregarAdministrador('10 avenida 11-26 zona 4', '56549871');


-- Proveeodores
call sp_AgregarProveedor('4545464-8', 'Agua', '45454545', 'Antigua Guatemala', 18500.50, 12450.60, 1);
call sp_AgregarProveedor('4654844-2', 'Limpieza', '48645484', 'Escuintla', 19500.20, 14850.80, 1);
call sp_AgregarProveedor('7894878-7', 'Seguridad', '78674112', 'Jutiapa', 25000.56, 17240.60, 2);
call sp_AgregarProveedor('1231321-4', 'Finanzas', '87888445', 'San Marcos', 12800.20, 12510.50, 1);
call sp_AgregarProveedor('4315313-8', 'Internet', '46565655', 'Jalapa', 45222.50, 12450.20, 2);
call sp_AgregarProveedor('4153131-5', 'Electricidad', '21354156', 'Mixco', 78211.50, 12450.60, 2);
call sp_AgregarProveedor('3215351-83', 'Mantenimiento de Maquinaria', '13588464', 'Petén', 17884.45, 11350.60, 2);
call sp_AgregarProveedor('6543121-8', 'Asesoria', '46987875', 'Antigua Guatemala', 7800.70, 8450.60, 1);
call sp_AgregarProveedor('7894651-2', 'Remodelación', '98765644', 'Zona 1 de Mixco', 15200.50, 1250.60, 1);
call sp_AgregarProveedor('4135151-8', 'Plomeria', '43235668', 'Esquipulas', 5600.60, 12450.60, 1);


-- CuentasPorPagar
call sp_AgregarCuentaPorPagar('105', '2021-07-17', 'Pendiente', 11505.30, 1, 3);
call sp_AgregarCuentaPorPagar('106', '2021-08-24', 'Pendiente', 15630.50, 2, 1);



-- Departamentos
call sp_AgregarDepartamento('Limpieza');
call sp_AgregarDepartamento('Seguridad');


-- Cargos
call sp_AgregarCargo('Jefe de Seguridad');
call sp_AgregarCargo('Jefe de Mantenimiento');


-- Horarios
call sp_AgregarHorario('13:00', '07:00', true, false, false, true, true);
call sp_AgregarHorario('19:00', '08:00', true, true, false, false, true);


-- TipoClientes
call sp_AgregarTipoCliente('Nuevo');
call sp_AgregarTipoCliente('Alto Volumen de Compra');


-- Locales
call sp_AgregarLocal(true, 1200.50, 12500.50);
call sp_AgregarLocal(false, 11500.50, 12000.50);
call sp_AgregarLocal(true, 8000.00, 12500.00);
call sp_AgregarLocal(true, 4500.00, 8500.5);
call sp_AgregarLocal(false, 7800.60, 13800.5);
call sp_AgregarLocal(false, 9900.00, 11600.00);
call sp_AgregarLocal(true, 6000.00, 12000.00);
call sp_AgregarLocal(true, 7200.40, 10600.80);
call sp_AgregarLocal(true, 5900.00, 9700.00);
call sp_AgregarLocal(false, 4000.50, 7500.50);


-- Empleados
call sp_AgregarEmpleado('Daniel', 'Morales', 'DanielMorales@gmail.com', '23234545', '2021-05-18', 5650.50, 1, 1, 1, 1);
call sp_AgregarEmpleado('Marcos', 'Hernández', 'MarcosH@gmail.com', '45151545', '2020-05-08', 7150.50, 1, 2, 2, 2);


-- Clientes
call sp_AgregarCliente('Jose', 'Martinez', '58585858', '6 avenida 19-4 zona 1', 'Jmartinez@gmail.com', 1, 1, 1);
call sp_AgregarCliente('Romero', 'Morales', '12314142', '12 avenida 11-13 zona 5', 'Rmorales@gmail.com', 1, 2, 2);


-- CuentasPorCobrar
call sp_AgregarCuentaPorCobrar('105', '2015', 06, 7450.30, 'Cancelado', 2, 1, 6);
call sp_AgregarCuentaPorCobrar('110', '2019', 08, 6420.20, 'Cancelado', 1, 2, 2);



-- Usuarios
call sp_AgregarUsuario('Santiago', 'López', 'Slopez', '123456');





-- Actividades en clase

Delimiter $$
	Create procedure sp_CalcularLiquido(in codLocal int)
	Begin
		Select L.saldoFavor-L.saldoContra as 'Liquido' 
        From Locales  L where codigoLocal= codLocal;	
    
	End$$
Delimiter ;
-- call sp_CalcularLiquido(1);


Delimiter $$
	Create procedure sp_LiquidoProveedor(in codProveedor int)
    Begin 
		Select P.codigoProveedor,
				P.NITProveedor,
                P.servicioPrestado,
                P.telefonoProveedor,
                P.direccionProveedor,
                P.saldoFavor,
                P.saldoContra,
                P.codigoAdministracion,
                P.saldoFavor-P.saldoContra as Saldo_Liquido
		From Proveedores P where codigoProveedor = codProveedor;
	End$$
Delimiter ;
-- call sp_LiquidoProveedor(1);
-- Santiago López 2020076


Delimiter $$
	Create procedure sp_LiquidoProveedores()
    Begin 
		Select 
			P.codigoProveedor,
            P.NITProveedor,
            P.servicioPrestado,
            P.telefonoProveedor,
            P.direccionProveedor,
            P.saldoFavor, 
            P.saldoContra,
            P.codigoAdministracion,
            P.saldoFavor-P.saldoContra as Saldo_Liquido
		From Proveedores P;
	End$$
Delimiter ;
-- call sp_LiquidoProveedores();
-- Santiago López 2020076


Delimiter $$
	Create procedure sp_Calculo_Renta(in codLocal int)
    Begin 
		Select 
			L.codigoLocal,
            L.saldoFavor,
            L.saldoContra,
            L.mesesPendientes,
            L.disponibilidad,
            L.valorLocal,
            L.valorAdministracion,
            L.mesesPendientes*L.valorLocal as Calculo_Meses_Pendientes,
            L.saldoFavor-L.saldoContra as Liquido,
            (L.mesesPendientes*L.valorLocal)-(L.saldoFavor-L.saldoContra) as Total_Renta
		From Locales L 
        Where codigoLocal = codLocal;
	End$$
Delimiter ;
-- call sp_Calculo_Renta(1);
-- Santiago López 2020076


Delimiter $$ 
	Create procedure sp_Calculos_De_Rentas()
		Begin 
			Select 
            L.codigoLocal,
            L.saldoFavor,
            L.saldoContra,
            L.mesesPendientes,
            L.disponibilidad,
            L.valorLocal,
            L.valorAdministracion,
            L.mesesPendientes*L.valorLocal as Calculo_Meses_Pendientes,
            L.saldoFavor-L.saldoContra as Liquido,
            (L.mesesPendientes*L.valorLocal)-(L.saldoFavor-L.saldoContra) as Total_Renta
			From Locales L;
	End$$
Delimiter ;
-- call sp_Calculos_De_Rentas();
-- Santiago López 2020076


Delimiter $$
	Create Procedure sp_Cantidad_Locales()
    Begin 
		Select count(*) as Cantidad_Locales
		From Locales;
	End$$
Delimiter ;
-- call sp_Cantidad_Locales();
-- Santiago López 2020076


Delimiter $$
	Create procedure sp_Disponibilidad_Local()
    Begin 
		Select count(*) as Locales_Disponibles from Locales where disponibilidad = true;
	End$$
Delimiter ;
-- call sp_Disponibilidad_Local();
-- Santiago López 2020076


Delimiter $$
	Create procedure sp_Disponibilidad_Locales()
    Begin 
		Select count(*) as Locales_No_Disponibles from Locales where disponibilidad = false;
	End$$
Delimiter ; 
-- call sp_Disponibilidad_Locales();
-- Santiago López 2020076



ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password By '58562100';




Select * from TipoCliente TC inner join Clientes C on
	TC.codigoTipoCliente = C.codigoTipoCliente
		where TC.descripcion = 'Nuevo';


Delimiter $$
	Create procedure sp_ReporteEmpleado(in codEmpleado int)
		Begin 
			Select * from Empleados EM 
            inner join Cargos C on C.codigoCargo=EM.codigoCargo
            inner join Departamentos D on D.codigoDepartamento=EM.codigoDepartamento
            inner join Horarios H on H.codigoHorario= EM.codigoHorario 
			where codigoEmpleado= codEmpleado;
		End$$
	
Delimiter ;


Delimiter $$ 
	Create procedure sp_ReporteClienteIndividual(in codCliente int)
	Begin 
		Select * from Clientes C 
			inner join TipoCliente TC on TC.codigoTipoCliente = C.codigoTipoCliente
            inner join Locales L on L.codigoLocal = C.codigoLocal
            inner join CuentasPorCobrar CC on CC.codigoCliente = C.codigoCliente
		where C.codigoCliente = codCliente;
	End$$
Delimiter ;
call sp_ListarCliente();

