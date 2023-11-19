--Derby does not support DROP TABLE IF EXISTS 
DROP TABLE DISPONIBILIDADEMPLEADO;
DROP TABLE VINCULACIONCONLAEMPRESA;
DROP TABLE ROLESENEMPRESA;
DROP TABLE TIPODEDISPONIBILIDAD;
DROP TABLE TIPODEVINCULACION;
DROP TABLE TIPODEROL;
DROP TABLE COMPONENTE;
DROP TABLE PC;
DROP TABLE PEDIDOPC;
DROP TABLE COMPONENTESENCONFIGURACION;
DROP TABLE CONFIGURACIONPC;
DROP TABLE CPU; 
DROP TABLE PEDIDOCOMPONENTES;
DROP TABLE ESTADOCOMPRACOMPONENTES;
DROP TABLE DESCRIPCIONCOMPONENTE;
DROP TABLE TIPOCOMPONENTE;
DROP TABLE ESTADOVENTAPCS;
DROP TABLE ESPACIOALMACENAMIENTO;
DROP TABLE EMPRESA;
DROP TABLE EMPLEADO;
DROP TABLE USUARIO;

-- Enum
create table TIPODEROL
(
	IdTipo SMALLINT not null,
	NombreTipo VARCHAR(20) not null unique,
		PRIMARY KEY(IdTipo)
);

INSERT INTO TIPODEROL
VALUES  (1,'PERSONALALMACEN'),
        (2,'GERENTEVENTAS'),
        (3,'TECNICODELTALLER');

-- Enum
create table TIPODEVINCULACION
(
	IdTipo SMALLINT not null,
	NombreTipo VARCHAR(20) not null unique,
		PRIMARY KEY(IdTipo)

);

INSERT INTO TIPODEVINCULACION
VALUES  (1,'CONTRATADO'),
        (2,'DESPEDIDO'),
        (3,'ENERTE');

-- Enum
create table TIPODEDISPONIBILIDAD
(
	IdTipo SMALLINT not null,
	NombreTipo VARCHAR(20) not null unique,
		PRIMARY KEY(IdTipo)
);

INSERT INTO TIPODEDISPONIBILIDAD
VALUES  (1,'VACACIONES'),
        (2,'BAJATEMPORAL'),
	(3, 'TRABAJANDO');

create table USUARIO
(
	NifCif VARCHAR(9) not null primary key,
	Nombre VARCHAR(30) not null,
	DireccionPostal VARCHAR(50),
	DireccionElectronica VARCHAR(50),
	Telefono VARCHAR(20)
);

INSERT INTO USUARIO
VALUES	('1234567A', 'Pedro', 'Calle Laguna', 'pedro@hotmail.com', '658124789'),
	('7654321A', 'Paula', 'Calle Magdalena', 'paula@hotmail.com', '623456789'),
	('4526364B', 'Sara', 'Calle pozo', 'sara@gmail.es', '687412489'),
	('1122334C', 'Juan', 'Carretera corta', 'juan@hotmail.es', '666555444'),
	('5553673C', 'Pepe', 'Avenida Palencia', 'pepe@hotmail.es', '654123789');

create table EMPRESA
(
	Cif VARCHAR(9) not null primary key,
	EsCliente BOOLEAN,
	EsProveedor BOOLEAN,
            FOREIGN KEY(Cif) REFERENCES USUARIO(NifCif)
);

INSERT INTO EMPRESA
VALUES	('1234567A', true, false),
	('7654321A', true, false),
	('4526364B', true, false),
	('5553673C', false, true);

create table EMPLEADO
(
	Nif VARCHAR(9) not null primary key,
	Password VARCHAR(20) not null,
	FechaInicio DATE not null,
            FOREIGN KEY(Nif) REFERENCES USUARIO(NifCif)
);

INSERT INTO EMPLEADO
VALUES  ('1234567A', 'abcd', '2020-03-06'),
	('7654321A', 'abcd', '2020-03-06'),
	('4526364B', 'cdef', '2020-03-06'),
	('1122334C', '1111', '2020-03-06'),
	('5553673C', '1111', '2020-03-06');

-- Association
create table ROLESENEMPRESA
(
	ComienzoEnRol DATE not null,
	Empleado VARCHAR(9) not null,
	Rol SMALLINT not null,
            FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
            FOREIGN KEY(Rol) REFERENCES TIPODEROL(IdTipo)
);

INSERT INTO ROLESENEMPRESA
VALUES	('2021-01-04', '1234567A', 1),
	('2021-01-03', '7654321A', 2),
	('2021-01-03', '4526364B', 2),
	('2021-01-03', '1122334C', 3),
	('2021-01-02', '5553673C', 3);

-- Association
create table VINCULACIONCONLAEMPRESA
(
	inicio DATE not null,
	Empleado VARCHAR(9) not null,
	Vinculo SMALLINT not null,
		FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
		FOREIGN KEY(Vinculo) REFERENCES TIPODEVINCULACION(IdTipo) 
);

