package org.cyk.ktearth.application

interface ApplicationHandler<INPUT, OUTPUT> {

    fun handler(input: INPUT): OUTPUT

}