package com.example.startup.models

class History {
    var message:String?=null
    var desc:String?=null
    var amount:Int?=null

    constructor(message:String, desc: String, amount:Int) {
        this.message = message
        this.desc =  desc
        this.amount = amount
    }
}