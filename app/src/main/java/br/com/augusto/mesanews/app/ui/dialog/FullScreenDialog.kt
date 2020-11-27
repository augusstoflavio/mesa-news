package br.com.marata.rastreamento.app.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import br.com.augusto.mesanews.R
import kotlinx.android.synthetic.main.layout_full_screen_dialog.*

abstract class FullScreenDialog: DialogFragment() {

    protected lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    override fun onStart() {
        super.onStart()

        getDialog()?.getWindow()?.setWindowAnimations(
            R.style.dialog_animation_fade
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.layout_full_screen_dialog, container, false)
        toolbar = view.findViewById(R.id.toolbar_app) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener { view1 ->dialog?.dismiss() }
        toolbar.title = getDialogTitle()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val inflate = layoutInflater.inflate(getContent(), null)
        inflate.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        content.addView(inflate)
        super.onViewCreated(view, savedInstanceState)
    }

    fun setTitle(title: String) {
        toolbar.title = title
    }

    abstract fun getDialogTitle(): String

    abstract fun getContent(): Int
}