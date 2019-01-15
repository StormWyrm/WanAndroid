package com.exmple.corelib.scheduler

import com.qingfeng.kotlinmvp_lib.sheduler.BaseScheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by xuhao on 2017/11/17.
 * desc:
 */


class TrampolineMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread())
