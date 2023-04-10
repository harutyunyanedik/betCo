package am.mil.presentationapp.base

import am.mil.presentationapp.base.utils.KeepStateNavigator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NavigationRes
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.R

class DispatchInsetsNavHostFragment : NavHostFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnApplyWindowInsetsListener { v, insets ->
            // During fragment transitions, multiple fragment's view hierarchies can be added at the
            // same time. If one consumes window insets, the other might not be layed out properly.
            // To workaround that, make sure we dispatch the insets to all children, regardless of
            // how they are consumed.
            (v as? ViewGroup)?.forEach { child ->
                child.dispatchApplyWindowInsets(insets)
            }
            insets
        }
    }

    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        navController.navigatorProvider.addNavigator(
            KeepStateNavigator(
                requireContext(),
                childFragmentManager,
                getContainerId()
            )
        )
    }

    private fun getContainerId(): Int {
        val containerId = id
        return if (containerId != 0 && containerId != View.NO_ID) {
            containerId
        } else R.id.nav_host_fragment_container
        // Fallback to using our own ID if this Fragment wasn't added via
        // add(containerViewId, Fragment)
    }

    companion object {

        private val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"
        private val KEY_START_DESTINATION_ARGS = "android-support-nav:fragment:startDestinationArgs"
        private val KEY_NAV_CONTROLLER_STATE = "android-support-nav:fragment:navControllerState"
        private val KEY_DEFAULT_NAV_HOST = "android-support-nav:fragment:defaultHost"

        /**
         * Create a new NavHostFragment instance with an inflated [NavGraph] resource.
         *
         * @param graphResId resource id of the navigation graph to inflate
         * @param startDestinationArgs arguments to send to the start destination of the graph
         * @return a new NavHostFragment instance
         */
        fun create(
            @NavigationRes graphResId: Int,
            startDestinationArgs: Bundle?
        ): DispatchInsetsNavHostFragment {
            var b: Bundle? = null
            if (graphResId != 0) {
                b = Bundle()
                b.putInt(KEY_GRAPH_ID, graphResId)
            }
            if (startDestinationArgs != null) {
                if (b == null) {
                    b = Bundle()
                }
                b.putBundle(KEY_START_DESTINATION_ARGS, startDestinationArgs)
            }

            val result = DispatchInsetsNavHostFragment()
            if (b != null) {
                result.arguments = b
            }
            return result
        }
    }
}