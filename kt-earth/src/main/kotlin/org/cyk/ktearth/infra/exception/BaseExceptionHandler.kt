package org.cyk.ktearth.infra.exception

import org.slf4j.LoggerFactory
import jakarta.validation.ConstraintViolationException
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestValueException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
 
 
@RestControllerAdvice
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
     * 限流异常，不打印详细栈信息
     */
    @ExceptionHandler(FlowLimitException::class)
    fun flowLimitException(e: FlowLimitException): ApiResp<*> {
        log.warn(e.log)
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
        IllegalArgumentException::class,
        HttpMessageNotReadableException::class,
    )
    fun handlerParamException(ex: Exception): ApiResp<*> {
        ex.printStackTrace()
        log.error(ex.message)
        return ApiResp.no(ApiStatus.INVALID_REQUEST)
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