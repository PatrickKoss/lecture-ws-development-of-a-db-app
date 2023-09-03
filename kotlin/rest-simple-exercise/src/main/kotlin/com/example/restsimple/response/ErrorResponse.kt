package com.example.restsimple.response

import javax.validation.constraints.NotNull

class ErrorResponse(var message: @NotNull(message = "message is required") String?)
