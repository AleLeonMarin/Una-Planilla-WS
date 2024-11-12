package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.TipoPlanilla;
import cr.ac.una.unaplanillaws.model.TipoPlanillaDto;
import cr.ac.una.unaplanillaws.service.EmpleadoService;
import cr.ac.una.unaplanillaws.service.TipoPlanillaService;
import cr.ac.una.unaplanillaws.util.Secure;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.JwTokenHelper;
import cr.ac.una.unaplanillaws.util.Respuesta;
import cr.ac.una.unaplanillaws.util.Secure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ws.rs.core.Response;

//@Secure
@Path("/TipoPlanillaController")
@Tag(name = "TipoPlanila", description = "Operaciones Sobre Tipos de Planilla")
// @SecurityRequirement(name = "jwt-auth")
public class TipoPlanillaController {

    @EJB
    TipoPlanillaService tipoPlanillaService;

    @GET
    @Path("/tipoplanilla/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoPlanilla(@PathParam("id") Long id) {
        try {
            Respuesta res = tipoPlanillaService.getTipoPlanilla(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            TipoPlanillaDto tipoPlanilla = (TipoPlanillaDto) res.getResultado("TipoPlanilla");
            return Response.ok(tipoPlanilla).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE,
                    "Ocurrio un error al consultar el tipo de planilla.", ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                    .entity("Ocurrio un error al consultar el tipo de planilla.").build();
        }
    }

    // @GET
    // @Path("/tipoplanillas")
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // @Operation(description = "Obtiene todos los tipos de planilla")
    // @ApiResponses({
    // @ApiResponse(responseCode = "200", description = "Tipos de planilla
    // encontrados", content = @Content(mediaType = MediaType.APPLICATION_JSON,
    // schema = @Schema(implementation = TipoPlanillaService.class))),
    // @ApiResponse(responseCode = "404", description = "Tipos de planilla No
    // Encontrados", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
    // @ApiResponse(responseCode = "500", description = "Error interno al buscar los
    // tipos de planilla", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    // })
    // public Response getTipoPlanillas() {
    // try {
    // Respuesta res = tipoPlanillaService.
    // if (!res.getEstado()) {
    // return
    // Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
    // }
    // return Response.ok(res.getResultado()).build();
    // } catch (Exception ex) {
    // Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE,
    // "Ocurrio un error al consultar los tipos de planilla.", ex);
    // return
    // Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Ocurrio un
    // error al consultar los tipos de planilla.").build();
    // }
    // }

    @POST
    @Path("/tipoplanilla")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            Respuesta res = tipoPlanillaService.guardarTipoPlanilla(tipoPlanillaDto);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok((TipoPlanillaDto) res.getResultado("TipoPlanilla")).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE,
                    "Ocurrio un error al guardar el tipo de planilla.", ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                    .entity("Ocurrio un error al guardar el tipo de planilla.").build();
        }

    }

    @DELETE
    @Path("/tipoplanilla/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarTipoPlanilla(@PathParam("id") Long id) {
        try {
            Respuesta res = tipoPlanillaService.eliminarTipoPlanilla(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE,
                    "Ocurrio un error al eliminar el tipo de planilla.", ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                    .entity("Ocurrio un error al eliminar el tipo de planilla.").build();
        }
    }

    @GET
    @Path("/tipoplanillas/reporte")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReportePlanillas() {
        Respuesta respuesta = tipoPlanillaService.generarReportePlanillas();
        if (!respuesta.getEstado()) {
            return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
        }

        File file = new File((String) respuesta.getResultado("Reporte"));
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=" + file.getName());
        return response.build();
    }

}
