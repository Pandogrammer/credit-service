package com.watea.creditservice.pungueado;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

abstract class BaseSetupDao implements SetupDao {
	Service billingService;

	public BaseSetupDao(Service billingService) {
		checkNotNull(billingService);
		checkArgument(billingService == Service.WSFE || billingService == Service.WSMTXCA || billingService == Service.WSFECRED,
				"Invalid billing service: %s", billingService);
		this.billingService = billingService;
	}

}