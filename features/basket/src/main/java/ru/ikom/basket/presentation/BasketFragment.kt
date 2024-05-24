package ru.ikom.basket.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.ikom.basket.databinding.FragmentBasketBinding
import ru.ikom.basket.di.BasketComponentProvider
import ru.ikom.common.BaseFragment
import javax.inject.Inject

class BasketFragment : BaseFragment<FragmentBasketBinding, BasketViewModel>() {

    @Inject
    lateinit var viewModelFactory: Lazy<BasketViewModel.Factory>

    override val viewModel: BasketViewModel by lazy {
        ViewModelProvider(this, viewModelFactory.get())[BasketViewModel::class.java]
    }

    private val adapter = ProductsBasketAdapter({
        viewModel.action(Event.OpenDetails(it))
    }, {
        viewModel.action(Event.DeleteProduct(it))
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as BasketComponentProvider).provideBasketComponent().inject(this)
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentBasketBinding =
        FragmentBasketBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productsBasket.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect { state ->
                binding.tvInfoEmpty.visibility = if (state.isEmpty) View.VISIBLE else View.GONE
                binding.productsBasket.visibility = if (state.isEmpty) View.GONE else View.VISIBLE

                if (!state.isEmpty) adapter.submitList(state.products)
            }
        }
    }

    companion object {

        fun newInstance() =
            BasketFragment()
    }
}