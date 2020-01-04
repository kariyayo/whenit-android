package info.bati11.whenit.ui.event

import android.app.Dialog
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import info.bati11.whenit.R

class EventMenuBottomSheetDialog : BottomSheetDialogFragment() {
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = View.inflate(context, R.layout.bottom_sheet_event_menu, null)
        dialog.setContentView(view)
    }
}
