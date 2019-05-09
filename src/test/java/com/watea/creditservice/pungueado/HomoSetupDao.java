package com.watea.creditservice.pungueado;

public class HomoSetupDao extends BaseSetupDao {

	public HomoSetupDao(Service billingService) {
		super(billingService);
	}

	@Override
	public Setup readSetup() {
		return new Setup(
				"https://wsaahomo.afip.gov.ar/ws/services/LoginCms?wsdl",
				"https://wsaahomo.afip.gov.ar/ws/services/LoginCms",
				billingService.getHomoWsdl(),
				"wsaahomo");
	}
}