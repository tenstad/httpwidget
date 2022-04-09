package io.amund.httpwidgets

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.GridView
import androidx.fragment.app.DialogFragment

class IconDialog(val onClick: (Int?) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val view = GridView(context)
            val icons = listOf(
                R.drawable.ic_http_black_24dp,
                R.drawable.ic_build_black_24dp,
                R.drawable.grade_black_24dp,
                R.drawable.radio_black_24dp,
            )

            val onClick = { i: Int? ->
                dismiss();
                onClick(i)
            }
            view.adapter = context?.let { c -> IconAdapter(c, icons, onClick) }

            AlertDialog.Builder(it)
                .setTitle(R.string.pick_icon)
                .setView(view)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
