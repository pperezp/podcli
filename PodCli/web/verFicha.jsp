<%@page import="model.bd.Usuario"%>
<%@page import="model.bd.FichaSelect"%>
<%@page import="model.bd.Respuesta"%>
<%@page import="model.bd.Data"%>
<%@page import="model.bd.EstadoCivil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="validar.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Ver Ficha</title>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top " role="navigation">

            <div class="navbar-header">
                <a class="navbar-brand" href="#" style="padding-bottom: 10px">
                    <span><img width = 30px alt="Brand" src="http://www.prodx.cl/images/ust.png"></span>
                </a>
            </div>

            <p class="navbar-text pull-left">PodCli</p>

            <ul class="nav navbar-nav navbar-left">
                <!-- <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li> -->
                <!-- Redirigir a crear ficha -->
                <li><a href="#">Link de prueba</a></li><%--                    if (u != null) {
                        out.println("Usuario Actual: " + u.getNombre());
                        out.println("<br>Perfil:");
                        Data d = new Data();

                        out.println(d.getPerfil(u.getPerfil()));
                    }
                        --%>

                        <%--
                            if (u.getPerfil() == 2 || u.getPerfil() == 3) {
                        --%>
                        <a href="crearUsuario.jsp">Crear Usuario</a>
                        <a href="reporteHistorico.jsp">Reporte histórico</a>
                        <%--
                            }
                        %></a></li> --%>
                <!-- Redirigir a reporte de uso -->
                <li><a href="#">Reporte de uso</a></li>
                <li><a href="verficha.jsp">Ver Ficha</a></li>
                <li><a href="verAtencion.jsp">Ver Atención</a></li>
                <li><a href="atencionPodologica.jsp">Atencion Podologica</a></li>
            </ul>

            <!-- Falta redirigir a donde se muestran los resultados de la busqueda -->
            <ul class="nav navbar-nav navbar-center">
                <li><form class="navbar-form pull-right" role="search" action="" method="get">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Buscar">
                        </div>
                        <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
                    </form>
                </li>
            </ul>
            <!-- <ul class="nav navbar-nav navbar-right">
                <li><button type="button" class="btn btn-danger navbar-btn"><a href="">Cerrar sesión</a></button></li>
            </ul> -->

            <ul class="nav navbar-nav navbar-right" style="padding-right: 10px">
                <li>
                    <form class="navbar-form pull-right" action="index.jsp">
                        <button type="submit" class="btn btn-danger">Cerrar sesión</button>
                    </form>
                </li>
            </ul>
        </nav>

        <%--
            Data d = new Data();

            String rut = request.getParameter("rut");
            FichaSelect f = d.getFicha(rut);
        --%>

        <br><br><br>
        <div class="container">
            <h1><%--=f.getNombrePaciente()--%>
                Fecha de Registro: <%--= Data.getFormattedDate(f.getFecha_ficha(), true)--%><br>
            </h1>
            <div class="col-md-12">
                <button type="button" class="btn btn-success col-md-12" data-toggle="collapse" data-target="#antecedentes">
                    Antecedentes Personales
                </button>
            </div>        
            <!----------------------Antecedentes Personales----------------------------------------------->
            <div class="col-md-12 collapse" id="antecedentes">
                <div class="panel panel-primary">
                    <div class="panel-heading panel-title">
                        Antecedentes Personales
                    </div>
                    <div class="panel-body">
                        <div class="col-md-6">
                            Rut: <input class="form-control" type="text" name="txtRut" value="<%--= f.getRut()--%>" readonly>
                            Nombre: <input class="form-control" type="text" name="txtNombre" value="<%--= f.getNombrePaciente()--%>" readonly>
                            Fecha Nacimiento: <input class="form-control" type="date" name="txtFechaNacimineto" value="<%--= f.getFecha()--%>" readonly>
                            Domicilio: <textarea class="form-control" name="txtDomicilio" readonly><%--= f.getDomicilio()--%></textarea>
                        </div>
                        <div class="col-md-6">
                            Estado Civil: <input class="form-control" type="text" value="<%--= f.getEstado_civil()--%>" readonly> 
                            Actividad: <input class="form-control" type="text" name="txtActividad" value="<%--= f.getActividad()--%>" readonly>
                            Telefonos:  <input class="form-control" type="text" name="txtTelefonos" value="<%--= f.getFono()--%>" readonly>
                            Fecha de Registro: <input class="form-control"  value="<%--= f.getFecha_ficha()--%>" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <!----------------------Antecedentes Personales----------------------------------------------->



            <!----------------------Antecedentes Morbidos----------------------------------------------->
            <div class="col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading panel-title">
                        Antecedentes Morbidos
                    </div>
                    <div class="panel-body">
                        <div class="col-md-6">
                            HTA: <input class="form-control" type="text" value="<%--= f.getHta()--%>" readonly>
                            D.M: <input class="form-control" type="text" value="<%--= f.getDm()--%>" readonly>
                            Tipo: <input class="form-control" type="text" value=" <%--
                                String tipo = f.getTipoDiabetes();

                                if (tipo.equals("0")) {
                                    out.println("N/A");
                                } else {
                                    out.println(tipo);
                                }

                                         --%>" readonly>   
                            Años Evolución: <input class="form-control form-control-sm" type="number" name="txtAnioEvolucion" value="<%--= f.getAnioEvolucion()--%>" readonly>
                            Paciente Mixto:<input class="form-control" type="text" value="<%--=(f.getPacienteMixto() ? "SI" : "NO")--%>" readonly>
                            Control:<input class="form-control" type="text" value="<%--=(f.getControl() ? "SI" : "NO")--%>" readonly>
                        </div>
                        <div class="col-md-6">
                            Farmacoterapia: <textarea class="form-control" name="txtFarmacoterapia" readonly><%--= f.getFarmacoterapia()--%></textarea>
                            Otras Patologías y Farmacoterapia: <textarea class="form-control" name="txtOtrasPatologíasYFarmacoterapia" readonly><%--= f.getOtrasPatologicas()--%></textarea>
                            Alteraciones Ortopédicas: <textarea class="form-control" name="txtAlteracionesOrtopédicas" readonly><%--= f.getAlteracionesOrtopedicas()--%></textarea>
                            Habitos Nocivos: <textarea class="form-control" name="txtHabitosNocivos" readonly><%--= f.getHabitos_nocivos()--%></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <!----------------------Antecedentes Morbidos----------------------------------------------->


            <!----------------------Examen Fisico General----------------------------------------------->
            <div class="col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading panel-title">
                        Examen Físico General
                    </div>
                    <div class="panel-body">
                        <div class="col-md-6">
                            Talla: <input class="form-control" type="number" step="any" name="txtTalla" value="<%--= f.getTalla()--%>" readonly>
                            IMC: <input class="form-control" type="number" step="any" name="txtIMC" value="<%--= f.getImc()--%>" readonly>
                            Amputación: <input class="form-control" type="text" value="<%--=(f.getAmputacion() ? "SI" : "NO")--%>" readonly>
                            Ubicación Amputación: <input class="form-control" type="text" name="txtUbicacionAmputacion" value="<%--= f.getUbicacion_Amputacion()--%>"readonly>
                            N° Calzado: <input class="form-control" type="number" name="txtNumCalzado" value="<%--= f.getNro_Calzando()--%>" readonly>
                            Varices Extremo Inferior: <input class="form-control" type="text" value="<%--= (f.getVarices() ? "SI" : "NO")--%>" readonly>
                            Heridas:<input class="form-control" type="text" value="<%--= (f.getHeridas() ? "SI" : "NO")--%>" readonly>
                        </div>
                        <div class="col-md-6">
                            Ubicación Heridas: <input class="form-control" type="text" name="txtUbicacionHeridas" value="<%--= f.getUbicacion_Heridas()--%>" readonly>
                            Tipo: <input class="form-control" type="text" name="txtTipoHeridas" value="<%--= f.getTipo_Heridas()--%>" readonly>
                            Tratamiento:<input class="form-control" type="text" value="<%--= (f.getTratamiento() ? "SI" : "NO")--%>" readonly>
                            Nevos: <input class="form-control" type="text" value="<%--= (f.getNevos() ? "SI" : "NO")--%>" readonly>
                            Ubicación Nevos: <input class="form-control" type="text" name="txtUbicacionNevos" value="<%--= f.getUbicacion_nevos()--%>" readonly>
                            Máculas:<input class="form-control" type="text" value="<%--= (f.getMaculas() ? "SI" : "NO")--%>" readonly>
                            Tipo: <input class="form-control" type="text" name="txtTipoMaculas" value="<%--= f.getTipo_Maculas()--%>" readonly>
                        </div>
                    </div>
                </div>
            </div>

             <!----------------------Examen Fisico General----------------------------------------------->
           
                        <center><a type="button" class="btn btn-primary btn-lg" href="buscarPaciente.jsp">Volver</a></center>
        
        </div>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
       

        
    </body>
</html>
