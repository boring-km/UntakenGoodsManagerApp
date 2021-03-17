package com.emart24.model

import android.os.Parcel
import android.os.Parcelable

class UnTakenGoods(): Parcelable {

    lateinit var name: String
    lateinit var phone: String
    lateinit var qrcode: String
    lateinit var dateTime: String
    var accept: Boolean = false

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()!!
        phone = parcel.readString()!!
        qrcode = parcel.readString()!!
        dateTime = parcel.readString()!!
        accept = parcel.readByte() != 0.toByte()
    }

    constructor(name: String, phone: String, qrcode: String, dateTime: String, accept: Boolean) : this() {
        this.name = name
        this.phone = phone
        this.qrcode = qrcode
        this.dateTime = dateTime
        this.accept = accept
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(qrcode)
        parcel.writeString(dateTime)
        parcel.writeByte(if (accept) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UnTakenGoods> {
        override fun createFromParcel(parcel: Parcel): UnTakenGoods {
            return UnTakenGoods(parcel)
        }

        override fun newArray(size: Int): Array<UnTakenGoods?> {
            return arrayOfNulls(size)
        }
    }
}