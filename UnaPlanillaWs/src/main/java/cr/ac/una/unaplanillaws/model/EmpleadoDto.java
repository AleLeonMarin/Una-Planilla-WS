/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Carlos
 */

@Schema(description = "Esta clase contiene la información de un empleado")
public class EmpleadoDto {

    @Schema(description = "Identificador del empleado", example = "1")
    private Long id;
    @Schema(description = "Nombre del empleado", example = "Carlos")
    private String nombre;
    @Schema(description = "Primer apellido del empleado", example = "Carranza")
    private String primerApellido;
    @Schema(description = "Segundo apellido del empleado", example = "Vargas")
    private String segundoApellido;
    @Schema(description = "Cédula del empleado", example = "112341234")
    private String cedula;
    @Schema(description = "Género del empleado", example = "M", allowableValues = "M,F")
    private String genero;
    @Schema(description = "Correo del empleado", example = "carlos.carranza@gmai.com")
    private String correo;
    @Schema(description = "Administrador del empleado", example = "S", allowableValues = "S,N")
    private String administrador;
    @Schema(description = "Usuario del empleado", example = "carlos.carranza")
    private String usuario;
    @Schema(description = "Clave del empleado", example = "123456")
    private String clave;
    @Schema(description = "Fecha de ingreso del empleado", example = "2021-09-01")
    private LocalDate fechaIngreso;
    @Schema(description = "Fecha de salida del empleado", example = "2021-09-01")
    private LocalDate fechaSalida;
    @Schema(description = "Estado del empleado", example = "A")
    private String estado;
    @Schema(description = "Versión del registro", example = "1")
    private Long version;
    @Schema(description = "Modificado", example = "false")
    private Boolean modificado;
   // TODO
    @Schema(description = "Fecha de modificación del empleado", example = "2021-09-01T10:15:30")
    private LocalDateTime fecha;
    private String token;

    public EmpleadoDto() {
        this.modificado = false;
        this.fecha = LocalDateTime.now();
    }

    public EmpleadoDto(Empleado empleado) {
        this();
        this.id = empleado.getId();
        this.nombre = empleado.getNombre();
        this.primerApellido = empleado.getPrimerApellido();
        this.segundoApellido = empleado.getSegundoApellido();
        this.cedula = empleado.getCedula();
        this.genero = empleado.getGenero();
        this.correo = empleado.getCorreo();
        this.administrador = empleado.getAdministrador();
        this.usuario = empleado.getUsuario();
        this.clave = empleado.getClave();
        this.fechaIngreso = empleado.getFechaIngreso();
        if (empleado.getFechaSalida()!= null) {
            this.fechaSalida = empleado.getFechaSalida();
        } else {
            this.fechaSalida = null;
        }
        this.estado = empleado.getEstado();
        this.version = empleado.getVersion();
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmpleadoDto other = (EmpleadoDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "EmpleadoDto{" + "id=" + id + ", nombre=" + nombre + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", cedula=" + cedula + '}';
    }

}
