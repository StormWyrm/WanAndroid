package com.github.stormwyrm.wanandroid.model.api

class ApiExceptioin (var code: Int, override var message: String) : RuntimeException()