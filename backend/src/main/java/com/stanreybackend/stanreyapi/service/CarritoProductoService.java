package com.stanreybackend.stanreyapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stanreybackend.stanreyapi.DTO.CarritoProductoDTO;
import com.stanreybackend.stanreyapi.entity.Carrito;
import com.stanreybackend.stanreyapi.entity.CarritoProducto;
import com.stanreybackend.stanreyapi.entity.Producto;
import com.stanreybackend.stanreyapi.repository.CarritoProductoRepository;
import com.stanreybackend.stanreyapi.repository.CarritoRepository;
import com.stanreybackend.stanreyapi.repository.ProductoRepository;

@Service
public class CarritoProductoService {

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Agrega un nuevo Producto al Carrito relacionados mediante CarritoProducto
    @Transactional
    public String addCarritoProducto(CarritoProductoDTO carritoProductoDTO) {
        // Busca y valida existencia de Carrito y Producto relacionados
        // caso contrario lanza excepcion de Argumento Invalido
        Carrito carrito = carritoRepository.findByIdCarrito(carritoProductoDTO.getCarritoId()).orElseThrow(
                () -> new IllegalArgumentException("Carrito no encontrado"));
        Producto producto = productoRepository.findByIdProducto(carritoProductoDTO.getProductoId()).orElseThrow(
                () -> new IllegalArgumentException("Producto no encontrado"));

        // Valida si el Usuario loggeado es el propietario del Carrito asociado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!carrito.getUsuario().getUsuario().equals(username)) {
            throw new SecurityException("No tienes permiso para modificar este carrito");
        }

        // Busca por existencia de relacion persistente
        CarritoProducto existingCarritoProducto = carritoProductoRepository
                .findByCarritoIdCarritoAndProductoIdProducto(carritoProductoDTO.getCarritoId(),
                        carritoProductoDTO.getProductoId())
                .orElse(null);

        // Si la relacion CarritoProducto ya existe entonces;
        // - suma nueva cantidad de producto y almacena
        if (existingCarritoProducto != null) {
            existingCarritoProducto
                    .setCantidad(existingCarritoProducto.getCantidad() + carritoProductoDTO.getCantidad());
            existingCarritoProducto.setPrecioUnitario(carritoProductoDTO.getPrecioUnitario());
            carritoProductoRepository.save(existingCarritoProducto);

            return existingCarritoProducto.getIdCarritoProducto().toString();
        } else {
            // Caso contrario
            // - crea nuevo CarritoProducto en base a DTO recibido y almacena
            CarritoProducto carritoProducto = new CarritoProducto(
                    carritoProductoDTO.getIdCarritoProducto(),
                    carritoProductoDTO.getFechaCreacion(),
                    carritoProductoDTO.getCantidad(),
                    carritoProductoDTO.getPrecioUnitario(),
                    carrito,
                    producto);

            carritoProductoRepository.save(carritoProducto);
            return carritoProducto.getIdCarritoProducto().toString();
        }
    }

    // Actualiza cantidad de Producto en Carrito
    @Transactional
    public String updateCarritoProductoCantidad(Long idCarritoProducto, Integer nuevaCantidad) {
        CarritoProducto carritoProducto = carritoProductoRepository.findByIdCarritoProducto(idCarritoProducto)
                .orElseThrow(
                        () -> new IllegalArgumentException("Producto no encontrado en el carrito"));

        // Valida si el Usuario es propietario del Carrito
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!carritoProducto.getCarrito().getUsuario().getUsuario().equals(username)) {
            throw new SecurityException("No tienes permiso para modificar este carrito");
        }

        if (nuevaCantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        carritoProducto.setCantidad(nuevaCantidad);
        carritoProductoRepository.save(carritoProducto);
        return carritoProducto.getIdCarritoProducto().toString();
    }

    public List<CarritoProducto> findByCarritoId(Long carritoId) {
        return carritoProductoRepository.findByCarritoIdCarrito(carritoId);
    }

    // Elimina todos los CarritoProducto relacionados a un Carrito
    @Transactional
    public void deleteAllByCarritoId(Long carritoId) {
        Carrito carrito = carritoRepository.findByIdCarrito(carritoId).orElseThrow(
                () -> new IllegalArgumentException("Carrito no encontrado"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!carrito.getUsuario().getUsuario().equals(username)) {
            throw new SecurityException("No tienes permiso para modificar este carrito");
        }

        // Utiliza metodo proporcionado por JPA
        carritoProductoRepository.deleteAllByCarritoIdCarrito(carritoId);
    }

    // Elimina el CarritoProducto del Carrito
    @Transactional
    public String deleteByIdCarritoProducto(Long idCarritoProducto) {
        CarritoProducto carritoProducto = carritoProductoRepository.findByIdCarritoProducto(idCarritoProducto)
                .orElse(null);
                
        if (carritoProducto != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!carritoProducto.getCarrito().getUsuario().getUsuario().equals(username)) {
                throw new SecurityException("No tienes permiso para modificar este carrito");
            }

            carritoProductoRepository.deleteByIdCarritoProducto(idCarritoProducto);
            return idCarritoProducto.toString();
        }
        return null;
    }
}