package com.agot.api


class Constants {
    companion object{
        private const val BASE_URL_DOMAIN = "http://www.shoponn.co.in/customer/"
        const val BASE_URL = BASE_URL_DOMAIN + "api/"
    }
    object AppSaveData {
    }

    object Preference {
        const val IS_LOGIN = "is_login"
    }
    object Params {
        const val DEVICE_ID = "device_id"
        const val DEVICE_TOKEN = "device_token"
    }
    object Apis {
        val GETCUSTOMERID = BASE_URL + "getMaxCustomerID"
        val LOGIN = BASE_URL + "login"
        val REGISTER = BASE_URL + "signupNewUser"

    }

}