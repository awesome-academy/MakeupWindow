package com.sun.makeupwindow.ui.search

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseFragment
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.ui.dialog.LoadingDialog
import com.sun.makeupwindow.ui.productdetail.ProductDetailFragment
import com.sun.makeupwindow.utlis.RepositoryFactory
import com.sun.makeupwindow.utlis.addFragment
import com.sun.makeupwindow.utlis.showToast
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment(), SearchContract.View {

    private var adapter = SearchAdapter(this::itemProductClicked)
    private var searchPresenter: SearchPresenter? = null
    private var loadingDialog: LoadingDialog? = null

    override val layoutResource: Int
        get() = R.layout.fragment_search

    override fun initData() {
        initDialog()
        initAdapter()
        initPresenter()
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryFactory.getRepository(context)
        searchPresenter = SearchPresenter(this, repository)
    }

    private fun initDialog() {
        context?.let { loadingDialog = LoadingDialog(it) }
    }

    private fun initAdapter() {
        recyclerSearch.adapter = adapter
    }

    override fun initActions() {
        changeEditText()
    }

    override fun showProducts(products: List<Product>) {
        adapter.replaceData(products)
    }

    private fun itemProductClicked(product: Product) {
        fragmentManager?.addFragment(
            R.id.frameContainer,
            ProductDetailFragment.newInstance(product)
        )
    }

    override fun showError(message: String) {
        context?.showToast(message)
    }

    override fun showLoading() {
        loadingDialog?.show()
    }

    override fun hideLoading() {
        loadingDialog?.hide()
    }

    private fun changeEditText() {
        editSearch.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                adapter.replaceData(mutableListOf())
                textResult.isVisible = false
            } else {
                searchPresenter?.getProducts(text.toString())
                textResult.isVisible = true
            }
        }
    }

    companion object {
        private var instance: SearchFragment? = null

        fun getInstance() = instance ?: SearchFragment().also { instance = it }
    }
}
