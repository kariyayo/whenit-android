package info.bati11.whenit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val found = providers.entries.find { modelClass.isAssignableFrom(it.key) }
        val provider =
            found?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            return provider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}