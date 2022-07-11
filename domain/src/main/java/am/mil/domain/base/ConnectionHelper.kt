package am.mil.domain.base

import android.content.Context
import android.net.ConnectivityManager

object ConnectionHelper {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected!!
    }

}