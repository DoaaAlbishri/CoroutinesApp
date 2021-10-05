package com.example.coroutinesapp

import com.google.gson.annotations.SerializedName

class Slip {

    @SerializedName("slip")
    var slip: Advices? = null

    class Advices {
        @SerializedName("id")
        var id: Int? = null
        @SerializedName("advice")
        var advice: String? = null

    }
}