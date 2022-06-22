package com.agot.api


class Constants {
    companion object{
        private const val BASE_URL_DOMAIN = "http://agot.onepixelsoft.com/"
        val BASE_URL = BASE_URL_DOMAIN + "api/"
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
        val LOGIN = BASE_URL + "login"

    }

}