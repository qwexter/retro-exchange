package xyz.qwexter.moneyexchanger.exchangerx.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import xyz.qwexter.moneyexchanger.R

class ExchangeFragment : Fragment(R.layout.fragment_exchange) {

    private var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.fragment_exchange_recycler)
        recyclerView?.adapter = ExchangeAdapter()
    }

    override fun onStart() {
        super.onStart()
        bindItems(
            (0..15).map {
                if (it == 0) {
                    ExchangeUiModel.Total(0, "1234", true)
                } else {
                    ExchangeUiModel.Nominal(it, "", it * 5L, 100000)
                }
            }
        )
    }

    private fun bindItems(items: List<ExchangeUiModel>) {
        (recyclerView?.adapter as? ExchangeAdapter)?.submitList(items)
        Log.d("", "")
    }

}