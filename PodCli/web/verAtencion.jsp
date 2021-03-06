<%@page import="model.bd.Paciente"%>
<%@page import="model.bd.TratamientoOrtonixia"%>
<%@page import="model.bd.AtencionPodologicaSelect"%>
<%@page import="model.bd.Data"%>
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


        <title>Ver atención</title>
    </head>
    <style>
        .navbar-header{
            padding-top: 7px; 
            padding-left: 10px
        }

        .rojo{
            color:red;
        }

        .verde{
            color:green;
        }
    </style>
    <body>
            <nav class="navbar navbar-default navbar-fixed-top " role="navigation">
            <div class="navbar-header">
                <a href="#" class="navbar-left">
                    <span><img width=80px height=35px src="imagen/ist.jpg"></span>
                </a>
            </div>

            <p class="navbar-text pull-left">PodCli</p>
            
            <%@include file="modules/actualUser.jsp" %>

            <ul class="nav navbar-nav navbar-left">
                <!-- <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li> -->
                <!-- Redirigir a crear ficha -->
                <li><a href="crearFicha.jsp">Crear Ficha</a></li>

                <!-- Se agregaron recientemente en el nav -->
                <li>
                    <%                        if (u.getPerfil() == 2 || u.getPerfil() == 3) {
                    %>
                    <a href="crearUsuario.jsp">Crear Usuario</a>
                    <%
                        }
                    %></li>

                <%if(u.getPerfil() == 2 || u.getPerfil() == 3){%>
                    <li><a href="reporteHistorico.jsp">Reporte Histórico</a></li>
                <%}%>
                <!-- Se agregaron recientemente en el nav -->

                <!-- Redirigir a reporte de uso -->                

            </ul>
            <!-- <ul class="nav navbar-nav navbar-right">
                <li><button type="button" class="btn btn-danger navbar-btn"><a href="">Cerrar sesión</a></button></li>
            </ul> -->
            <ul class="nav navbar-nav navbar-right" style="padding-right: 10px">
                <li>
                    <form class="navbar-form pull-right" action="cerrar.do">
                        <button type="submit" class="btn btn-danger">Cerrar sesión</button>
                    </form>
                </li>
            </ul>
            <!-- Falta redirigir a donde se muestran los resultados de la busqueda -->

            <%@include file="modules/buscarNav.jsp" %>
        </nav>

        <%            
            Data d = new Data();

            String idAtencionPod = request.getParameter("idAntPod");
            String idPaciente = request.getParameter("idPaciente");

            AtencionPodologicaSelect a = d.getAtencionPodologicaBy(idAtencionPod);
            Paciente p = d.getPaciente(idPaciente);
        %>

        <br><br><br>
        <div class="container" >
            <form action="registrarAtencion.do" method="post">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h1><%= p.getNombre()%></h1>
                        <h2>Atención podológica</h2>
                        <h4><%= Data.getFormattedDate(a.getFecha(), true)%></h4>
                    </div>
                    <div class="panel-body">
                        
                        <div class="form-group col-md-6">
                            Lugar de atención:
                            <input class="form-control" type="text" value="<%= a.getLugarAtencion() %>" name="lugarDeAtencion" readonly>
                        </div>
                            
                        <div class="form-group col-md-6">
                            Detalle del lugar del terreno:
                            <textarea class="form-control" type="text" name="detalleLugar" readonly><%= a.getDetalleLugar()%></textarea>
                        </div>

                        <div class="form-group col-md-3">
                            Presión Arterial:
                            <input class="form-control" type="text" step="any" value="<%= a.getPresion()%>" name="presion" readonly>
                        </div>

                        <div class="form-group col-md-3">
                            Pulso Radial (PPM):
                            <input class="form-control" type="number"  value="<%= a.getPulsoRadial()%>" name="pulso" readonly>
                        </div>

                        <div class="form-group col-md-3">
                            Peso:
                            <input class="form-control" type="number" value="<%= a.getPeso()%>" step="any" name="peso" readonly>
                        </div>

                        <div class="form-group col-md-3">
                            Pulso Pedio Derecho (PPM):
                            <input class="form-control" type="number" value="<%= a.getPulsoPedio_d()%>" step="any" name="ppd" readonly>
                        </div>

                        <div class="form-group col-md-4">
                            Pulso Pedio Izquierdo (PPM):
                            <input class="form-control" type="number" value="<%= a.getPulsoPedio_i()%>" name="ppi" readonly>
                        </div>

                        <div class="form-group col-md-4">
                            T° Podal Derecho:
                            <input class="form-control" type="text" value="<%= a.gettPoda1_d()%>" step="any" name="tpd" readonly>
                        </div>

                        <div class="form-group col-md-4">
                            T° Podal Izquierdo:
                            <input class="form-control" type="text" value="<%= a.gettPoda1_i()%>" step="any" name="tpi" readonly>
                        </div>

                        <div>
                            <!--
                            <div class="panel-heading">
                                <h3>Antecendentes</h3>
                            </div> 
                            -->
                            
                            <div class="form-group col-md-4">
                                <text class="<%=(a.isSens_d() ? "verde" : "rojo")%>">Sensibilidad Pie Derecho (<%=(a.isSens_d() ? "Si" : "No")%>)
                            </div>

                           
                            <div class="form-group col-md-4">
                                <text class="<%=(a.isSens_i() ? "verde" : "rojo")%>">Sensibilidad Pie Izquierdo  (<%=(a.isSens_i() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isCuracion() ? "verde" : "rojo")%>">Curación (<%=(a.isCuracion() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isResecado() ? "verde" : "rojo")%>">Resecado (<%=(a.isResecado() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isEnucleacion() ? "verde" : "rojo")%>">Enucleación (<%=(a.isEnucleacion() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isDevastado() ? "verde" : "rojo")%>">Devastado Ungueal (<%=(a.isDevastado() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isMaso() ? "verde" : "rojo")%>">Masoterapia o Masaje (<%=(a.isMaso() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isEspiculoectomia() ? "verde" : "rojo")%>">Espiculoectomía (<%=(a.isEspiculoectomia() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isAnalgesia() ? "verde" : "rojo")%>">Analgesia (<%=(a.isAnalgesia() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isColocacionAcrilico() ? "verde" : "rojo")%>">Colocacion Acrilico (<%=(a.isColocacionAcrilico() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isBandaMolecular() ? "verde" : "rojo")%>">Colocacion Banda Molecular (<%=(a.isBandaMolecular() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isColocacionPuente() ? "verde" : "rojo")%>">Colocacion Puente (<%=(a.isColocacionPuente() ? "Si" : "No")%>)
                            </div>

                            <div class="form-group col-md-4">
                                <text class="<%=(a.isPoli() ? "verde" : "rojo")%>">Colocacion Policarboxilato (<%=(a.isPoli() ? "Si" : "No")%>)
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            Tratamiento Ortonixia:
                            <input class="form-control" type="text" value="<%=a.getTratamientoOrtonixia()%>" readonly >
                        </div>

                        <div class="form-group col-md-12">
                            Observaciones:
                            <textarea class="form-control" rows="2" name="obs" readonly><%= a.getObservaciones()%></textarea>
                        </div>

                    </div>

                </div>
            </form>
        </div>

        <%%>
        <form action="historicoAtencion.jsp" method="post">
            <input type="hidden" name="idPaciente" value="<%=idPaciente%>">
            <center>
                <button type="submit" class="btn btn-primary" value="volver">Volver</button>
            </center>
        </form>
    </body>
</html>
