package com.github.StormWyrm.wanandroid

import org.greenrobot.eventbus.EventBus

fun registerEventBus(obj : Any){
    if(!EventBus.getDefault().isRegistered(obj))
        EventBus.getDefault().register(obj)
}

fun unregisterEventBus(obj : Any){
    if(EventBus.getDefault().isRegistered(obj))
        EventBus.getDefault().register(obj)
}

fun sendEvent(obj: Any) {
    EventBus.getDefault().post(obj)
}

