package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.util.Request;
import cr.ac.una.unaplanilla.util.Respuesta;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Desktop;

/**
 *
 * @author cbcar
 */
public class TipoPlanillaService {

    public Respuesta getTipoPlanilla(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("TipoPlanillaController/tipoplanilla", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            TipoPlanillaDto tipoPlanilla = (TipoPlanillaDto) request.readEntity(TipoPlanillaDto.class);
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanilla);
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE,
                    "Error obteniendo el tipo de planilla [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el tipo de planilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta getTipoPlanillas(String codigo, String descripcion, String plaxmes, String idemp, String cedula) {
        try {

            return new Respuesta(true, "", "", "TipoPlanillas", null /* tipoPlanillasDto */);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo tipos de planillas.",
                    ex);
            return new Respuesta(false, "Error obteniendo tipos de planillas.", "getTipoPlanillas " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            Request request = new Request("TipoPlanillaController/tipoplanilla");
            request.post(tipoPlanillaDto);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            TipoPlanillaDto tipoPlanilla = (TipoPlanillaDto) request.readEntity(TipoPlanillaDto.class);
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanilla);
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE,
                    "Error guardando el tipo de planilla.", ex);
            return new Respuesta(false, "Error guardando el tipo de planilla.",
                    "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("TipoPlanillaController/tipoplanilla", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "", "TipoPlanilla", null);
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE,
                    "Error eliminando el tipo de planilla [" + id + "]", ex);
            return new Respuesta(false, "Error eliminando el tipo de planilla.",
                    "eliminarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta reporte() {
        try {
            Request request = new Request("TipoPlanillaController/tipoplanillas/reporte");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            byte[] archivoReporte = (byte[]) request.readEntity(byte[].class);
            String nombreArchivo = "reporte_tipo_planillas.docx";
            try (FileOutputStream fos = new FileOutputStream(nombreArchivo)) {
                fos.write(archivoReporte);
            }
            File file = new File(nombreArchivo);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            }
            return new Respuesta(true, "Reporte descargado y abierto exitosamente.", "", "TipoPlanilla", nombreArchivo);

        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE,
                    "Error obteniendo el reporte de tipos de planillas.", ex);
            return new Respuesta(false, "Error obteniendo el reporte de tipos de planillas.",
                    "reporte " + ex.getMessage());
        }
    }

}
