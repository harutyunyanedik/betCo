package am.mil.data.net.wallet

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class WalletDtoPacket<T>(
    @SerializedName("StatusCode")
    val statusCode: Int? = null,
    @SerializedName("StatusCodeDescription")
    val statusCodeDescription: String? = null,
    @SerializedName("Result")
    var data: T? = null
) : Serializable
