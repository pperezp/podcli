package model.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Data {

    private final Conexion con;
    private ResultSet rs;
    private String query;
    
    public static String getFormattedDate(Date date, boolean conHora) {
        SimpleDateFormat sdf = 
                new SimpleDateFormat(
                        "dd 'de' MMMM 'de' YYYY"
                        +(conHora?" 'a las' HH:mm":""));
        
        return sdf.format(date);
    }
    
    

    public Data() throws SQLException, ClassNotFoundException {

        con = new Conexion(
            "localhost",
            "podcli",//nombre BD
            "root",
            "123456"//Password
        );

    }
    
    public List<DetalleAtencion> getDetallesAtencion(String idUsuario) throws SQLException{
        List<DetalleAtencion> lista = new ArrayList<>();
        query = "CALL getPacientesAtendidos("+idUsuario+")";
        
        rs = con.ejecutarSelect(query);
        
        while(rs.next()){
            lista.add(
                new DetalleAtencion(
                    rs.getInt(1), 
                    rs.getInt(2), 
                    rs.getString(3), 
                    rs.getString(4), 
                    rs.getString(5), 
                    rs.getString(6), 
                    rs.getInt(7)
                )
            );
        }
        
        return lista;
    }
    
    public List<CantidadAtencion> getCantidadDeAtenciones(String fecIni, String fecFin) throws SQLException{
        List<CantidadAtencion> lista = new ArrayList<>();
        
        query = "CALL getAtenciones('"+fecIni+"', '"+fecFin+"');";
        
        rs = con.ejecutarSelect(query);
        
        while(rs.next()){
            lista.add(new CantidadAtencion(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
        }
        
        return lista;
    }

    public int getCantidad(String sexo) throws SQLException{
        query = "SELECT COUNT(*) FROM paciente WHERE sexo = '"+sexo+"';";
        int cant = 0;
        
        rs = con.ejecutarSelect(query);
        if(rs.next()){
            cant = rs.getInt(1);
        }
        
        return cant;
    }
    
    public List<Rango> getGrupoEtario() throws SQLException{
        query = "SELECT * FROM rango";
        
        List<Rango> lista = new ArrayList<>();
        
        rs = con.ejecutarSelect(query);
        
        while(rs.next()){
            lista.add(new Rango(rs.getString(1), rs.getInt(2)));
        }
        
        return lista;
    }
    
    public String getNombreBy(String idFicha) throws SQLException {
        query = "SELECT"
                + "    p.nombre "
                + "FROM"
                + "    paciente p"
                + "    INNER JOIN ficha f ON f.paciente = p.id "
                + "WHERE"
                + "    f.id = " + idFicha;

        String nombre = null;
        rs = con.ejecutarSelect(query);

        if (rs.next()) {
            nombre = rs.getString(1);
        }

        con.close();
        return nombre;
    }

    public Usuario getUsuario(String rut) throws SQLException {
        Usuario u = null;

        rs = con.ejecutarSelect("SELECT * FROM usuario WHERE rut = '" + rut + "'");

        //rs = con.ejecutarSelect("SELECT * FROM usuario WHERE rut = '" + rut + "'");
        if (rs.next()) {
            u = new Usuario();

            u.setId(rs.getInt(1));
            u.setRut(rs.getString(2));
            u.setNombre(rs.getString(3));
            u.setPerfil(rs.getInt(4));
        }

        con.close();

        return u;
    }

    public void crearPaciente(Paciente p) throws SQLException {

        con.ejecutar("INSERT INTO paciente VALUES (null, "
                + "'" + p.getRut() + "', "
                + "'" + p.getNombre() + "', "
                + "'" + p.getSexo() + "', "
                + "'" + p.getDomicilio() + "', "
                + "'" + p.getFechaNacimiento() + "', "
                + "'" + p.getEstadoCivil() + "', "
                + "'" + p.getActividad() + "', "
                + "'" + p.getTelefonos() + "');");

    }

    public void crearFicha(Ficha f) throws SQLException {
        con.ejecutar("insert into ficha value(null,NOW(),"
                + "'" + f.getPaciente() + "',"
                + "'" + f.getUsuario() + "',"
                + "'" + f.getHta() + "',"
                + "'" + f.getDm() + "',"
                + "'" + f.getTipoDiabetes() + "',"
                + "'" + f.getAniosEvolucion() + "',"
                + "" + f.isMixto() + ","
                + "" + f.isControl() + ","
                + "'" + f.getFarmacoterapia() + "',"
                + "'" + f.getOtros() + "',"
                + "'" + f.getAlteracion() + "',"
                + "'" + f.getHabitos() + "',"
                + "'" + f.getTalla() + "',"
                + "'" + f.getImc() + "',"
                + "" + f.isAmputacion() + ","
                + "'" + f.getUbiAmputacion() + "',"
                + "'" + f.getnCalzado() + "',"
                + "" + f.isVarices() + ","
                + "" + f.isHeridas() + ","
                + "'" + f.getUbiHeridas() + "',"
                + "'" + f.getTipoHerida() + "',"
                + "" + f.isTratamiento() + ","
                + "" + f.isNevos() + ","
                + "'" + f.getUbiNevos() + "',"
                + "" + f.isMaculas() + ","
                + "'" + f.getTipoMaculas() + "')");
    }

    public void crearFicha(Paciente p, Ficha f) throws SQLException {
        crearPaciente(p);
        f.setPaciente(getUltimoIdPaciente());
        crearFicha(f);
    }

    public void crearAtencionPodologica(AtencionPodologica a) throws SQLException {
        query = "insert into atencionPodologica values"
                + "(null,"
                + "'" + a.getFicha() + "',"
                + "'" + a.getUsuario() + "',"
                + "NOW(),"
                + "'" + a.getPresion() + "',"
                + "'" + a.getPulsoRadial() + "',"
                + "'" + a.getPulsoPedio_d() + "' ,"
                + "'" + a.getPulsoPedio_i() + "' ,"
                + "'" + a.getPeso() + "' ,"
                + "" + a.isSens_d() + " ,"
                + "" + a.isSens_i() + ","
                + "'" + a.gettPoda1_d() + "' ,"
                + "'" + a.gettPoda1_i() + "' ,"
                + "" + a.isCuracion() + ","
                + "" + a.isColocacionPuente() + ","
                + "" + a.isResecado() + ","
                + "" + a.isEnucleacion() + ","
                + "" + a.isDevastado() + " ,"
                + "" + a.isMaso() + ","
                + "" + a.isEspiculoectomia() + "  ,"
                + "" + a.isAnalgesia() + ","
                + "" + a.isColocacionAcrilico() + " ,"
                + "" + a.isBandaMolecular()
                + ",'" + a.getTratamientoOrtonixia() + "',"
                + "" + a.isPoli() + " ,"
                + "'" + a.getObservaciones() + "', "
                + "'" +a.getLugarAtencion()+"', "
                + "'" +a.getDetalleLugar()+"')";
        con.ejecutar(query);
    }

    public List<AtencionPodologicaSelect> getAtencionesPodologicas(int idFicha) throws SQLException {//ARREGLADO

        query = "SELECT \n" +
                "    atencionPodologica.id 				AS ID, \n" +
                "    atencionPodologica.ficha 				AS 'Nº Ficha',\n" +
                "    u1.nombre 						AS Creador, \n" +
                "    atencionPodologica.fecha 				AS Fecha, \n" +
                "    atencionPodologica.presion 			AS Presión, \n" +
                "    atencionPodologica.pulsoRadial 			AS 'Pulso Radial', \n" +
                "    atencionPodologica.pulsoPedio_d                    AS 'P. Pedio (d)', \n" +
                "    atencionPodologica.pulsoPedio_i                    AS 'P. Pedio (i)', \n" +
                "    atencionPodologica.peso				AS Peso, \n" +
                "    atencionPodologica.sens_d 				AS 'Sensibilidad (d)', \n" +
                "    atencionPodologica.sens_i 				AS 'Sensibilidad (i)',\n" +
                "    atencionPodologica.tpodal_d 			AS 'Tº Podal (d)', \n" +
                "    atencionPodologica.tpodal_i 			AS 'Tº Podal (i)',\n" +
                "    atencionPodologica.curacion 			AS Curación, \n" +
                "    atencionPodologica.coloqPuente 			AS 'Coloc. Puente', \n" +
                "    atencionPodologica.resecado 			AS Resecado, \n" +
                "    atencionPodologica.enucleacion 			AS Enucleación, \n" +
                "    atencionPodologica.devastado 			AS 'Dev. Ungueal', \n" +
                "    atencionPodologica.maso 				AS 'Masoterapia o Masaje', \n" +
                "    atencionPodologica.espiculoectomia                 AS 'Espiculoectomía (Grado)', \n" +
                "    atencionPodologica.analgesia 			AS 'Analgesia (Tipo)', \n" +
                "    atencionPodologica.colocacionAcrilico              AS 'Colocación Acilico', \n" +
                "    atencionPodologica.bandaMolecular                  AS 'Colocac. Banda Molec.', \n" +
                "    tratamientoOrtonixia.nombre 			AS 'C. Bracket/Cambio Elast.', \n" +
                "    atencionPodologica.poli 				AS 'C. Policarboxilato', \n" +
                "    atencionPodologica.observaciones                   AS Observaciones,"
                + "  u2.nombre \n" +
                "FROM \n" +
                "    atencionPodologica\n" +
                "    INNER JOIN ficha                ON atencionPodologica.ficha = ficha.id\n" +
                "    INNER JOIN usuario u1           ON ficha.usuario = u1.id\n" +
                "    INNER JOIN usuario u2           ON atencionPodologica.usuario = u2.id\n" +
                "    INNER JOIN tratamientoOrtonixia ON atencionPodologica.tratamientoOrtonixia = tratamientoOrtonixia.id\n" +
                "WHERE \n" +
                "    atencionPodologica.ficha = "+idFicha+" "
                + "  ORDER BY atencionPodologica.fecha DESC;";

        rs = con.ejecutarSelect(query);
        List<AtencionPodologicaSelect> atenciones = new ArrayList<>();
        AtencionPodologicaSelect a;
        while (rs.next()) {
            a = new AtencionPodologicaSelect();
            a.setId(rs.getInt(1));
            a.setFicha(rs.getInt(2));
            a.setUsuario(rs.getString(3));
            a.setFecha(rs.getTimestamp(4));
            a.setPresion(rs.getString(5));
            a.setPulsoRadial(rs.getInt(6));
            a.setPulsoPedio_d(rs.getInt(7));
            a.setPulsoPedio_i(rs.getInt(8));
            a.setPeso(rs.getFloat(9));
            a.setSens_d(rs.getBoolean(10));
            a.setSens_i(rs.getBoolean(11));
            a.settPoda1_d(rs.getString(12));
            a.settPoda1_i(rs.getString(13));
            a.setCuracion(rs.getBoolean(14));
            a.setColocacionPuente(rs.getBoolean(15));
            a.setResecado(rs.getBoolean(16));
            a.setEnucleacion(rs.getBoolean(17));
            a.setDevastado(rs.getBoolean(18));
            a.setMaso(rs.getBoolean(19));
            a.setEspiculoectomia(rs.getBoolean(20));
            a.setAnalgesia(rs.getBoolean(21));
            a.setColocacionAcrilico(rs.getBoolean(22));
            a.setBandaMolecular(rs.getBoolean(23));

            a.setTratamientoOrtonixia(rs.getString(24));
            a.setPoli(rs.getBoolean(25));
            a.setObservaciones(rs.getString(26));
            a.setAtendidoPor(rs.getString(27));
            atenciones.add(a);
        }
        con.close();
        return atenciones;
    }

    /**
     * 
     * @param filtro Puede ser rut o ID
     * @return
     * @throws SQLException 
     */
    public Paciente getPaciente(String filtro) throws SQLException {

        query = "SELECT * FROM paciente WHERE rut = '" + filtro+"' OR id = "+filtro;

        rs = con.ejecutarSelect(query);
        Paciente p = null;

        if (rs.next()) {
            p = new Paciente();

            p.setId(rs.getInt(1));
            p.setRut(rs.getString(2));
            p.setNombre(rs.getString(3));
            p.setSexo(rs.getString(4));
            p.setDomicilio(rs.getString(5));
            p.setFechaNacimiento(rs.getTimestamp(6));
            p.setEstadoCivil(rs.getInt(7));
            p.setActividad(rs.getString(8));
            p.setTelefonos(rs.getString(9));

        }
        con.close();

        return p;
    }
    
    public Paciente getPacienteByRut(String rut) throws SQLException {

        query = "SELECT * FROM paciente WHERE rut = '" + rut+"'";

        rs = con.ejecutarSelect(query);
        Paciente p = null;

        if (rs.next()) {
            p = new Paciente();

            p.setId(rs.getInt(1));
            p.setRut(rs.getString(2));
            p.setNombre(rs.getString(3));
            p.setSexo(rs.getString(4));
            p.setDomicilio(rs.getString(5));
            p.setFechaNacimiento(rs.getTimestamp(6));
            p.setEstadoCivil(rs.getInt(7));
            p.setActividad(rs.getString(8));
            p.setTelefonos(rs.getString(9));

        }
        con.close();

        return p;
    }

    public List<Paciente> getPacientes(String filtro) throws SQLException {
        List<Paciente> lista = new ArrayList<>();

        query = "SELECT * FROM paciente "
                + "WHERE rut LIKE '%" + filtro + "%' OR "
                + "nombre LIKE '%" + filtro + "%'";

        rs = con.ejecutarSelect(query);
        Paciente p;

        while (rs.next()) {
            p = new Paciente();

            p.setId(rs.getInt(1));
            p.setRut(rs.getString(2));
            p.setNombre(rs.getString(3));
            p.setSexo(rs.getString(4));
            p.setDomicilio(rs.getString(5));
            p.setFechaNacimiento(rs.getTimestamp(6));
            p.setEstadoCivil(rs.getInt(7));
            p.setActividad(rs.getString(8));
            p.setTelefonos(rs.getString(9));

            lista.add(p);
        }

        con.close();

        return lista;
    }

    public FichaSelect getFicha(String rut) throws SQLException { //arreglado

        rs = con.ejecutarSelect("SELECT "
                + "	f.id AS 'Nº Ficha', p.nombre AS Paciente, p.sexo AS Sexo, "
                + "     p.fechaNacimiento AS Fecha, p.domicilio AS Domicilio, "
                + "	p.rut AS Rut, e.nombre AS 'Estado Civil', p.actividad AS Actividad, "
                + "     p.telefonos AS Fonos, f.fecha AS 'Fecha Ficha', "
                + "	u.nombre AS Encargado, hta.nombre AS HTA, dm.nombre AS DM, "
                + "     f.tipoDiabetes AS 'Tipo Diabetes', f.aniosEvolucion AS 'Años Evolución', "
                + "	f.mixto AS 'Pcte Mixto', f.control AS Control, "
                + "     f.farmacoterapia AS Farmacoterapia, f.otros AS 'Otras Patologias', "
                + "	f.alteraciones AS 'Alteraciones Ortopedicas', "
                + "     f.habitos AS 'Habitos Nocivos', f.talla AS Talla, "
                + "     f.imc AS IMC, f.amputacion AS Amputación, "
                + "	f.ubiAmputacion AS 'Ubicaciòn Amp.', f.nCalzado AS 'Nº Calzado', "
                + "     f.varices AS Varices, f.heridas AS Heridas, f.ubiHeridas AS 'Ubi. heridas', "
                + "	f.tipoHerida AS 'Tipo Heridas', f.tratamiento AS Tratamientos, "
                + "     f.nevos AS Nevos, f.ubiNevos AS 'Ubi. Nevos', f.maculas AS Maculas, "
                + "	f.tipoMaculas AS 'Tipo Maculas' "
                + "FROM "
                + "	ficha f "
                + "	INNER JOIN respuesta hta ON f.hta = hta.id "
                + "	INNER JOIN respuesta dm ON f.dm = dm.id "
                + "	INNER JOIN paciente p ON p.id = f.paciente "
                + "	INNER JOIN estadoCivil e ON e.id = p.estadoCivil "
                + "	INNER JOIN usuario u ON u.id = f.usuario "
                + "WHERE "
                + "p.rut = '" + rut + "'");

        FichaSelect f = null;

        if (rs.next()) {
            f = new FichaSelect();
            f.setId(rs.getInt(1));
            f.setNombrePaciente(rs.getString(2));
            f.setSexo(rs.getString(3));
            f.setFecha(rs.getTimestamp(4));
            f.setDomicilio(rs.getString(5));
            f.setRut(rs.getString(6));
            f.setEstado_civil(rs.getString(7));
            f.setActividad(rs.getString(8));
            f.setFono(rs.getString(9));
            f.setFecha_ficha(rs.getTimestamp(10));
            f.setEncargado(rs.getString(11));
            f.setHta(rs.getString(12));
            f.setDm(rs.getString(13));
            f.setTipoDiabetes(rs.getString(14));
            f.setAnioEvolucion(rs.getString(15));
            f.setPacienteMixto(rs.getBoolean(16));
            f.setControl(rs.getBoolean(17));
            f.setFarmacoterapia(rs.getString(18));
            f.setOtrasPatologicas(rs.getString(19));
            f.setAlteracionesOrtopedicas(rs.getString(20));
            f.setHabitos_nocivos(rs.getString(21));
            f.setTalla(rs.getString(22));
            f.setImc(rs.getString(23));
            f.setAmputacion(rs.getBoolean(24));
            f.setUbicacion_Amputacion(rs.getString(25));
            f.setNro_Calzando(rs.getString(26));
            f.setVarices(rs.getBoolean(27));
            f.setHeridas(rs.getBoolean(28));
            f.setUbicacion_Heridas(rs.getString(29));
            f.setTipo_Heridas(rs.getString(30));
            f.setTratamiento(rs.getBoolean(31));
            f.setNevos(rs.getBoolean(32));
            f.setUbicacion_nevos(rs.getString(33));
            f.setMaculas(rs.getBoolean(34));
            f.setTipo_Maculas(rs.getString(35));

        }
        con.close();

        return f;
    }

    public List<EstadoCivil> getEstadosCiviles() throws SQLException {
        List<EstadoCivil> list = new ArrayList<>();

        query = "select * from estadoCivil";

        rs = con.ejecutarSelect(query);

        EstadoCivil es;

        while (rs.next()) {
            es = new EstadoCivil();

            es.setId(rs.getInt(1));
            es.setNombre(rs.getString(2));

            list.add(es);
        }
        con.close();

        return list;

    }

    public List<Respuesta> getRespuestas() throws SQLException {
        List<Respuesta> list = new ArrayList<>();

        query = "select * from respuesta";

        rs = con.ejecutarSelect(query);

        Respuesta r;

        while (rs.next()) {
            r = new Respuesta();

            r.setId(rs.getInt(1));
            r.setNombre(rs.getString(2));

            list.add(r);
        }
        con.close();

        return list;
    }

    public List<Perfil> getPerfiles() throws SQLException {

        query = "SELECT * FROM perfil;";
        rs = con.ejecutarSelect(query);

        List<Perfil> list = new ArrayList<>();
        Perfil p;

        while (rs.next()) {
            p = new Perfil();
            p.setId(rs.getInt(1));
            p.setNombre(rs.getString(2));
            list.add(p);
        }
        con.close();
        return list;
    }

    public String getPerfil(int id) throws SQLException {

        query = "SELECT nombre FROM perfil WHERE id =" + id;

        rs = con.ejecutarSelect(query);
        String nombre = null;
        Perfil p;
        if (rs.next()) {
            p = new Perfil();
            p.setNombre(rs.getString(1));
            nombre = p.getNombre();
        }
        return nombre;
    }

    public List<TratamientoOrtonixia> getTratamientosOrtonoxia() throws SQLException {

        query = "SELECT * FROM tratamientoOrtonixia;";
        rs = con.ejecutarSelect(query);

        List<TratamientoOrtonixia> list = new ArrayList<>();
        TratamientoOrtonixia to;

        while (rs.next()) {
            to = new TratamientoOrtonixia();
            to.setId(rs.getInt(1));
            to.setNombre(rs.getString(2));
            list.add(to);
        }
        con.close();

        return list;
    }

    private int getUltimoIdPaciente() throws SQLException { //Genera id del ultimo paciente Creado
        int ultimaId = 0;
        String lastId = "SELECT MAX(id) FROM paciente;";
        rs = con.ejecutarSelect(lastId);
        Paciente p;
        if (rs.next()) {
            p = new Paciente();
            p.setId(rs.getInt(1));
            ultimaId = p.getId();
        }
        con.close();
        return ultimaId;
    }

    public void crearUsuario(Usuario u) throws SQLException {
        query = "insert into usuario value(null,'" + u.getRut() + "','" + u.getNombre() + "'," + u.getPerfil() + ")";

        con.ejecutar(query);
    }

    public static Timestamp dateToTimeStamp(Date fecha) {
        return new Timestamp(fecha.getTime());
    }

    public String getMes(String mes) {
        //Obtiene el nº de mes según el nombre del mes :x
        switch (mes) {
            case "enero":
                mes = "1";
                break;
            case "febrero":
                mes = "2";
                break;
            case "marzo":
                mes = "3";
                break;
            case "abril":
                mes = "4";
                break;
            case "mayo":
                mes = "5";
                break;
            case "junio":
                mes = "6";
                break;
            case "julio":
                mes = "7";
                break;
            case "agosto":
                mes = "8";
                break;
            case "septiembre":
                mes = "9";
                break;
            case "octubre":
                mes = "10";
                break;
            case "noviembre":
                mes = "11";
                break;
            case "diciembre":
                mes = "12";
                break;
        }

        return mes;
    }

    public int getIdFicha(String idPaciente) throws SQLException {
        int idFicha = 0;
        query = "select id from ficha where paciente =" + idPaciente;

        rs = con.ejecutarSelect(query);
        if (rs.next()) {
            idFicha = rs.getInt(1);
        }

        return idFicha;
    }

    public List<DatosReporteUso> getDatosReporteUso(String fecIni, String fecFin) throws SQLException {

        List<DatosReporteUso> list = new ArrayList<>();
        DatosReporteUso dr;
        query = "SELECT "
                + "usuario.rut as Rut, usuario.nombre as Nombre, IFNULL(COUNT(ficha.id),0) AS 'Cantidad Fichas' "
                + "FROM "
                + "usuario LEFT JOIN ficha ON ficha.usuario = usuario.id "
                + "WHERE ficha.fecha between '" + fecIni + "' and '" + fecFin + "' "
                + "GROUP BY usuario.rut,  usuario.nombre;";
        String query2 = "SELECT IFNULL(COUNT(atencionPodologica.id),0) AS 'Cantidad AP' "
                + "FROM usuario LEFT JOIN atencionPodologica ON atencionPodologica.usuario = usuario.id "
                + "WHERE atencionPodologica.fecha between '" + fecIni + "' and '" + fecFin + "' "
                + "GROUP BY usuario.rut,  usuario.nombre;";

        rs = con.ejecutarSelect(query);
        ResultSet rs2 = con.ejecutarSelect(query2);

        while (rs.next() && rs2.next()) {
            dr = new DatosReporteUso();
            dr.setRut(rs.getString(1));
            dr.setNombre(rs.getString(2));
            dr.setCantidadFichas(rs.getInt(3));
            dr.setCantidadAtencionesPodologicas(rs2.getInt(1));
            list.add(dr);
        }
        con.close();
        return list;
    }

    public static void main(String[] args) throws SQLException {
        
    }
    
    public int getIdFichaById(String idPaciente) throws SQLException {

        query = "SELECT id FROM ficha "
                + "WHERE paciente = " + idPaciente;

        rs = con.ejecutarSelect(query);
        int id = -1;

        if (rs.next()) {
            id = rs.getInt(1);
        }

        con.close();

        return id;
    }

    public AtencionPodologicaSelect getAtencionPodologicaBy(String idAtencion) throws SQLException {
        query = "SELECT \n" +
                "    atencionPodologica.id 				AS ID, \n" +
                "    atencionPodologica.ficha 				AS 'Nº Ficha',\n" +
                "    u1.nombre 						AS Creador, \n" +
                "    atencionPodologica.fecha 				AS Fecha, \n" +
                "    atencionPodologica.presion 			AS Presión, \n" +
                "    atencionPodologica.pulsoRadial 			AS 'Pulso Radial', \n" +
                "    atencionPodologica.pulsoPedio_d                    AS 'P. Pedio (d)', \n" +
                "    atencionPodologica.pulsoPedio_i                    AS 'P. Pedio (i)', \n" +
                "    atencionPodologica.peso				AS Peso, \n" +
                "    atencionPodologica.sens_d 				AS 'Sensibilidad (d)', \n" +
                "    atencionPodologica.sens_i 				AS 'Sensibilidad (i)',\n" +
                "    atencionPodologica.tpodal_d 			AS 'Tº Podal (d)', \n" +
                "    atencionPodologica.tpodal_i 			AS 'Tº Podal (i)',\n" +
                "    atencionPodologica.curacion 			AS Curación, \n" +
                "    atencionPodologica.coloqPuente 			AS 'Coloc. Puente', \n" +
                "    atencionPodologica.resecado 			AS Resecado, \n" +
                "    atencionPodologica.enucleacion 			AS Enucleación, \n" +
                "    atencionPodologica.devastado 			AS 'Dev. Ungueal', \n" +
                "    atencionPodologica.maso 				AS 'Masoterapia o Masaje', \n" +
                "    atencionPodologica.espiculoectomia                 AS 'Espiculoectomía (Grado)', \n" +
                "    atencionPodologica.analgesia 			AS 'Analgesia (Tipo)', \n" +
                "    atencionPodologica.colocacionAcrilico              AS 'Colocación Acilico', \n" +
                "    atencionPodologica.bandaMolecular                  AS 'Colocac. Banda Molec.', \n" +
                "    tratamientoOrtonixia.nombre 			AS 'C. Bracket/Cambio Elast.', \n" +
                "    atencionPodologica.poli 				AS 'C. Policarboxilato', \n" +
                "    atencionPodologica.observaciones                   AS Observaciones,"
                + "  atencionPodologica.lugarAtencion,"
                + "  atencionPodologica.detalleLugar  \n" +
                "FROM \n" +
                "    atencionPodologica\n" +
                "    INNER JOIN ficha                ON atencionPodologica.ficha = ficha.id\n" +
                "    INNER JOIN usuario u1           ON ficha.usuario = u1.id\n" +
                "    INNER JOIN usuario u2           ON atencionPodologica.usuario = u2.id\n" +
                "    INNER JOIN tratamientoOrtonixia ON atencionPodologica.tratamientoOrtonixia = tratamientoOrtonixia.id\n" +
                "WHERE \n" +
                "    atencionPodologica.id = "+idAtencion+";";

        rs = con.ejecutarSelect(query);
        AtencionPodologicaSelect a = null;
        if (rs.next()) {
            a = new AtencionPodologicaSelect();
            a.setId(rs.getInt(1));
            a.setFicha(rs.getInt(2));
            a.setUsuario(rs.getString(3));
            a.setFecha(rs.getTimestamp(4));
            a.setPresion(rs.getString(5));
            a.setPulsoRadial(rs.getInt(6));
            a.setPulsoPedio_d(rs.getInt(7));
            a.setPulsoPedio_i(rs.getInt(8));
            a.setPeso(rs.getFloat(9));
            a.setSens_d(rs.getBoolean(10));
            a.setSens_i(rs.getBoolean(11));
            a.settPoda1_d(rs.getString(12));
            a.settPoda1_i(rs.getString(13));
            a.setCuracion(rs.getBoolean(14));
            a.setColocacionPuente(rs.getBoolean(15));
            a.setResecado(rs.getBoolean(16));
            a.setEnucleacion(rs.getBoolean(17));
            a.setDevastado(rs.getBoolean(18));
            a.setMaso(rs.getBoolean(19));
            a.setEspiculoectomia(rs.getBoolean(20));
            a.setAnalgesia(rs.getBoolean(21));
            a.setColocacionAcrilico(rs.getBoolean(22));
            a.setBandaMolecular(rs.getBoolean(23));

            a.setTratamientoOrtonixia(rs.getString(24));
            a.setPoli(rs.getBoolean(25));
            a.setObservaciones(rs.getString(26));
            a.setLugarAtencion(rs.getString(27));
            a.setDetalleLugar(rs.getString(28));
        }
        con.close();
        return a;
    }

}
//Si alguno ve que falta algo, Digalo por wsp o en algun momento, non se callen nada Saludos
