package com.pharma.api


class Constants {
    companion object{
        const val BASE_URL_DOMAIN = "https://www.shoponn.co.in/"
        const val BASE_URL_CUSTOMER = BASE_URL_DOMAIN + "customer/api/"
        const val BASE_URL_PROMOTION = BASE_URL_DOMAIN + "promotion/api/"
        const val BASE_URL_MASTERMENU = BASE_URL_DOMAIN + "mastermenu/get/api/"
        const val BASE_URL_ORDER = BASE_URL_DOMAIN + "order/api/"
        const val BASE_URL_WISHLIST = BASE_URL_DOMAIN + "wishlist/api/"
        const val BASE_URL_PRODUCT = BASE_URL_DOMAIN + "product/"
        const val BASE_URL_PRODUCTSHOP = BASE_URL_DOMAIN + "productshop/api/"
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
        val GETCUSTOMERID = BASE_URL_CUSTOMER + "getMaxCustomerID"
        val LOGIN = BASE_URL_CUSTOMER + "login"
        val REGISTER = BASE_URL_CUSTOMER + "signupNewUser"
        val GETHOMEPAGEBANNERDETAILS = BASE_URL_PROMOTION + "get/getHomePageBannerDetails"
        val NEWLYLAUNCHED = BASE_URL_PRODUCT + "get/api/newlylaunched"
        val GETCATEGORYOFELECTRONICS = BASE_URL_MASTERMENU + "getCategoryOfElectronics"
        val GETCATEGORYOFBEAUTY = BASE_URL_MASTERMENU + "getCategoryOfBeauty"

    }

}