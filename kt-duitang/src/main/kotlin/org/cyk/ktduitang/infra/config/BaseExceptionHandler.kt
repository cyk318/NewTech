package org.cyk.ktduitang.infra.config

import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestValueException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException


@ControllerAdvice
@ResponseBody
class BaseExceptionHandler {

    companion object {
        private val log = LoggerFactory.getLogger(BaseExceptionHandler::class.java)
    }

    @ExceptionHandler(AppException::class)
    fun applicationException(e: AppException): ApiResp<*> {
        e.printStackTrace()
        log.error(e.log)
        return ApiResp.no(e.status)
    }

    /**
     * 参数异常相关
     */
    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        ConstraintViolationException::class,
        MissingRequestValueException::class,
        MethodArgumentTypeMismatchException::class,
        IllegalArgumentException::class
    )
    fun handlerParamException(ex: Exception): ApiResp<*> {
        ex.printStackTrace()
        log.error(ex.message)
        return ApiResp.no(ApiStatus.INVALID_PARAM)
    }

    /**
     * 处理未捕获的异常
     */
    @ExceptionHandler(Exception::class)
    fun handlerException(e: Exception): ApiResp<*> {
        e.printStackTrace()
        log.error(e.message)
        return ApiResp.no(ApiStatus.SERVER_ERROR)
    }
}
