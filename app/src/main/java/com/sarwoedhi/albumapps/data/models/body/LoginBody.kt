package com.sarwoedhi.albumapps.data.models.body

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("device_token")
    var deviceToken: String? = null
)