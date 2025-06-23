package com.stanreybackend.stanreyapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stanreybackend.stanreyapi.DTO.DetalleFacturaDTO;
import com.stanreybackend.stanreyapi.entity.DetalleFactura;
import com.stanreybackend.stanreyapi.repository.DetalleFacturaRepository;
import com.stanreybackend.stanreyapi.repository.FacturaRepository;
import com.stanreybackend.stanreyapi.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class DetalleFacturaService {

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Transactional
    public String addDetalleFactura(DetalleFacturaDTO detalleFacturaDTO) {
        DetalleFactura detalleFactura = new DetalleFactura(
                detalleFacturaDTO.getIdDetalleFactura(),
                detalleFacturaDTO.getCantidad(),
                detalleFacturaDTO.getSubtotal(),
                productoRepository.findByIdProducto(detalleFacturaDTO.getProductoId()).orElse(null),
                facturaRepository.findByIdFactura(detalleFacturaDTO.getFacturaId()).orElse(null)
        );

        detalleFacturaRepository.save(detalleFactura);

        return detalleFactura.getIdDetalleFactura().toString();
    }

    public DetalleFactura findByIdDetalleFactura(Long idDetalleFactura) {
        return detalleFacturaRepository.findByIdDetalleFactura(idDetalleFactura).orElse(null);
    }

    public List<DetalleFactura> findByFacturaId(Long facturaId) {
        return detalleFacturaRepository.findByFacturaIdFactura(facturaId);
    }

    public List<DetalleFactura> findAll() {
        return detalleFacturaRepository.findAll();
    }
}