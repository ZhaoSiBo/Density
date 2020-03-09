package com.chaomeng.density

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**

 *文件描述：.
 *作者：Created by Administrator on 2020/3/2.
 *版本号：1.0

 */
class FragmentLifecycleCallbacksImplToAndroidx(val densityAdaptStrategy: DensityAdaptStrategy) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
            densityAdaptStrategy.applyAdapt(f, f.requireActivity())
    }
}