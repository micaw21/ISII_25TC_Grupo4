package com.stanreybackend.stanreyapi.controller;

import java.util.List;

import com.stanreybackend.stanreyapi.DTO.FacturaDTO;
import com.stanreybackend.stanreyapi.DTO.DetalleFacturaDTO;

// Clase dedicada a la transaccion y manejo de datos
// recibidos a la hora de crear una nueva Factura con multiples DetalleFactura
public class FacturaRequest {

    private FacturaDTO facturaDTO;
    private List<DetalleFacturaDTO> detallesDTO;

    public FacturaDTO getFacturaDTO() {
        return facturaDTO;
    }

    public void setFacturaDTO(FacturaDTO facturaDTO) {
        this.facturaDTO = facturaDTO;
    }

    public List<DetalleFacturaDTO> getDetallesDTO() {
        return detallesDTO;
    }

    public void setDetallesDTO(List<DetalleFacturaDTO> detallesDTO) {
        this.detallesDTO = detallesDTO;
    }
}