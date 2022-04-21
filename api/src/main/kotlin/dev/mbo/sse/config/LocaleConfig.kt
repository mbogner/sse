package dev.mbo.sse.config

import org.springframework.context.annotation.Configuration
import java.util.Locale

@Configuration
class LocaleConfig {

    companion object {
        val DEFAULT_LOCALE: Locale = Locale.ENGLISH
    }

}