package com.gateway.exception

import com.gateway.Constant.GATEWAY_INITIALIZATION_FAILED

class GatewayInitializerException(override val message: String = GATEWAY_INITIALIZATION_FAILED): Exception(message)