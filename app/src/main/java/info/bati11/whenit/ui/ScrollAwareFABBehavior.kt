package info.bati11.whenit.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import info.bati11.whenit.R

class ScrollAwareFABBehavior<V : View>(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (child is ViewGroup) {
            child.findViewById<FloatingActionButton>(R.id.fab)?.let {
                updateFab(it, dy)
            }
        } else if (child is FloatingActionButton) {
            updateFab(child, dy)
        }
    }

    private fun updateFab(fab: FloatingActionButton, dy: Int) {
        if (dy > 0 && fab.visibility == View.VISIBLE) {
            val listener = object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(b: FloatingActionButton?) {
                    super.onHidden(b)
                    b?.visibility = View.INVISIBLE;
                }
            }
            fab.hide(listener)
        } else if (dy < 0 && fab.visibility != View.VISIBLE) {
            fab.show();
        }
    }
}
