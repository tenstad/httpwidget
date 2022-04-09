package io.amund.httpwidgets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

class IconAdapter(context: Context, private val items: List<Int>, private val onClick: (Int?) -> Unit) : ArrayAdapter<Int>(context, 0, MutableList(items.size) { items[it] }) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val icon = getItem(position) ?: R.drawable.ic_http_black_24dp
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.icon_layout, parent, false)

        view.findViewById<ImageView>(R.id.icon).setImageResource(icon)
        view.setOnClickListener {
            onClick(getItem(position))
        }

        return view
    }
}