INSERT INTO VINCULACIONCONLAEMPRESA
VALUES  ('2021-02-05', '1234567A', 1),
	('2021-02-04', '7654321A', 1),
        ('2020-05-05', '4526364B', 2),
	('2021-01-03', '1122334C', 1),
	('2020-11-11', '5553673C', 3);


-- Association
create table DISPONIBILIDADEMPLEADO
(
	Comienzo DATE not null,
	FinalPrevisto DATE,
	Empleado VARCHAR(9) not null,
	Disponibilidad SMALLINT not null,
		FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
		FOREIGN KEY(Disponibilidad) REFERENCES TIPODEDISPONIBILIDAD(IdTipo)
);

INSERT INTO DISPONIBILIDADEMPLEADO
VALUES 	('2019-02-05', '2022-12-10', '1234567A', 3),
	('2019-02-05', '2022-12-10', '7654321A', 3),
	('2019-02-05', '2022-06-12', '4526364B', 2),
	('2021-01-03', '2022-06-12', '1122334C', 3),
	('2019-02-05', '2022-07-09', '5553673C', 2);

create table ESPACIOALMACENAMIENTO
(
	IdUbicacion INTEGER not null primary key,
	Seccion SMALLINT,
	Zona SMALLINT not null,
	Estanteria SMALLINT,
	Ocupado BOOLEAN
);

INSERT INTO ESPACIOALMACENAMIENTO
VALUES	(123, 1, 2, 1, false),
	(124, 9, 2, 5, false),
	(125, 8, 1, 9, true),
	(126, 3, 4, 7, false);

-- Enum
create table CPU 
(
	IdTipoCPU SMALLINT not null primary key,
	NombreTipoCPU VARCHAR(20) not null
);

INSERT INTO CPU
VALUES  (1,'AMD'),
        (2,'INTELCORE');

create table CONFIGURACIONPC
(
	IdConfiguracion INTEGER not null primary key,
	TipoCPU SMALLINT not null,
	VelocidadCPU REAL not null,
	CapacidadRAM INTEGER not null,
	CapacidadDD INTEGER not null,
	VelocidadTarjetaGrafica REAL,
	MemoriaTarjetaGrafica INTEGER,
            FOREIGN KEY(TipoCPU) REFERENCES CPU(IdTipoCPU)
);

INSERT INTO CONFIGURACIONPC
VALUES	(920, 1, 4, 8, 128, 4, 2),
	(921, 2, 4, 16, 256, 3, 3),
	(922, 1, 3, 16, 512, 5, 2),
	(923, 2, 3, 32, 256, 3, 3),
	(924, 2, 4, 8, 1024, 2, 3);

--Enum
create table TIPOCOMPONENTE
(
	IdTipoComponente SMALLINT not null primary key,
	NombreTipoComponente VARCHAR(20) not null
);

INSERT INTO TIPOCOMPONENTE
VALUES  (1,'TARJETAGRAFICA'),
        (2,'DISCODURO'),
        (3,'PLACABASE'),
        (4,'CAJA'),
        (5,'PROCESADOR'),
        (6,'RAM');

create table DESCRIPCIONCOMPONENTE
(
	IdDescripcion INTEGER not null primary key,
	Tipo SMALLINT not null,
	Marca VARCHAR(20) not null,
	Modelo VARCHAR(20),
	Precio REAL,
	CaracteristicasTecnicas VARCHAR(250),
            FOREIGN KEY(Tipo) REFERENCES TIPOCOMPONENTE(IdTipoComponente)
);

INSERT INTO DESCRIPCIONCOMPONENTE
VALUES	(1234, 1, 'Nvidia', 'GTX7', 200.20, 'Componente para ordenador de sobremesa'),
	(1235, 2, 'WD', '12.4', 100.00, 'Componente para ordenador de sobremesa'),
	(1236, 3, 'Gigabyte', 'GTX7', 200.20, 'Componente para ordenador de sobremesa'),
	(1237, 4, 'Razer', 'Tomahawk', 150, 'Componente para ordenador de sobremesa'),
	(1238, 5, 'Intel', 'i5', 270, 'Componente para ordenador de sobremesa'),
	(1239, 6, 'Kingston', '2.3', 64, 'Componente para ordenador de sobremesa');

-- Association
create table COMPONENTESENCONFIGURACION
(
	IdDescripcion INTEGER not null,
	IdConfiguracion INTEGER not null,
            FOREIGN KEY(IdConfiguracion) REFERENCES CONFIGURACIONPC(IdConfiguracion),
            FOREIGN KEY(IdDescripcion) REFERENCES DESCRIPCIONCOMPONENTE(IdDescripcion)
);

INSERT INTO COMPONENTESENCONFIGURACION
VALUES	(1234, 920),
	(1238, 923),
	(1239, 923),
	(1235, 923),
	(1236, 924);

-- Enum
create table ESTADOVENTAPCS
(
	IdEstadoVenta SMALLINT not null primary key,
	NombreEstadoVenta VARCHAR(20) not null
);

INSERT INTO ESTADOVENTAPCs
VALUES  (1,'SOLICITADO'),
        (2,'ENPROCESO'),
        (3,'COMPLETADO'),
        (4,'ENVIADO'),
        (5,'ENTREGADO');

