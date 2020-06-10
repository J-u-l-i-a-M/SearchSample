package com.example.searchsample.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout.LayoutParams
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import com.example.searchsample.R
import com.example.searchsample.views.BaseMoxyView
import moxy.MvpAppCompatDialogFragment


abstract class BaseDialog : MvpAppCompatDialogFragment(), BaseMoxyView {
    var dismissListener: () -> Unit = {}

    @Nullable
    var positiveButton: Button? = null

    @Nullable
    var negativeButton: Button? = null

    @Nullable
    var neutralButton: Button? = null

    override fun getView(): View = if (this.layoutResId() != 0) viewA else FrameLayout(requireContext())

    @LayoutRes
    protected open fun layoutResId(): Int = 0

    @StringRes
    protected open fun titleResId(): Int = 0

    override fun onStart() {
        super.onStart()
        val window = dialog?.window ?: return
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private val viewA: View by lazy { LayoutInflater.from(context).inflate(layoutResId(), null, false) }

    protected open fun inject() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), getStyle())
        when {
            titleResId() != 0 -> builder.setTitle(titleResId())
        }

        when {
            layoutResId() != 0 -> builder.setView(viewA)
            message().isNotEmpty() -> builder.setMessage(message())
        }

        when {
            positiveButton() != 0 -> builder.setPositiveButton(positiveButton(), null)
            positiveButtonString().isNotEmpty() -> builder.setPositiveButton(positiveButtonString(), null)
        }

        when {
            negativeButton() != 0 -> builder.setNegativeButton(negativeButton(), null)
            negativeButtonString().isNotEmpty() -> builder.setNegativeButton(negativeButtonString(), null)
        }

        when {
            neutralButton() != 0 -> builder.setNeutralButton(neutralButton(), null)
            neutralButtonString().isNotEmpty() -> builder.setNeutralButton(neutralButtonString(), null)
        }

        initDialog(builder)

        return builder.create().apply { setCanceledOnTouchOutside(isCanceledOnTouchOutside()) }
    }

    protected open fun initDialog(builder: AlertDialog.Builder) {}

    protected open fun isCanceledOnTouchOutside(): Boolean = true

    @StringRes
    protected open fun positiveButton(): Int = 0

    protected open fun positiveButtonString(): String = ""

    protected open fun positiveClick() {}

    protected open fun message(): CharSequence = ""

    protected open fun initViews() {}

    private fun getButton(whichButton: Int): Button? = if (dialog == null) null else (dialog as? AlertDialog)?.getButton(whichButton)

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener()
    }

    override fun onResume() {
        positiveButton = getButton(DialogInterface.BUTTON_POSITIVE)
        negativeButton = getButton(DialogInterface.BUTTON_NEGATIVE)
        neutralButton = getButton(DialogInterface.BUTTON_NEUTRAL)
        val params = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply { setMargins(resources.getDimensionPixelSize(R.dimen.padding), 0, 0, 0) }

        if (positiveButton() != 0 || positiveButtonString().isNotEmpty()) {
            positiveButton?.layoutParams = params
            positiveButton?.setOnClickListener { positiveClick() }
        }

        if (negativeButton() != 0 || negativeButtonString().isNotEmpty()) {
            negativeButton?.layoutParams = params
            negativeButton?.setOnClickListener { negativeClick() }
        }

        if (neutralButton() != 0 || negativeButtonString().isNotEmpty()) {
            neutralButton?.layoutParams = params
            neutralButton?.setOnClickListener { neutralClick() }
        }

        initViews()

        if (isCanceledOnTouchOutside()) dialog?.setOnCancelListener { cancelClick() }

        super.onResume()
    }

    @StyleRes
    protected open fun getStyle(): Int = R.style.AppDialogStyle

    @StringRes
    protected open fun negativeButton(): Int = 0

    protected open fun negativeButtonString(): String = ""

    protected open fun negativeClick() {}

    @StringRes
    protected open fun neutralButton(): Int = 0

    protected open fun neutralClick() {}

    protected open fun neutralButtonString(): String = ""

    protected open fun cancelClick() {}

    override fun onDestroyView() {
        if (layoutResId() != 0) (viewA.parent as? ViewGroup)?.removeView(viewA)
        super.onDestroyView()
    }
}