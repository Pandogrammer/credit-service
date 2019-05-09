package com.watea.creditservice.pungueado;

public class ProductionSetupDao extends BaseSetupDao {
	
	public ProductionSetupDao(Service billingService) {
		super(billingService);
	}

	@Override
	public Setup readSetup() {
		return new Setup("https://wsaa.afip.gov.ar/ws/services/LoginCms?wsdl",
				"https://wsaa.afip.gov.ar/ws/services/LoginCms",
				billingService.getProductionWsdl(),
				"wsaa");
	}
}
