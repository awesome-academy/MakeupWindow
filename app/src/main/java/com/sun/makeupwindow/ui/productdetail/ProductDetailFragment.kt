package com.sun.makeupwindow.ui.productdetail

import androidx.core.os.bundleOf
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseFragment
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.ui.dialog.LoadingDialog
import com.sun.makeupwindow.utlis.RepositoryFactory
import com.sun.makeupwindow.utlis.loadImage
import kotlinx.android.synthetic.main.fragment_detail_product.*

class ProductDetailFragment : BaseFragment(), ProductDetailContract.View {

    private var presenter: ProductDetailPresenter? = null
    private var loadingDialog: LoadingDialog? = null
    private var productColorAdapter = ProductColorAdapter()
    private var relatedProductAdapter = RelatedProductAdapter(this::itemRelatedClicked)
    private var product: Product? = null

    override val layoutResource: Int
        get() = R.layout.fragment_detail_product

    override fun initData() {
        initInputData()
        initAdapter()
        initPresenter()
        initDialog()
        bindRelatedProductData()
        presenter?.start()
    }

    private fun initAdapter() {
        recyclerColor.adapter = productColorAdapter
        recyclerRelate.adapter = relatedProductAdapter
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryFactory.getRepository(context)
        val repositoryFavorite = RepositoryFactory.getRepositoryFavorite(context)
        presenter = ProductDetailPresenter(this, repository, repositoryFavorite)
    }

    private fun bindRelatedProductData() {
        product?.let { product ->
            presenter?.apply {
                getRelatedProducts(product.category)
            }
        }
    }

    private fun initDialog() {
        loadingDialog = LoadingDialog(requireContext())
    }

    override fun isFavoriteProduct(check: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showRelatedProducts(products: List<Product>) {
        relatedProductAdapter.replaceData(products)
    }

    override fun isInsertedFavoriteProduct(check: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isDeletedFavoriteProduct(check: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        loadingDialog?.show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun itemRelatedClicked(product: Product) {
    }

    private fun initInputData() {
        product = arguments?.getParcelable<Product>(BUNDLE_PRODUCT_DETAIL)?.apply {
            imageProduct.loadImage(this.imageLink)
            textProductName.text = this.name
            textProductPrice.text = this.price + " " + this.priceSign
            textProductDetail.text = this.description
            productColorAdapter.replaceData(this.color)
        }
    }

    companion object {
        private const val BUNDLE_PRODUCT_DETAIL = "BUNDLE_PRODUCT_DETAIL"

        fun getInstance(product: Product) = ProductDetailFragment().apply {
            arguments = bundleOf(BUNDLE_PRODUCT_DETAIL to product)
        }
    }
}
