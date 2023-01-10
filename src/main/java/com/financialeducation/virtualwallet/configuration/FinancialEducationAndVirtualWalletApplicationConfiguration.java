package com.financialeducation.virtualwallet.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.financialeducation.virtualwallet.mapper.IMapper;

@Configuration
public class FinancialEducationAndVirtualWalletApplicationConfiguration {

	@Bean
	public ModelMapper modelMapperUser() {
		IMapper modelMapperUser = () -> {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
			return modelMapper;
		};
		return modelMapperUser.getModelMapper();
	}

	@Bean
	public ModelMapper modelMapper() {
		IMapper modelMapper = ModelMapper::new;
		return modelMapper.getModelMapper();
	}

}
