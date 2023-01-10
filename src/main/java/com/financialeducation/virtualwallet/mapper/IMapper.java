package com.financialeducation.virtualwallet.mapper;

import org.modelmapper.ModelMapper;

@FunctionalInterface
public interface IMapper {
	public ModelMapper getModelMapper();
}
