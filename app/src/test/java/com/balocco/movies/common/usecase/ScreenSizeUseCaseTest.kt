package com.balocco.movies.common.usecase

import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.WindowManager
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val WIDTH = 100
private const val HEIGHT = 150

class ScreenSizeUseCaseTest {

    @Mock private lateinit var context: Context
    @Mock private lateinit var windowManager: WindowManager
    @Mock private lateinit var display: Display

    private lateinit var useCase: ScreenSizeUseCase

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = ScreenSizeUseCase(context)
        initializeDisplay()
    }

    @Test fun `When get screen width, returns width from display`() {
        val screenWidth = useCase.screenWidth()

        assertThat(screenWidth).isEqualTo(WIDTH)
    }

    private fun initializeDisplay() {
        prepareDisplaySizes()
        whenever(context.getSystemService(Context.WINDOW_SERVICE)).thenReturn(windowManager)
        whenever(windowManager.defaultDisplay).thenReturn(display)
    }

    private fun prepareDisplaySizes() {
        doAnswer { invocation ->
            val point = invocation.arguments[0] as Point
            point.x = WIDTH
            point.y = HEIGHT
            null
        }.whenever(display).getSize(any())
    }
}