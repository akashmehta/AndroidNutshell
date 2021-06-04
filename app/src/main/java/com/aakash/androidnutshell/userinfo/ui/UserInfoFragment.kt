package com.aakash.androidnutshell.userinfo.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aakash.androidnutshell.AppApplication
import com.aakash.androidnutshell.R
import com.aakash.androidnutshell.userinfo.dto.UserInfoScreenData
import com.aakash.androidnutshell.usermodule.dto.UserItemResponse
import com.aakash.androidnutshell.usermodule.model.UserItemsViewModel
import com.aakash.androidnutshell.utils.Constants
import com.aakash.androidnutshell.utils.apicontract.ApiEndPoint
import kotlinx.android.synthetic.main.fragment_user_info.*
import javax.inject.Inject

class UserInfoFragment : Fragment() {

    companion object {
        fun newInstance(userInfoScreenData: UserInfoScreenData) : UserInfoFragment {
            val fragment = UserInfoFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constants.BundleKeys.USER_INFO_SCREEN_DATA, userInfoScreenData)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var userViewModel: UserItemsViewModel

    @Inject
    lateinit var apiService: ApiEndPoint

    private var userInfoScreenData: UserInfoScreenData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViewModel()
    }

    private fun initData() {
        arguments?.let {
            userInfoScreenData = it.getParcelable(Constants.BundleKeys.USER_INFO_SCREEN_DATA)
        }
    }

    private fun initViewModel() {
        activity?.let { activity ->
            (activity.application as AppApplication).appComponent.inject(this)
            userViewModel = ViewModelProvider(this,
                UserItemsViewModel.FACTORY(apiService))[UserItemsViewModel::class.java]
            userViewModel.getSingleUserLiveData().observe(viewLifecycleOwner, Observer {
                populateData(it.response)
            })
        }
        userInfoScreenData?.let { screenData ->
            userViewModel.fetchSingleUser(screenData.login ?: "")
        }
    }

    private fun populateData(response: UserItemResponse?) {
        response?.let {
            tvUserInfo.text = it.login
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("${this.tag} UserInfoFragment.onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("${this.tag} UserInfoFragment.onCreate")
    }

    override fun onStart() {
        super.onStart()
        println("${this.tag} UserInfoFragment.onStart")
    }

    override fun onResume() {
        super.onResume()
        println("${this.tag} UserInfoFragment.onResume")
    }

    override fun onPause() {
        super.onPause()
        println("${this.tag} UserInfoFragment.onPause")
    }

    override fun onStop() {
        super.onStop()
        println("${this.tag} UserInfoFragment.onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("${this.tag} UserInfoFragment.onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("${this.tag} UserInfoFragment.onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        println("${this.tag} UserInfoFragment.onDetach")
    }
}