package info.bati11.whenit.ui.app.event_create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import info.bati11.whenit.CoroutinesTestRule
import info.bati11.whenit.repository.EventRepository
import info.bati11.whenit.ui.ValidationError
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.threeten.bp.Instant

@RunWith(Enclosed::class)
class EventCreateViewModelTest {

    @RunWith(Parameterized::class)
    class `onSaveClickedのテスト` constructor(private val t: TestCase) {

        @get:Rule
        var coroutinesTestRule = CoroutinesTestRule()

        @get:Rule
        var instantTaskExecutorRule = InstantTaskExecutorRule()

        data class Args(val title: String, val date: Long?)
        data class TestCase(
            val name: String,
            val args: Args,
            val want1: ValidationError?,
            val want2: ValidationError?,
            val shouldCalledRepo: Boolean
        )

        companion object {
            @JvmStatic
            @Parameterized.Parameters(name = "{0}")
            fun data() = listOf(
                TestCase(
                    "タイトルも日付も指定なし",
                    Args("", null),
                    ValidationError.Required, ValidationError.Required, false
                ),
                TestCase(
                    "日付だけ空文字列",
                    Args("ほげ", null),
                    null, ValidationError.Required, false
                ),
                TestCase(
                    "タイトルも日付も空文字列ではない",
                    Args("ほげ", Instant.now().toEpochMilli()),
                    null, null, true
                )
            )
        }

        @Before
        fun setUp() {
            MockKAnnotations.init(this, relaxUnitFun = true)
            eventRepository = mockk()
            coEvery { eventRepository.add(any()) } just Runs
            target = EventCreateViewModel(eventRepository)
            t.args.title?.also { target.inputTitle(it) }
            t.args.date?.also { target.inputDate(it) }
        }

        lateinit var eventRepository: EventRepository
        lateinit var target: EventCreateViewModel

        @Test
        fun `onSaveClickedのテスト`() {
            val j = target.onSaveClicked()

            if (t.shouldCalledRepo) {
                coVerify(exactly = 1) { eventRepository.add(any()) }
            } else {
                coVerify(exactly = 0) { eventRepository.add(any()) }
            }
            assertEquals(t.want1, target.formTitleErr.value)
            assertEquals(t.want2, target.formDateErr.value)
            assertEquals(false, j.isCancelled)
        }
    }
}
