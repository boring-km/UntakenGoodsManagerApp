package com.emart24.model

class UnTakenGoods() {
    lateinit var name: String
    lateinit var phone: String
    lateinit var qrcode: String
    lateinit var dateTime: String
    var accept: Boolean = false

    constructor(name: String, phone: String, qrcode: String, dateTime: String, accept: Boolean) : this() {
        this.name = name
        this.phone = phone
        this.qrcode = qrcode
        this.dateTime = dateTime
        this.accept = accept
    }
}