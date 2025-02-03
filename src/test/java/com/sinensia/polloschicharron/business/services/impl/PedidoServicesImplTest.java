package com.sinensia.polloschicharron.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.polloschicharron.business.model.EstadoPedido;
import com.sinensia.polloschicharron.business.model.Pedido;
import com.sinensia.polloschicharron.integration.model.EstadoPedidoPL;
import com.sinensia.polloschicharron.integration.model.PedidoPL;
import com.sinensia.polloschicharron.integration.repositories.PedidoPLRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServicesImplTest {
	
	@Mock
	private PedidoPLRepository pedidoPLRepository;
	
	@Mock
	private DozerBeanMapper mapper;
	
	@InjectMocks
	private PedidoServicesImpl pedidoServicesImpl;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	private PedidoPL pedidoPL1;
	private PedidoPL pedidoPL2;
	private Pedido pedido1;
	private Pedido pedido2;
	
	@Test
	void testCreate() {
	
		Pedido pedido = new Pedido();
		pedido.setId(null);
		
		PedidoPL pedidoPL = new PedidoPL();
		pedidoPL.setId(null);
		
		PedidoPL createdPedidoPL = new PedidoPL();
		createdPedidoPL.setId(120L);
		
		when(mapper.map(pedido, PedidoPL.class)).thenReturn(pedidoPL);
		when(pedidoPLRepository.save(pedidoPL)).thenReturn(createdPedidoPL);
		
		Long id = pedidoServicesImpl.create(pedido);
		
		assertEquals(120L, id);
		
	}
	
	@Test
	void testRead() {
		
		when(pedidoPLRepository.findById(50L)).thenReturn(Optional.of(pedidoPL1));
		when(pedidoPLRepository.findById(666L)).thenReturn(Optional.empty());
		
		when(mapper.map(pedidoPL1, Pedido.class)).thenReturn(pedido1);
		
		Optional<Pedido> optional1 = pedidoServicesImpl.read(50L);
		Optional<Pedido> optional2 = pedidoServicesImpl.read(666L);
		
		assertTrue(optional1.isPresent());
		assertTrue(optional2.isEmpty());
		
		assertEquals(50L, optional1.get().getId());
		
	}
	
	@Test
	void testUpdate() {
	
		Pedido pedido = new Pedido();
		pedido.setId(10L);
		pedido.setEstado(EstadoPedido.CANCELADO);
		
		PedidoPL pedidoPL = new PedidoPL();
		pedidoPL.setId(null);
		pedidoPL.setEstado(EstadoPedidoPL.NUEVO);
		
		PedidoPL updatePedidoPL = new PedidoPL();
		updatePedidoPL.setId(10L);
		updatePedidoPL.setEstado(EstadoPedidoPL.NUEVO);
		
		when(pedidoPLRepository.existsById(pedido.getId())).thenReturn(true);
		when(mapper.map(pedido, PedidoPL.class)).thenReturn(pedidoPL);
		
		pedidoServicesImpl.update(pedido);
		
		assertEquals(EstadoPedidoPL.NUEVO, updatePedidoPL.getEstado());
		
	}
	
	// ********************************************
	//
	// Private Methods
	//
	// ********************************************
	
	private void initObjects() {
		
		pedidoPL1 = new PedidoPL();
		pedidoPL1.setId(50L);
		
		pedidoPL2 = new PedidoPL();
		pedidoPL2.setId(60L);
		
		pedido1 = new Pedido();
		pedido1.setId(50L);
		
		pedido2 = new Pedido();
		pedido2.setId(60L);
		
	}

}
