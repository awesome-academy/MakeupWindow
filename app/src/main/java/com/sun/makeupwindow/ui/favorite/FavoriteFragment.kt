package com.sun.makeupwindow.ui.favorite

import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseFragment
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.ui.dialog.LoadingDialog
import com.sun.makeupwindow.ui.productdetail.ProductDetailFragment
import com.sun.makeupwindow.utlis.RepositoryFactory
import com.sun.makeupwindow.utlis.addFragment
import com.sun.makeupwindow.utlis.showToast
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : BaseFragment(), FavoriteContract.View {

    private var presenter: FavoritePresenter? = null
    private var loadingDialog: LoadingDialog? = null
    private var favoriteProductAdapter = FavoriteProductAdapter(this::itemFavoriteClicked)

    override val layoutResource: Int
        get() = R.layout.fragment_favorite

    override fun initData() {
        initAdapter()
        initPresenter()
        initDialog()
        presenter?.start()
    }

    private fun initAdapter() {
        recyclerFavoriteProducts.adapter = favoriteProductAdapter
    }

    private fun initPresenter() {
        val context = context ?: return
        val repositoryFavorite = RepositoryFactory.getRepositoryFavorite(context)
        presenter = FavoritePresenter(this, repositoryFavorite)
    }

    private fun initDialog() {
        loadingDialog = LoadingDialog(requireContext())
    }

    override fun initActions() {
    }

    override fun showProducts(products: List<Product>) {
        favoriteProductAdapter.replaceData(products)
    }

    private fun itemFavoriteClicked(product: Product) {
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
        loadingDialog?.dismiss()
    }

    companion object {
        private var instance: FavoriteFragment? = null

        fun getInstance() = instance ?: FavoriteFragment().also { instance = it }
    }
}
