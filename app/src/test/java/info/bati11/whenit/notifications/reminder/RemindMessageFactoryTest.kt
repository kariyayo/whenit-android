package info.bati11.whenit.notifications.reminder

import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate

@RunWith(Enclosed::class)
class RemindMessageFactoryTest {

    class `引数を変える`() {
        private val date = LocalDate.of(2020, 5, 16)
        private lateinit var eventRepository: EventRepository

        @Before
        fun setUp() {
            eventRepository = mockk(relaxed = true)
            every { eventRepository.findByDate(date.plusDays(1)) } returns listOf(
                Event(1, "AA", date.plusDays(1).year, date.plusDays(1).monthValue, date.plusDays(1).dayOfMonth),
                Event(2, "BB", date.plusDays(1).year, date.plusDays(1).monthValue, date.plusDays(1).dayOfMonth)
            )
            every { eventRepository.findByDate(date.plusWeeks(1)) } returns listOf(
                Event(3, "CC", date.plusWeeks(1).year, date.plusWeeks(1).monthValue, date.plusWeeks(1).dayOfMonth),
                Event(4, "DD", date.plusWeeks(1).year, date.plusWeeks(1).monthValue, date.plusWeeks(1).dayOfMonth)
            )
            every { eventRepository.findByDate(date.plusMonths(1)) } returns listOf(
                Event(5, "EE", date.plusMonths(1).year, date.plusMonths(1).monthValue, date.plusMonths(1).dayOfMonth),
                Event(6, "FF", date.plusMonths(1).year, date.plusMonths(1).monthValue, date.plusMonths(1).dayOfMonth)
            )
        }

        @Test
        fun `何も取得しない`() {
            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = false,
                isAvailableWeek = false,
                isAvailableMonth = false
            )
            assertEquals(null, result)
        }

        @Test
        fun `明日だけ取得する`() {
            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = true,
                isAvailableWeek = false,
                isAvailableMonth = false
            )
            assertEquals("Whenit", result?.first)
            assertEquals("tomorrow is AA and others.", result?.second)
        }

        @Test
        fun `来週だけ取得する`() {
            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = false,
                isAvailableWeek = true,
                isAvailableMonth = false
            )
            assertEquals("Whenit", result?.first)
            assertEquals("one week later, CC and others.", result?.second)
        }

        @Test
        fun `来月だけ取得する`() {
            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = false,
                isAvailableWeek = false,
                isAvailableMonth = true
            )
            assertEquals("Whenit", result?.first)
            assertEquals("one month later, EE and others.", result?.second)
        }

        @Test
        fun `明日も来週も来月も取得する`() {
            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = true,
                isAvailableWeek = true,
                isAvailableMonth = true
            )
            assertEquals("Whenit", result?.first)
            assertEquals("tomorrow is AA and others.\none week later, CC and others.\none month later, EE and others.", result?.second)
        }
    }

    class `repositoryの返り値を変える`() {

        @Test
        fun `イベントが0件`() {
            val date = LocalDate.of(2020, 5, 16)
            val eventRepository = mockk<EventRepository>(relaxed = true)
            every { eventRepository.findByDate(date.plusDays(1)) } returns listOf()
            every { eventRepository.findByDate(date.plusWeeks(1)) } returns listOf()
            every { eventRepository.findByDate(date.plusMonths(1)) } returns listOf()

            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = true,
                isAvailableWeek = true,
                isAvailableMonth = true
            )
            assertEquals(null, result)
        }

        @Test
        fun `明日のイベントが1件`() {
            val date = LocalDate.of(2020, 5, 16)
            val eventRepository = mockk<EventRepository>(relaxed = true)
            every { eventRepository.findByDate(date.plusDays(1)) } returns listOf(
                Event(1, "AA", date.plusDays(1).year, date.plusDays(1).monthValue, date.plusDays(1).dayOfMonth)
            )
            every { eventRepository.findByDate(date.plusWeeks(1)) } returns listOf()
            every { eventRepository.findByDate(date.plusMonths(1)) } returns listOf()

            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = true,
                isAvailableWeek = true,
                isAvailableMonth = true
            )
            assertEquals("Whenit", result?.first)
            assertEquals("tomorrow is AA .", result?.second)
        }

        @Test
        fun `明日と来週のイベントが1件ずつ`() {
            val date = LocalDate.of(2020, 5, 16)
            val eventRepository = mockk<EventRepository>(relaxed = true)
            every { eventRepository.findByDate(date.plusDays(1)) } returns listOf(
                Event(1, "AA", date.plusDays(1).year, date.plusDays(1).monthValue, date.plusDays(1).dayOfMonth)
            )
            every { eventRepository.findByDate(date.plusWeeks(1)) } returns listOf(
                Event(3, "CC", date.plusWeeks(1).year, date.plusWeeks(1).monthValue, date.plusWeeks(1).dayOfMonth)
            )
            every { eventRepository.findByDate(date.plusMonths(1)) } returns listOf()

            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = true,
                isAvailableWeek = true,
                isAvailableMonth = true
            )
            assertEquals("Whenit", result?.first)
            assertEquals("tomorrow is AA .\none week later, CC .", result?.second)
        }

        @Test
        fun `明日は1件と来週は2件`() {
            val date = LocalDate.of(2020, 5, 16)
            val eventRepository = mockk<EventRepository>(relaxed = true)
            every { eventRepository.findByDate(date.plusDays(1)) } returns listOf(
                Event(1, "AA", date.plusDays(1).year, date.plusDays(1).monthValue, date.plusDays(1).dayOfMonth)
            )
            every { eventRepository.findByDate(date.plusWeeks(1)) } returns listOf(
                Event(3, "CC", date.plusWeeks(1).year, date.plusWeeks(1).monthValue, date.plusWeeks(1).dayOfMonth),
                Event(4, "DD", date.plusWeeks(1).year, date.plusWeeks(1).monthValue, date.plusWeeks(1).dayOfMonth)
            )
            every { eventRepository.findByDate(date.plusMonths(1)) } returns listOf()

            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = true,
                isAvailableWeek = true,
                isAvailableMonth = true
            )
            assertEquals("Whenit", result?.first)
            assertEquals("tomorrow is AA .\none week later, CC and others.", result?.second)
        }

        @Test
        fun `来月は1件`() {
            val date = LocalDate.of(2020, 5, 16)
            val eventRepository = mockk<EventRepository>(relaxed = true)
            every { eventRepository.findByDate(date.plusDays(1)) } returns listOf()
            every { eventRepository.findByDate(date.plusWeeks(1)) } returns listOf()
            every { eventRepository.findByDate(date.plusMonths(1)) } returns listOf(
                Event(5, "EE", date.plusMonths(1).year, date.plusMonths(1).monthValue, date.plusMonths(1).dayOfMonth)
            )

            val target = RemindMessageFactory(eventRepository)
            val result = target.createRemindContent(
                date = date,
                isAvailableDay = true,
                isAvailableWeek = true,
                isAvailableMonth = true
            )
            assertEquals("Whenit", result?.first)
            assertEquals("one month later, EE .", result?.second)
        }

    }
}
