package org.zongzi.platform.operation.socket.service;

import org.zongzi.platform.operation.socket.TyPayloadIdentity;

class ClockExecuteService extends AbstractExecuteService {

	ClockExecuteService() {
		super(TyPayloadIdentity.FLAG_CLOCK);
	}

	@Override
	Object execute(String body) {

		return false;
	}
}
