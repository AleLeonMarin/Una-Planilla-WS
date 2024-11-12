/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.service;

import cr.ac.una.unaplanillaws.model.Empleado;
import cr.ac.una.unaplanillaws.model.EmpleadoDto;
import cr.ac.una.unaplanillaws.model.TipoPlanilla;
import cr.ac.una.unaplanillaws.model.TipoPlanillaDto;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.Respuesta;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.logging.Logger;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

/**
 *
 * @author Carlos
 */
// TODO
@Stateless
@LocalBean
public class TipoPlanillaService {

    private static final Logger LOG = Logger.getLogger(TipoPlanillaService.class.getName());

    @PersistenceContext(unitName = "UnaPlanillaWsPU")
    private EntityManager em;

    public Respuesta getTipoPlanilla(Long id) {
        try {
            Query qryTipoPlanilla = em.createNamedQuery("TipoPlanilla.findById", TipoPlanilla.class);
            qryTipoPlanilla.setParameter("id", id);

            TipoPlanilla tipoPlanilla = (TipoPlanilla) qryTipoPlanilla.getSingleResult();
            TipoPlanillaDto tipoPlanillaDto = new TipoPlanillaDto(tipoPlanilla);
            for (Empleado emp : tipoPlanilla.getEmpleados()) {
                tipoPlanillaDto.getEmpleados().add(new EmpleadoDto(emp));
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "TipoPlanilla", tipoPlanillaDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,
                    "No existe un tipo de planilla con el código ingresado.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el tipo de planilla.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                    "Ocurrio un error al consultar el tipo de planilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el empleado.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                    "Ocurrio un error al consultar el tipo de planilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            TipoPlanilla tipoPlanilla;
            if (tipoPlanillaDto.getId() != null && tipoPlanillaDto.getId() > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, tipoPlanillaDto.getId());
                if (tipoPlanilla == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,
                            "No se encontró el tipo de planilla a modificar.", "guardarTipoPlanilla NoResultException");
                }
                tipoPlanilla.actualizar(tipoPlanillaDto);
                for (EmpleadoDto emp : tipoPlanillaDto.getEmpleadosEliminados()) {
                    tipoPlanilla.getEmpleados().remove(new Empleado(emp.getId()));
                }
                if (!tipoPlanillaDto.getEmpleados().isEmpty()) {
                    for (EmpleadoDto emp : tipoPlanillaDto.getEmpleados()) {
                        if (emp.getModificado()) {
                            Empleado empleado = em.find(Empleado.class, emp.getId());
                            empleado.getTiposPlanilla().add(tipoPlanilla);
                            tipoPlanilla.getEmpleados().add(empleado);
                        }
                    }
                }
                tipoPlanilla = em.merge(tipoPlanilla);
            } else {
                tipoPlanilla = new TipoPlanilla(tipoPlanillaDto);
                em.persist(tipoPlanilla);
            }
            em.flush();
            em.refresh(tipoPlanilla);
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "TipoPlanilla",
                    new TipoPlanillaDto(tipoPlanilla));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el tipo de planilla.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                    "Ocurrio un error al guardar el tipo de planilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            TipoPlanilla tipoPlanilla;
            if (id != null && id > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, id);
                if (tipoPlanilla == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,
                            "No se encrontró el tipo de planilla a eliminar.",
                            "eliminarTipoPlanilla NoResultException");
                }
                em.remove(tipoPlanilla);
            } else {
                return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,
                        "Debe cargar el tipo de planilla a eliminar.", "eliminarTipoPlanilla NoResultException");
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                        "No se puede eliminar el tipo de planilla porque tiene relaciones con otros registros.",
                        "eliminarTipoPlanilla " + ex.getMessage());
            }

            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el tipo de planilla.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                    "Ocurrio un error al eliminar el tipo de planilla.", "eliminarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta generarReportePlanillas() {
        try {
            List<TipoPlanilla> tiposPlanilla = em.createNamedQuery("TipoPlanilla.findAll", TipoPlanilla.class).getResultList();
            if (tiposPlanilla.isEmpty()) {
                return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontraron planillas", "generarReportePlanillas");
            }

            XWPFDocument document = new XWPFDocument();

            // Título principal del reporte
            XWPFParagraph header = document.createParagraph();
            XWPFRun runHeader = header.createRun();
            header.setAlignment(ParagraphAlignment.LEFT);

            runHeader.setText("Una");
            runHeader.setColor("d62828");
            runHeader.setBold(true);
            runHeader.setFontSize(20);
            

            XWPFRun runHeader2 = header.createRun();
            runHeader2.setText(" Planilla"); // Añadir un espacio antes de "Planilla" para separación
            runHeader2.setColor("1d4ed8"); // Color azul para "Planilla"
            runHeader2.setBold(true);
            runHeader2.setFontSize(20);

            XWPFParagraph tituloPrincipal = document.createParagraph();
            XWPFRun runTituloPrincipal = tituloPrincipal.createRun();
            runTituloPrincipal.setText("Reporte de Planillas");
            runTituloPrincipal.setBold(true);
            runTituloPrincipal.setFontSize(20);
            runTituloPrincipal.setUnderline(UnderlinePatterns.SINGLE);
            tituloPrincipal.setAlignment(ParagraphAlignment.CENTER);

            // Generar contenido para cada planilla
            int planillaIndex = 1;
            for (TipoPlanilla tipoPlanilla : tiposPlanilla) {
                // Título de cada planilla
                XWPFParagraph tituloPlanilla = document.createParagraph();
                XWPFRun runTituloPlanilla = tituloPlanilla.createRun();
                runTituloPlanilla.setText(planillaIndex++ + ". Planilla: " + tipoPlanilla.getDescripcion());
                runTituloPlanilla.setBold(true);
                runTituloPlanilla.setFontSize(16);

                // Crear viñetas para detalles de la planilla
                agregarParrafoConViñeta(document, "Descripción: " + tipoPlanilla.getDescripcion());
                agregarParrafoConViñeta(document, "Planillas por Mes: " + tipoPlanilla.getPlanillaPorMes());
                agregarParrafoConViñeta(document, "Código: " + tipoPlanilla.getCodigo());

                // Crear tabla de empleados para la planilla
                XWPFParagraph tituloEmpleados = document.createParagraph();
                XWPFRun runTituloEmpleados = tituloEmpleados.createRun();
                runTituloEmpleados.setText("Empleados:");
                runTituloEmpleados.setBold(true);

                XWPFTable table = document.createTable();
                XWPFTableRow headerRow = table.getRow(0);
                headerRow.getCell(0).setText("Nombre");
                headerRow.addNewTableCell().setText("Apellidos");
                headerRow.addNewTableCell().setText("  Cédula");
                headerRow.addNewTableCell().setText("Género");

                for (Empleado empleado : tipoPlanilla.getEmpleados()) {
                    XWPFTableRow row = table.createRow();
                    row.getCell(0).setText(empleado.getNombre());
                    row.getCell(1).setText(empleado.getPrimerApellido() + " " + empleado.getSegundoApellido());
                    row.getCell(2).setText(empleado.getCedula());
                    row.getCell(3).setText(empleado.getGenero());
                }

                // Añadir un espacio entre cada planilla
                //document.createParagraph().createRun().addBreak(BreakType.COLUMN);
                XWPFParagraph espacioEntrePlanillas = document.createParagraph();
                XWPFRun runEspacio = espacioEntrePlanillas.createRun();
                runEspacio.addBreak();
                runEspacio.addBreak();
                runEspacio.addBreak();
                runEspacio.addBreak();
                runEspacio.addBreak();
            }

            // Mensaje de finalización de reporte
            XWPFParagraph footer = document.createParagraph();
            XWPFRun runFooter = footer.createRun();
            runFooter.setText("\nEste reporte fue generado automáticamente.");
            runFooter.setItalic(true);
            runFooter.setUnderline(UnderlinePatterns.WAVE);
            footer.setAlignment(ParagraphAlignment.CENTER);

            // Guardar documento
            try (FileOutputStream out = new FileOutputStream("ReportePlanillas.docx")) {
                document.write(out);
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "Reporte generado exitosamente", "", "Reporte", "ReportePlanillas.docx");

        } catch (Exception e) {
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Error al generar reporte", "generarReportePlanillas " + e.getMessage());
        }
    }