create table PEDIDOPC
(
	IdPedido INTEGER not null primary key,
	CantidadSolicitada SMALLINT not null,
	FechaPedido DATE not null,
    	Estado SMALLINT not null,
	ConfiguracionSolicitada INTEGER not null,
	EncargadoPor VARCHAR(9) not null,
            FOREIGN KEY(ConfiguracionSolicitada) REFERENCES CONFIGURACIONPC(IdConfiguracion),
            FOREIGN KEY(EncargadoPor) REFERENCES EMPRESA(Cif),
            FOREIGN KEY(Estado) REFERENCES ESTADOVENTAPCS(IdEstadoVenta)
);

INSERT INTO PEDIDOPC
VALUES	(758, 3, '2021-01-01', 1, 920, '1234567A'),
	(759, 2, '2021-01-02', 1, 922, '5553673C'),
	(760, 1, '2021-01-03', 2, 923, '1234567A'),
	(761, 2, '2021-02-02', 5, 924, '5553673C'),
	(762, 8, '2021-01-02', 2, 923, '5553673C'),
	(763, 5, '2021-02-02', 2, 920, '1234567A');

-- Enum
create table ESTADOCOMPRACOMPONENTES
(
	IdEstadoCompra SMALLINT not null primary key,
	NombreEstadoCompra VARCHAR(20) not null
);

INSERT INTO ESTADOCOMPRACOMPONENTES
VALUES  (1,'ENCARGADA'),
        (2,'ENVIADA'),
        (3,'RECIBIDA');

create table PEDIDOCOMPONENTES
(
	IdPedido INTEGER not null primary key,
	ComponentePedido INTEGER not null,
	CantidadPedida SMALLINT,
	FechaPedido DATE not null,
	FechaOfertaEntrega DATE,
	Estado SMALLINT not null,
	SolicitadoA VARCHAR(9) not null,
        FOREIGN KEY(SolicitadoA) REFERENCES EMPRESA(Cif),
	    FOREIGN KEY(ComponentePedido) REFERENCES DESCRIPCIONCOMPONENTE(IdDescripcion),
	    FOREIGN KEY(Estado) REFERENCES ESTADOCOMPRACOMPONENTES(IdEstadoCompra)
);

INSERT INTO PEDIDOCOMPONENTES
VALUES	(758, 1234, 4, '2021-01-02', '2021-03-03', 1, '1234567A'),
	(760, 1238, 5, '2020-12-12', '2021-01-01', 3, '1234567A'),
	(761, 1237, 1, '2021-04-05', '2021-05-05', 1, '1234567A');

create table PC
(
	IdEtiqueta INTEGER not null primary key,
	Reservado BOOLEAN,
	FechaMontaje DATE not null,
	IdConfiguracion INTEGER not null,
	MontadoPor VARCHAR(9) not null,
	IdPedido INTEGER,
	Ubicacion INTEGER,

            FOREIGN KEY(IdConfiguracion) REFERENCES CONFIGURACIONPC(IdConfiguracion),
            FOREIGN KEY(MontadoPor) REFERENCES EMPLEADO(Nif),
            FOREIGN KEY(Ubicacion) REFERENCES ESPACIOALMACENAMIENTO(IdUbicacion),
	    	FOREIGN KEY(IdPedido) REFERENCES PEDIDOPC(IdPedido)
);

INSERT INTO PC
VALUES	(652, false, '2021-02-02', 923, '1234567A', null, 123),
	(653, true, '2021-03-08', 920, '1234567A', 759, 124),
	(654, false, '2021-03-09', 924, '1234567A', null, 126),
	(655, false, '2021-03-10', 920, '1234567A', null, 125),
	(657, true, '2021-03-09', 920, '1234567A', 761, 123);

create table COMPONENTE
(
	IdEtiqueta INTEGER not null primary key,
	FechaEntrada DATE not null,
	Reservado BOOLEAN,
	IdDescripcion INTEGER not null,
	EtiquetaPC INTEGER,
	RecibidoEnCompra INTEGER not null,
	Ubicacion INTEGER,
            FOREIGN KEY(Ubicacion) REFERENCES ESPACIOALMACENAMIENTO(IdUbicacion),
            FOREIGN KEY(EtiquetaPC) REFERENCES PC(IdEtiqueta),
            FOREIGN KEY(RecibidoEnCompra) REFERENCES PEDIDOCOMPONENTES(IdPedido),
            FOREIGN KEY(IdDescripcion) REFERENCES DESCRIPCIONCOMPONENTE(IdDescripcion)
);

INSERT INTO COMPONENTE
VALUES	(652, '2021-06-06', false, 1234, null, 758, 123),
	(653, '2021-07-07', false, 1238, null, 761, 124),
	(654, '2021-09-09', true, 1237, 654, 760, 126),
	(655, '2021-07-07', true, 1238, 653, 761, 124),
	(656, '2021-07-07', false, 1238, null, 761, 124),
	(657, '2021-07-07', false, 1239, null, 761, 124),
	(658, '2021-07-07', false, 1235, null, 761, 124),
	(659, '2021-06-06', false, 1234, null, 758, 123);