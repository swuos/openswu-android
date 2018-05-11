package com.swuos.mobile.models.http

enum class ApiUrl(val url: String, val id: Int) {

    AC_HOST("http://ac.xenoeye.org", 100),
    FREEGATTY_HOST("http://118.25.5.239:10080", 1011),
    VERIFICATION_HOST("http://119.27.174.160", 1010),
    LOGIN_URL(AC_HOST.url + "/ac/login", 1001),

}