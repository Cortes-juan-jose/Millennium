package com.app.millennium.data.model

import java.util.ArrayList

data class FCMResponse(
    var multicast_id: Long = 0L,
    var success: Int = 0,
    var failure: Int = 0,
    var canonical_ids: Int = 0,
    var results: ArrayList<Any> = arrayListOf()
)
