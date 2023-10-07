package com.tc.refuel.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.drake.net.request.BodyRequest
import com.dylanc.viewbinding.base.FragmentBinding
import com.dylanc.viewbinding.base.FragmentBindingDelegate
import com.xuexiang.rxutil2.rxjava.RxJavaUtils
import io.reactivex.disposables.Disposable
import ui.loading.LoadingDialog
import ui.loading.LoadingDialogManager
import java.util.concurrent.TimeUnit

abstract class BaseFragment<VB : ViewBinding> : Fragment(), FragmentBinding<VB> by FragmentBindingDelegate(), LoadingDialogManager {

    private var mFakeLoadingDelay: Disposable? = null

    lateinit var vm: MainViewModel

    abstract fun initView()

    override val loadingDialog by lazy { LoadingDialog(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createViewWithBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        initView()
    }

    fun BodyRequest.addMapDotToCloud(bean: String) {
        param("bean", bean)
    }

    fun baseFakeLoading(str: String, time: Long = 5) {
        startLoadingDialog(requireContext(), str)
        mFakeLoadingDelay = RxJavaUtils.delay(time, TimeUnit.SECONDS).subscribe {
            endLoadingDialog()
            mFakeLoadingDelay?.dispose()
        }
    }
}