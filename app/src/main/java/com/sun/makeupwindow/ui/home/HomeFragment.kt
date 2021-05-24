package com.sun.makeupwindow.ui.home

import android.widget.Toast
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseFragment
import com.sun.makeupwindow.data.model.Category
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.repository.ProductRepository
import com.sun.makeupwindow.data.source.ProductDataSource
import com.sun.makeupwindow.ui.dialog.LoadingDialog
import com.sun.makeupwindow.ui.productdetail.ProductDetailFragment
import com.sun.makeupwindow.utlis.RepositoryFactory
import com.sun.makeupwindow.utlis.addFragment
import com.sun.makeupwindow.utlis.showToast
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), HomeContract.View {

    private var presenter: HomePresenter? = null
    private var loadingDialog: LoadingDialog? = null
    private val adapterProduct = ProductAdapter(this::itemProductClicked)
    private val adapterCategory = CategoryAdapter(this::itemCategoryClicked)

    override val layoutResource: Int
        get() = R.layout.fragment_home

    override fun initData() {
        initAdapter()
        initPresenter()
        initDialog()
        presenter?.start()
    }

    override fun initActions() {
    }

    private fun initAdapter() {
        recyclerProduct.adapter = adapterProduct
        recyclerCategory.adapter = adapterCategory
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryFactory.getRepository(context)
        presenter = HomePresenter(this, repository)
    }

    private fun initDialog() {
        context?.let {
            loadingDialog = LoadingDialog(it)
        }
    }

    override fun showProducts(products: List<Product>) {
        adapterProduct.replaceData(products)
    }

    override fun showCategories(categories: List<Category>) {
        adapterCategory.replaceData(categories)
    }

    override fun showError(message: String) {
        context?.showToast(message, Toast.LENGTH_LONG)
    }

    override fun showLoading() {
        loadingDialog?.show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun itemProductClicked(product: Product) {
        fragmentManager?.addFragment(R.id.frameContainer, ProductDetailFragment.getInstance(product))
    }

    private fun itemCategoryClicked(category: Category) {
    }

    companion object{
        private var instance: HomeFragment? = null

        fun getInstance() = instance ?: HomeFragment().also { instance = it }
    }
}