// Método auxiliar para crear un párrafo con viñeta
    private void agregarParrafoConViñeta(XWPFDocument document, String texto) {
        XWPFParagraph paragraph = document.createParagraph();

        // Configurar estilo de lista
        paragraph.setNumID(addBulletNumbering(document));
        paragraph.setIndentationLeft(500);

        XWPFRun run = paragraph.createRun();
        run.setText(texto);
        run.setFontSize(12);
    }

// Configuración de viñetas
    private BigInteger addBulletNumbering(XWPFDocument document) {
        XWPFNumbering numbering = document.createNumbering();
        CTAbstractNum abstractNum = CTAbstractNum.Factory.newInstance();
        abstractNum.setAbstractNumId(BigInteger.ZERO);

        CTLvl level = abstractNum.addNewLvl();
        level.setIlvl(BigInteger.ZERO);
        level.addNewNumFmt().setVal(STNumberFormat.BULLET);
        level.addNewLvlText().setVal("•");  // Símbolo de viñeta
        level.addNewStart().setVal(BigInteger.ONE);

        XWPFAbstractNum xwpfAbstractNum = new XWPFAbstractNum(abstractNum);
        BigInteger abstractNumID = numbering.addAbstractNum(xwpfAbstractNum);
        return numbering.addNum(abstractNumID);
    }

}
