package com.sun.makeupwindow.ui.search

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseFragment
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.ui.dialog.LoadingDialog
import com.sun.makeupwindow.ui.home.OnLoadMoreListener
import com.sun.makeupwindow.ui.home.RecyclerViewLoadMoreScroll
import com.sun.makeupwindow.ui.productdetail.ProductDetailFragment
import com.sun.makeupwindow.utlis.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment(), SearchContract.View {

    private var searchAdapter = SearchAdapter(this::itemProductClicked)
    private var searchPresenter: SearchPresenter? = null
    private var loadingDialog: LoadingDialog? = null
    private var searchWord = STRING_DEFAULT
    private var loadMoreItems = mutableListOf<Product>()
    private lateinit var layout: RecyclerView.LayoutManager
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

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
        recyclerSearch.adapter = searchAdapter
    }

    override fun initActions() {
        setLayoutManager()
        setScrollListener()
        changeEditText()
    }

    override fun showProducts(products: List<Product>) {
        searchAdapter.replaceData(products)
        loadMoreItems.addAll(products)
    }

    override fun showProductsLoadMore(products: List<Product>) {
        loadMoreItems.addAll(products)
        searchAdapter.removeLoadingView()
        searchAdapter.replaceData(loadMoreItems)
        scrollListener.isLoading = false
    }

    override fun showTotalProduct(totalItem: Int) {
        textResult.text = "${RESULT}: ${totalItem}"
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
                searchAdapter.replaceData(mutableListOf())
                textResult.isVisible = false
                searchWord = STRING_DEFAULT
            } else {
                searchPresenter?.getProducts(text.toString())
                searchPresenter?.getTotalProduct(text.toString())
                textResult.isVisible = true
                searchWord = text.toString()
            }
        }
    }

    private fun setLayoutManager() {
        layout = GridLayoutManager(context, 1)
        recyclerSearch.apply {
            layoutManager = layout
            setHasFixedSize(true)
            adapter = searchAdapter
        }
        (layout as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {

                override fun getSpanSize(position: Int) =
                    when (searchAdapter.getItemViewType(position)) {
                        VIEW_TYPE_ITEM -> 1
                        VIEW_TYPE_LOADING -> 1
                        else -> -1
                    }
            }
    }

    private fun setScrollListener() {
        scrollListener = RecyclerViewLoadMoreScroll(layout as GridLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        recyclerSearch.addOnScrollListener(scrollListener)
    }

    private fun LoadMoreData() {
        searchAdapter.addLoadingView()
        val totalItem = searchAdapter.itemCount
        searchPresenter?.getProductsLoadMore(mapOf(searchWord to totalItem))
    }

    companion object {
        private const val STRING_DEFAULT = ""
        private const val RESULT = "Result"
        private var instance: SearchFragment? = null

        fun getInstance() = instance ?: SearchFragment().also { instance = it }
    }
}
