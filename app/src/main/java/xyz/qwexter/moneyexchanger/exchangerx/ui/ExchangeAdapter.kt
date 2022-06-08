package xyz.qwexter.moneyexchanger.exchangerx.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import xyz.qwexter.moneyexchanger.R

internal class ExchangeAdapter :
    ListAdapter<ExchangeUiModel, ExchangeAdapter.ExchangeViewHolder<ExchangeUiModel>>(Callback) {

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).id == 0) {
            R.layout.item_exchange_total
        } else {
            R.layout.item_exchange_nominal
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExchangeViewHolder<ExchangeUiModel> {
        return when (viewType) {
            R.layout.item_exchange_total -> ExchangeTotalViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            R.layout.item_exchange_nominal -> NominalViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            else -> throw IllegalStateException("Unsupported viewType")
        } as ExchangeViewHolder<ExchangeUiModel>
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder<ExchangeUiModel>, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ExchangeViewHolder<ExchangeUiModel>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = payloads.firstOrNull()
        if (item != null && item is ExchangeUiModel) {
            holder.bindData(item)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    internal abstract class ExchangeViewHolder<in TData : ExchangeUiModel>(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindData(item: TData)
    }

    private class ExchangeTotalViewHolder(
        itemView: View
    ) : ExchangeViewHolder<ExchangeUiModel.Total>(itemView) {
        private val amountEditText =
            itemView.findViewById<EditText>(R.id.item_exchange_total_edit_text)

        override fun bindData(item: ExchangeUiModel.Total) {
            amountEditText.setText(item.amount)
        }
    }

    private class NominalViewHolder(
        itemView: View
    ) : ExchangeViewHolder<ExchangeUiModel.Nominal>(itemView) {
        private val amountEditText =
            itemView.findViewById<EditText>(R.id.item_exchange_nominal_amount_edit_text)

        private val amountSlider =
            itemView.findViewById<Slider>(R.id.item_exchange_nominal_amount_slider)

        override fun bindData(item: ExchangeUiModel.Nominal) {
            amountEditText.setText(item.amount)
        }
    }

}

private object Callback : DiffUtil.ItemCallback<ExchangeUiModel>() {
    override fun areItemsTheSame(oldItem: ExchangeUiModel, newItem: ExchangeUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExchangeUiModel, newItem: ExchangeUiModel): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ExchangeUiModel, newItem: ExchangeUiModel): Any {
        return newItem
    }
}

sealed class ExchangeUiModel {
    abstract val id: Int

    data class Total(
        override val id: Int,
        val amount: String,
        val isValid: Boolean
    ) : ExchangeUiModel()

    data class Nominal(
        override val id: Int,
        val amount: String,
        val nominal: Long,
        val max: Long
    ) : ExchangeUiModel()
}