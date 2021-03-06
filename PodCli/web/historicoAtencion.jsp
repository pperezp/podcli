<%@page import="model.bd.Usuario"%>
<%@page import="model.bd.AtencionPodologicaSelect"%>
<%@page import="java.util.List"%>
<%@page import="model.bd.Ficha"%>
<%@page import="model.bd.Paciente"%>
<%@page import="model.bd.Data"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="validar.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Histórico Atención podológica</title>
    </head>
    <style>
        .navbar-header{

            padding-top: 7px; 
            padding-left: 10px

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

                <%if (u.getPerfil() == 2 || u.getPerfil() == 3) {%>
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
        <%            Data d = new Data();

            String idPaciente = request.getParameter("idPaciente");
            int idFicha = d.getIdFichaById(idPaciente);
            List<AtencionPodologicaSelect> atenciones = d.getAtencionesPodologicas(idFicha);

            String rutPaciente = d.getPaciente(idPaciente).getRut();

            String nomPac = d.getNombreBy(String.valueOf(idFicha));
        %>
        
        <br><br><br>
        <div class="container">
            <h1 class="col-md-12">Atenciones podológicas de <b><%=nomPac%></b></h1>
            <div class="col-md-12">
                <form action="verFicha.jsp" class="form-inline" method="post">
                    <input type="hidden" name="rut" value="<%=rutPaciente%>">                     
                    <input type="submit" value="Ver ficha" class="btn btn-primary" style="width: 200px;">
                </form>
            </div>

            <table class="table table-striped">
                <tr>
                    <th>Id</th>
                    <th>Fecha</th>
                    <th>Observaciones</th>
                    <th>Atendido por</th>
                    <th>Ver detalle</th>
                </tr>
                <%
                    for (AtencionPodologicaSelect aps : atenciones) {
                        out.println("<tr>");
                        out.println("<td>" + aps.getId() + "</td>");
                        out.println("<td>" + Data.getFormattedDate(aps.getFecha(), true) + "</td>");
                        out.println("<td>" + aps.getObservaciones() + "</td>");
                        out.println("<td>" + aps.getAtendidoPor() + "</td>");
                        out.println("<td>");
                        out.println("<form action='verAtencion.jsp' method='post' class='form-inline'>");
                        out.println("<input type='submit' value='Ver detalle' class='btn btn-success'>");
                        out.println("<input type='hidden' name='idAntPod' value=" + aps.getId() + ">");
                        out.println("<input type='hidden' name='idPaciente' value=" + idPaciente + ">");
                        out.println("</form>");
                        out.println("</td>");
                        out.println("</tr>");
                    }
                %>
            </table>
        </div> 
    </body>
</html>